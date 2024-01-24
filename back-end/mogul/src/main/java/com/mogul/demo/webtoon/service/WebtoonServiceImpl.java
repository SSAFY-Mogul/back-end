package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonCountRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebtoonServiceImpl implements WebtoonService{

    @Autowired
    WebtoonRepository webtoonRepository;

    @Autowired
    WebtoonCountRepository webtoonCountRepository;

    @Override
    public List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize){
        return webtoonRepository.findAllByIsDeletedFalseOrderByGrade(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize) {
        return webtoonCountRepository.getWebtoonCountEntityByIsDeletedFalseOrderByCount(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonCountEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize) {
        return webtoonRepository.getWebtoonByIsDeletedFalseOrderByTitle(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }
}
