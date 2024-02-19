package com.mogul.demo.common.service;

import com.mogul.demo.recommend.dto.WebtoonRecommendResponse;
import com.mogul.demo.recommend.service.RecommendService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonRecommendServiceImpl implements CommonRecommendService {

    private final UserService userService;

    private final ReviewService reviewService;

    private final RecommendService recommendService;

    private final WebtoonService webtoonService;

    @Override
    @Transactional(readOnly = true)
    public List listRecommandWebtoons() {
        User user = userService.getUserByToken();
        List<Long> webtodonIds = reviewService.findTopRatedWebtoonFive(user.getId());

        List<WebtoonRecommendResponse> data = new ArrayList<>();
        for (Long webtoonId : webtodonIds) {
            if(!webtoonService.getIsEmbedded(webtoonId)){
                continue;
            }
            List<WebtoonRecommendResponse> list = recommendService.ListRecommandWebtoons(webtoonId);
            for (WebtoonRecommendResponse dto : list) {
                data.add(dto);
            }
        }
        return data;
    }
}
