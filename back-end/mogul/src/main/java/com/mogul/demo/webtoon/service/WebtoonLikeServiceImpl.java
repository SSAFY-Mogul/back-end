package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.entity.WebtoonLikeEntity;
import com.mogul.demo.webtoon.repository.WebtoonLikeRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebtoonLikeServiceImpl implements WebtoonLikeService{

    private final WebtoonLikeRepository webtoonLikeRepository;

    private final WebtoonRepository webtoonRepository;

    @Override
    @Transactional(readOnly = true)
    public WebtoonLikeResponse getLike(Long webtoonId, Long userId) {
        WebtoonLikeResponse data = new WebtoonLikeResponse();
        data.setLike(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isPresent());
        return data;
    }

    @Override
    @Transactional
    public boolean addLike(Long webtoonId, Long userId) {
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
    @Transactional
    public boolean removeLike(Long webtoonId, Long userId) {
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
