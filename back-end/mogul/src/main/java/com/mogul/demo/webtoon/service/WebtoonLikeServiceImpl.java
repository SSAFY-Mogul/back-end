package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.repository.WebtoonLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebtoonLikeServiceImpl implements WebtoonLikeService{

    private final WebtoonLikeRepository webtoonLikeRepository;

    @Override
    public WebtoonLikeResponse getLike(long webtoonId, long userId) {
        WebtoonLikeResponse data = new WebtoonLikeResponse();
        data.setLike(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isPresent());
        return data;
    }
}
