package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonWebtoonTagTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonTagServiceImpl implements WebtoonTagService{

    private final WebtoonWebtoonTagTagRepository webtoonWebtoonTagTagRepository;

    @Override
    public List<WebtoonTagResponse> findTag(long webtoonId) {
        return webtoonWebtoonTagTagRepository.findByWebtoonId(webtoonId).stream().map(WebtoonMapper.INSTANCE::fromWebtoonWebtoonTagTagEntityToWebtoonResponse).collect(Collectors.toList());
    }
}
