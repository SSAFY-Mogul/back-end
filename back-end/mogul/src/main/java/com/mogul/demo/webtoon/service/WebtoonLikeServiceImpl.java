package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.entity.WebtoonLikeEntity;
import com.mogul.demo.webtoon.repository.WebtoonLikeRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import jakarta.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException("해당 아이디의 웹툰이 존재하지 않습니다.");
        }
        if(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isPresent()){
            throw new EntityNotFoundException("해당 사용자는 해당 웹툰에 이미 좋아요를 눌렀습니다.");
        }
        webtoonLikeRepository.save(new WebtoonLikeEntity(webtoonId, userId));
        return true;
    }

    @Override
    @Transactional
    public boolean removeLike(Long webtoonId, Long userId) {
        if(!webtoonRepository.existsByIdAndIsDeletedFalse(webtoonId)){
            throw new EntityNotFoundException("해당 아이디의 웹툰이 존재하지 않습니다.");
        }
        if(webtoonLikeRepository.findByWebtoonIdAndUserId(webtoonId, userId).isEmpty()){
            throw new EntityNotFoundException("해당 사용자는 해당 웹툰에 좋아요를 누른 적이 없습니다.");
        }
        webtoonLikeRepository.delete(new WebtoonLikeEntity(webtoonId, userId));
        return true;
    }
}
