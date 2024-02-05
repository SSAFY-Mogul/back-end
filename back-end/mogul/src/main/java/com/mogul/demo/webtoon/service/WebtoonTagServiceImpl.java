package com.mogul.demo.webtoon.service;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonTagAddRequest;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.entity.WebtoonTagEntity;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonTagRepository;
import com.mogul.demo.webtoon.repository.WebtoonTagWebtoonRepository;
import com.mogul.demo.webtoon.repository.WebtoonWebtoonTagTagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonTagServiceImpl implements WebtoonTagService{

    private final WebtoonWebtoonTagTagRepository webtoonWebtoonTagTagRepository;

    private final WebtoonTagWebtoonRepository webtoonTagWebtoonRepository;

    private final WebtoonTagRepository webtoonTagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonTagResponse> findTag(Long webtoonId) {
        List data =  webtoonWebtoonTagTagRepository.findByWebtoonId(webtoonId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonWebtoonTagTagEntityToWebtoonTagResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 아이디의 웹툰에 달린 태그가 없습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonByTagId(Long tagId) {
        List data =  webtoonTagWebtoonRepository.findByTagIdAndIsDeletedFalse(tagId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonTagWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 태그가 달린 웹툰이 존재하지 않습니다.");
        }
        return data;
    }

    @Override
    @Transactional
    public Long addTag(WebtoonTagAddRequest webtoonTagAddRequest) {
        if(webtoonTagRepository.existsByTag(webtoonTagAddRequest.getTag())){
            throw new EntityNotFoundException("이미 존재하는 태그 입니다.");
        }
        return webtoonTagRepository.save(WebtoonMapper.INSTANCE.fromWebtoonTagAddRequestToWebtoonTagEntity(webtoonTagAddRequest)).getId();
    }

    @Override
    @Transactional
    public boolean removeWebtoonTag(Long id) {
        Optional<WebtoonTagEntity> optionalWebtoonTagEntity = webtoonTagRepository.findById(id);
        if(optionalWebtoonTagEntity.isEmpty())
            throw new EntityNotFoundException("해당 아이디의 웹툰에 태그가 존재하지 않습니다.");
        webtoonTagRepository.delete(optionalWebtoonTagEntity.get());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonTagResponse> findWebtoonTag() {
        return webtoonTagRepository.findAll().stream().map(WebtoonMapper.INSTANCE::fromWebtoonTagEntityToWebtoonTagResponse).collect(Collectors.toList());
    }

}
