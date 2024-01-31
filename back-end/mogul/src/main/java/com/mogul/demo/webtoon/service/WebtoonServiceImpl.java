package com.mogul.demo.webtoon.service;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonUpdateRequest;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonCountRepository;
import com.mogul.demo.webtoon.repository.WebtoonLibraryRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonServiceImpl implements WebtoonService{

    private final WebtoonRepository webtoonRepository;

    private final WebtoonCountRepository webtoonCountRepository;

    private final WebtoonLibraryRepository webtoonLibraryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize){
        return webtoonRepository.findByIsDeletedFalseOrderByGradeDesc(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize) {
        return webtoonCountRepository.findAllByIsDeletedFalseOrderByCount(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonCountEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize) {
        return webtoonRepository.findByIsDeletedFalseOrderByTitle(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonAllByGenre(String genre, int pageNumber, int pageSize) {
        return webtoonRepository.findByGenreAndIsDeletedFalseOrderByTitleAsc(genre, PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WebtoonDetailResponse findWebtoonById(Long webtoonId) {
        return WebtoonMapper.INSTANCE.fromWebtoonEntityToWebtoonDtailResponse(webtoonRepository.findOneByIdAndIsDeletedFalse(webtoonId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByLibraryId(Long libraryId) {
        return webtoonLibraryRepository.findAllByLibraryIdAndIsDeletedFalseOrderByTitleAsc(libraryId)
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonLibraryEntityToWebtoonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Long webtoonId) {
        return webtoonRepository.existsByIdAndIsDeletedFalse(webtoonId);
    }

    @Override
    @Transactional
    public void modifyWebtoonGrade(Long id, Float grade, Float drawingGrade, Float storyGrade, Float directingGrade) {
        webtoonRepository.updateGrade(id, grade, drawingGrade, storyGrade, directingGrade);
    }

    @Override
    public boolean addWebtoon(WebtoonAddRequest webtoonAddRequest) {
        return false;
    }

    @Override
    public boolean removeWebtoon(Long id) {
        return false;
    }

    @Override
    public boolean modifyWebtoon(WebtoonUpdateRequest webtoonUpdateRequest) {
        return false;
    }
}
