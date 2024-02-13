package com.mogul.demo.recommend.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mogul.demo.recommend.dto.WebtoonInfo;
import com.mogul.demo.recommend.dto.WebtoonRecommendResponse;
import com.mogul.demo.recommend.repository.EmbeddingRepository;
import com.mogul.demo.search.repository.WebtoonEmbeddingRepository;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.service.WebtoonService;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final EmbeddingRepository embeddingRepository;

    private final WebtoonEmbeddingRepository webtoonEmbeddingRepository;

    private final WebtoonService webtoonService;

    private final RestClient restClient;

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonRecommendResponse> ListRecommandWebtoons(Long webtoonId){
        String embeddingVector = embeddingRepository.findByWebtoonId(webtoonId);
        List<WebtoonInfo> webtoonInfos = new ArrayList<>();

        try{
            webtoonInfos = ElasticSearchKnnRequest(embeddingVector);
        }catch (IOException e){
            Throwable cause = e.getCause();
            if (cause != null) {
                // 원본 예외 처리
                cause.printStackTrace();
            } else {
                // 원본 예외가 없는 경우 기타 처리
                e.printStackTrace();
            }
        }

        List<WebtoonRecommendResponse> data = webtoonInfos.stream().filter(webtoonInfo -> webtoonInfo.getScore() != 1.0).map(
            webtoonInfo -> {
                WebtoonDetailResponse webtoonDetailResponse = webtoonService.findWebtoonById(webtoonInfo.getId());
                WebtoonRecommendResponse webtoonRecommendResponse = new WebtoonRecommendResponse();

                webtoonRecommendResponse.setId(webtoonInfo.getId());
                webtoonRecommendResponse.setScore(webtoonInfo.getScore());
                webtoonRecommendResponse.setWebtoon(webtoonDetailResponse);

                return webtoonRecommendResponse;
            }
        ).collect(Collectors.toList());

        return data;
    }

    @Override
    public List<WebtoonInfo> ElasticSearchKnnRequest(String queryVector) throws IOException {
        Request request = new Request("POST", "/webtoon-embeddings/_search");
        String jsonRequest = "{\n" +
            "  \"knn\": {\n" +
            "    \"field\": \"webtoon_embedding\",\n" +
            "    \"query_vector\": " + queryVector + ",\n" +
            "    \"k\": 10,\n" +
            "    \"num_candidates\": 100\n" +
            "  },\n" +
            "  \"_source\": [\"webtoon_id\"]\n" +
            "}";

        request.setJsonEntity(jsonRequest);

        Response response = restClient.performRequest(request);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        List<WebtoonInfo> list = parseResponse(responseBody);

        return list;
    }

    @Override
    public List<WebtoonInfo> parseResponse(String jsonResponse) {
        List<WebtoonInfo> webtoonInfos = new ArrayList<>();

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject hitsObject = jsonObject.getAsJsonObject("hits");
        JsonArray hitsArray = hitsObject.getAsJsonArray("hits");

        for (JsonElement hitElement : hitsArray) {
            JsonObject hitObject = hitElement.getAsJsonObject();
            JsonObject sourceObject = hitObject.getAsJsonObject("_source");

            int webtoonId = sourceObject.get("webtoon_id").getAsInt();
            float score = hitObject.get("_score").getAsFloat();

            WebtoonInfo webtoonInfo = new WebtoonInfo(webtoonId, score);
            webtoonInfos.add(webtoonInfo);
        }

        return webtoonInfos;
    }
}

