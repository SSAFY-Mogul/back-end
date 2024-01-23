package com.mogul.demo.webtoon.service;

import com.mogul.demo.review.repository.ReviewRepository;
import com.mogul.demo.webtoon.response.WebtoonAllPageRes;
import com.mogul.demo.webtoon.response.WebtoonDetailPageRes;
import com.mogul.demo.webtoon.response.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.response.WebtoonMainPageRes;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import com.mogul.demo.webtoon.repository.WebtoonCntRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebtoonServiceImpl implements WebtoonService{

    @Autowired
    WebtoonRepository webtoonRepository;

    @Autowired
    WebtoonCntRepository webtoonCntRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public WebtoonMainPageRes findWebtoonMain(int pageNumber, int pageSize) {
        WebtoonMainPageRes res = new WebtoonMainPageRes();
        Map<String, List> data = new HashMap<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try{
            data.put("webtoons_hot_grade", webtoonRepository.findMain(pageable));
            data.put("webtoons_hot_library", webtoonCntRepository.findMain(pageable));
            res.setCode("200");
            res.setDescription("웹툰 메인 페이지 데이터 읽기 성공 : 인기 웹툰 목록과 서재에 많이 담긴 웹툰 목록");
            res.setData(data);
        }catch(SQLException e){
            e.printStackTrace();
            res.setCode("500");
            res.setDescription("웹툰 메인 페이지 데이터 읽기 실패");
        }
        return res;
    }

    @Override
    public WebtoonAllPageRes findWebtoonAll(int pageNumber, int pageSize) {
        WebtoonAllPageRes res = new WebtoonAllPageRes();
        List<WebtoonEntity> data;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try{
            data = webtoonRepository.findAllByTitle(pageable);
            res.setCode("200");
            res.setDescription("웹툰 모두 보기 페이지 데이터 읽기 성공 : 모든 웹툰 목록 이름 순");
            res.setData(data);
        }catch(SQLException e){
            e.printStackTrace();
            res.setCode("500");
            res.setDescription("웹툰 모두 보기 페이지 데이터 읽기 실패");
        }
        return res;
    }

    @Override
    public WebtoonGenrePageRes findWebtoonByGenre(String genre, int pageNumber, int pageSize) {
        WebtoonGenrePageRes res = new WebtoonGenrePageRes();
        List<WebtoonEntity> data;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try{
            webtoonRepository.findAllByGenre(genre, pageable);
            res.setCode("200");
            res.setDescription("웹툰 장르별 보기 페이지 데이터 읽기 성공 : " + genre + "장르의 웹툰 목록 이름 순");
        }catch(SQLException e){
            e.printStackTrace();
            res.setCode("500");
            res.setDescription("웹툰 장르별 보기 페이지 데이터 읽기 실패");
        }
        return res;
    }

    @Override
    public WebtoonDetailPageRes findWebtoonDetail(long webtoonId, int pageNumber, int pageSize) {
        WebtoonDetailPageRes res = new WebtoonDetailPageRes();
        Map<String, Object> data = new HashMap<>();
        try{
            data.put("webtoon", webtoonRepository.find(webtoonId));
            data.put("reviews", reviewRepository.findByWebtoonId(webtoonId));
            res.setCode("200");
            res.setDescription("웹툰 상세 보기 페이지 데이터 읽기 성공 : 웹툰 상세와 웹툰의 리뷰 최신순, 웹툰이 포함된 서재 팔로워 순");
            res.setData(data);
        }catch(SQLException e){
            e.printStackTrace();
            res.setCode("500");
            res.setDescription("웹툰 상세 보기 페이지 데이터 읽기 실패");
        }
        return res;
    }
}
