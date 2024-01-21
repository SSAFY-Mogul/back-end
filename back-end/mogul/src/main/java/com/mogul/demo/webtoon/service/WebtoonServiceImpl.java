package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;
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
        Map<String, List> data = new HashMap<String, List>();
        Pageable pageable = PageRequest.of(page_number, page_size);
        try{
            data.put("webtoon_hot_grade", webtoonRepository.find(pageable));

        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }
}
