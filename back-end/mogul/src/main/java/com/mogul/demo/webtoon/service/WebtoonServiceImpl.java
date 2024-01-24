package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
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
        return webtoonCountRepository.findAllByIsDeletedFalseOrderByCount(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonCountEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize) {
        return webtoonRepository.findAllByIsDeletedFalseOrderByTitle(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonResponse> findWebtoonAllByGenre(String genre, int pageNumber, int pageSize) {
        return webtoonRepository.findAllByGenreAndIsDeletedFalseOrderByTitle(genre, PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    public WebtoonDetailResponse findWebtoonById(long webtoonId) {
        return WebtoonMapper.INSTANCE.fromWebtoonEntityToWebtoonDtailResponse(webtoonRepository.findOneByIdAndIsDeletedFalse(webtoonId).get());
    }
}
