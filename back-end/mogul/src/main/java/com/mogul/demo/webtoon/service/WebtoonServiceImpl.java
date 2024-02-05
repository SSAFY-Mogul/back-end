package com.mogul.demo.webtoon.service;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonUpdateRequest;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagEntity;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.repository.WebtoonCountRepository;
import com.mogul.demo.webtoon.repository.WebtoonLibraryRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import com.mogul.demo.webtoon.repository.WebtoonWebtoonTagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonServiceImpl implements WebtoonService{

    private final WebtoonRepository webtoonRepository;

    private final WebtoonCountRepository webtoonCountRepository;

    private final WebtoonLibraryRepository webtoonLibraryRepository;

    private final WebtoonWebtoonTagRepository webtoonWebtoonTagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize){
        List data =  webtoonRepository.findByIsDeletedFalseOrderByGradeDesc(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("웹툰이 존재하지 않습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize) {
        List data = webtoonCountRepository.findAllByIsDeletedFalseOrderByCount(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonCountEntityToWebtoonResponse).collect(Collectors.toList());
        if (data.isEmpty()){
            throw new EntityNotFoundException("웹툰이 담긴 서재가 없거나 웹툰이 없습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize) {
        List data =  webtoonRepository.findByIsDeletedFalseOrderByTitle(PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("웹툰이 없습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonAllByGenre(String genre, int pageNumber, int pageSize) {
        List data =  webtoonRepository.findByGenreAndIsDeletedFalseOrderByTitleAsc(genre, PageRequest.of(pageNumber, pageSize))
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 장르의 웹툰이 존재하지 않습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public WebtoonDetailResponse findWebtoonById(Long webtoonId) {
        Optional<WebtoonEntity> data = webtoonRepository.findOneByIdAndIsDeletedFalse(webtoonId);
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 아이디의 웹툰이 존재하지 않습니다.");
        }
        return WebtoonMapper.INSTANCE.fromWebtoonEntityToWebtoonDetailResponse(data.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByLibraryId(Long libraryId) {
        List data =  webtoonLibraryRepository.findAllByLibraryIdAndIsDeletedFalseOrderByTitleAsc(libraryId)
                .stream().map(WebtoonMapper.INSTANCE::fromWebtoonLibraryEntityToWebtoonResponse).collect(Collectors.toList());
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Long webtoonId) {
        return webtoonRepository.existsByIdAndIsDeletedFalse(webtoonId);
    }

    @Override
    @Transactional
    public boolean addWebtoon(WebtoonAddRequest webtoonAddRequest) {
        WebtoonEntity webtoonEntity = webtoonRepository.save(WebtoonMapper.INSTANCE.fromWebtoonAddREquestToWebtoonEntity(webtoonAddRequest));
        Long webtoonId = webtoonEntity.getId();
        for(Long tagId : webtoonAddRequest.getTags()){
            webtoonWebtoonTagRepository.save(new WebtoonWebtoonTagEntity(webtoonId, tagId));
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeWebtoon(Long id) {
        if(!webtoonRepository.existsByIdAndIsDeletedFalse(id)){
            throw new EntityNotFoundException("해당 아이디의 웹툰이 존재하지 않습니다.");
        }
        webtoonRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean modifyWebtoon(WebtoonUpdateRequest webtoonUpdateRequest) {
        if(!webtoonRepository.existsById(webtoonUpdateRequest.getId())){
            throw new EntityNotFoundException("해당 아이디의 웹툰이 존재하지 않습니다.");
        }
        webtoonRepository.save(WebtoonMapper.INSTANCE.fromWebtoonUpdateRequestToWebtoonEntity(webtoonUpdateRequest));
        return true;
    }
}
