package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonAllPageRes;
import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;
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

    @Override
    public WebtoonMainPageRes findWebtoonMain(int page_number, int page_size) {
        WebtoonMainPageRes res = new WebtoonMainPageRes();
        Map<String, List> data = new HashMap<>();
        Pageable pageable = PageRequest.of(page_number, page_size);
        try{
            data.put("webtoon_hot_grade", webtoonRepository.find(pageable));
            data.put("webtoon_hot_library", webtoonCntRepository.find(pageable));
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
}
