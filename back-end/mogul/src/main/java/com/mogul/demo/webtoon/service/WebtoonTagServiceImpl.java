package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonTagWebtoonRepository;
import com.mogul.demo.webtoon.repository.WebtoonWebtoonTagTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonTagServiceImpl implements WebtoonTagService{

    private final WebtoonWebtoonTagTagRepository webtoonWebtoonTagTagRepository;

    private final WebtoonTagWebtoonRepository webtoonTagWebtoonRepository;

    @Override
    public List<WebtoonTagResponse> findTag(long webtoonId) {
        return webtoonWebtoonTagTagRepository.findByWebtoonId(webtoonId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonWebtoonTagTagEntityToWebtoonTagResponse).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonResponse> findWebtoonByTagId(long tagId) {
        return webtoonTagWebtoonRepository.findByTagIdAndIsDeletedFalse(tagId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonTagWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }
}
