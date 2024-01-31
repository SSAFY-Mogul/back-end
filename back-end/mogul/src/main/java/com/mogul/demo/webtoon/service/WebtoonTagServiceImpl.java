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
        return webtoonWebtoonTagTagRepository.findByWebtoonId(webtoonId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonWebtoonTagTagEntityToWebtoonTagResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonByTagId(Long tagId) {
        return webtoonTagWebtoonRepository.findByTagIdAndIsDeletedFalse(tagId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonTagWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean addTag(WebtoonTagAddRequest webtoonTagAddRequest) {
        if(webtoonTagRepository.existsByTag(webtoonTagAddRequest.getTag())){
            return false;
        }
        webtoonTagRepository.save(WebtoonMapper.INSTANCE.fromWebtoonTagAddRequestToWebtoonTagEntity(webtoonTagAddRequest));
        return true;
    }

    @Override
    @Transactional
    public boolean removeWebtoonTag(Long id) {
        Optional<WebtoonTagEntity> optionalWebtoonTagEntity = webtoonTagRepository.findById(id);
        if(optionalWebtoonTagEntity.isEmpty())
            return false;
        webtoonTagRepository.delete(optionalWebtoonTagEntity.get());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonTagResponse> findWebtoonTag() {
        return webtoonTagRepository.findAll().stream().map(WebtoonMapper.INSTANCE::fromWEbtoonTagEntityToWebtoonTagResponse).collect(Collectors.toList());
    }

}
