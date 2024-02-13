package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.RecommandResponse;
import com.mogul.demo.recommand.service.RecommandService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonRecommandServiceImpl implements CommonRecommandService{

    private final UserService userService;

    private final ReviewService reviewService;

    private final RecommandService recommandService;

    private final WebtoonService webtoonService;

    @Override
    @Transactional(readOnly = true)
    public List listRecommandWebtoons() {
        User user = userService.getUserFromAuth();
        List<Long> webtodonIds = reviewService.findTopRatedWebtoonFive(user.getId());
        List<RecommandResponse> data = new ArrayList<RecommandResponse>();
        for (Long webtoonId : webtodonIds) {
            if(!webtoonService.getIsEmbedded(webtoonId)){
                continue;
            }
            List<RecommandResponse> list = recommandService.ListRecommandWebtoons(webtoonId);
            for (RecommandResponse dto : list) {
                data.add(dto);
            }
        }
        for(RecommandResponse dto : data){
            dto.setWebtoon(webtoonService.findWebtoonById(dto.getWebtoonId()));
        }
        return data;
    }
}
