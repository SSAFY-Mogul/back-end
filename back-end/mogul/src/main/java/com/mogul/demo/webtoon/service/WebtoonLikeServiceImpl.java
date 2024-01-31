package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.entity.WebtoonLikeEntity;
import com.mogul.demo.webtoon.repository.WebtoonLikeRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebtoonLikeServiceImpl implements WebtoonLikeService{

    private final WebtoonLikeRepository webtoonLikeRepository;

    private final WebtoonRepository webtoonRepository;

    @Override
    public WebtoonLikeResponse getLike(long webtoonId, long userId) {
        WebtoonLikeResponse data = new WebtoonLikeResponse();
        data.setLike(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isPresent());
        return data;
    }

    @Override
    public boolean addLike(long webtoonId, long userId) {
        if(!webtoonRepository.existsByIdAndIsDeletedFalse(webtoonId)){
            return false;
        }
        if(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isPresent()){
            return false;
        }
        webtoonLikeRepository.save(new WebtoonLikeEntity(webtoonId, userId));
        return true;
    }

    @Override
    public boolean removeLike(long webtoonId, long userId) {
        if(!webtoonRepository.existsByIdAndIsDeletedFalse(webtoonId)){
            return false;
        }
        if(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isEmpty()){
            return false;
        }
        webtoonLikeRepository.delete(new WebtoonLikeEntity(webtoonId, userId));
        return true;
    }
}
