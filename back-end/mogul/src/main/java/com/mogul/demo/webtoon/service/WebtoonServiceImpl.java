package com.mogul.demo.webtoon.service;

import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.LibraryWebtoonThumbnailRepository;
import com.mogul.demo.review.mapper.ReviewMapper;
import com.mogul.demo.review.repository.ReviewRepository;
import com.mogul.demo.webtoon.dto.WebtoonDto;
import com.mogul.demo.webtoon.mapper.WebtoonMapper;
import com.mogul.demo.webtoon.response.WebtoonAllPageRes;
import com.mogul.demo.webtoon.response.WebtoonDetailPageRes;
import com.mogul.demo.webtoon.response.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.response.WebtoonMainPageRes;
import com.mogul.demo.webtoon.repository.WebtoonCountRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebtoonServiceImpl implements WebtoonService{

    @Autowired
    WebtoonRepository webtoonRepository;

//    @Autowired
//    WebtoonCountRepository webtoonCountRepository;
//
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Autowired
//    LibraryWebtoonThumbnailRepository libraryWebtoonThumbnailRepository;
//
//    @Override
//    public WebtoonMainPageRes findWebtoonMain(int pageNumber, int pageSize) {
//        WebtoonMainPageRes res = new WebtoonMainPageRes();
//        Map<String, List> data = new HashMap<>();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        try{
//            data.put("webtoons_hot_grade", webtoonRepository.findMain(pageable).stream().map(webtoonMapper::fromWebtoonEntityToWebtoonDto).collect(Collectors.toList()));
//            data.put("webtoons_hot_library", webtoonCountRepository.findMain(pageable).stream().map(webtoonMapper::fromWebtoonCountEntityToWebtoonDto).collect(Collectors.toList()));
//            res.setData(data);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public WebtoonAllPageRes findWebtoonAll(int pageNumber, int pageSize) {
//        WebtoonAllPageRes res = new WebtoonAllPageRes();
//        List<WebtoonDto> data;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        try{
//            data = webtoonRepository.findAllByTitle(pageable).stream().map(webtoonMapper::fromWebtoonEntityToWebtoonDto).collect(Collectors.toList());
//            res.setData(data);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public WebtoonGenrePageRes findWebtoonByGenre(String genre, int pageNumber, int pageSize) {
//        WebtoonGenrePageRes res = new WebtoonGenrePageRes();
//        List<WebtoonDto> data;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        try{
//            data = webtoonRepository.findAllByGenre(genre, pageable).stream().map(webtoonMapper::fromWebtoonEntityToWebtoonDto).collect(Collectors.toList());
//            res.setData(data);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public WebtoonDetailPageRes findWebtoonDetail(long webtoonId, int pageNumber, int pageSize) {
//        WebtoonDetailPageRes res = new WebtoonDetailPageRes();
//        Map<String, Object> data = new HashMap<>();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        try{
//            data.put("webtoon", webtoonMapper.fromWebtoonEntityToWebtoonDtailDto(webtoonRepository.find(webtoonId)));
//            data.put("reviews", reviewRepository.findByWebtoonId(webtoonId, pageable).stream().map(reviewMapper::fromReviewEntityToReviewDto).collect(Collectors.toList()));
//            data.put("librarys", libraryWebtoonThumbnailRepository.findAllByWebtoonId(webtoonId, pageable).stream().map(libraryMapper::fromLibraryWebtoonThumbnailEntityToLibraryDto).collect(Collectors.toList()));
//            res.setData(data);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return res;
//    }

    @Override
    public List<WebtoonDto> findWebtoonOrderByGrade(int pageNumber, int pageSize){
        return webtoonRepository.findAllByIsDeletedFalseOrderByGrade(PageRequest.of(pageNumber, pageSize)).get().stream().map(WebtoonMapper.INSTANCE::fromWebtoonEntityToWebtoonDto).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonDto> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize) {
        return null;
    }
}
