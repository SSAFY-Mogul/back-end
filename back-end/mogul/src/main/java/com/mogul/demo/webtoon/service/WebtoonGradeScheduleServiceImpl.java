package com.mogul.demo.webtoon.service;

import com.mogul.demo.review.repository.ReviewRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WebtoonGradeScheduleServiceImpl implements WebtoonGradeScheduleService {

    private final WebtoonRepository webtoonRepository;

    private final ReviewRepository reviewRepository;

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void getGrade(){
        Long min = webtoonRepository.findMinId();
        Long max = webtoonRepository.findMaxId();
        for(Long i=min; i<=max; i++){
            if(webtoonRepository.findIsDeletedById(i)){
                continue;
            }
            float storyGrade = reviewRepository.avgStoryScoreByWebtoonId(i);
            float directionGrade = reviewRepository.avgDirectingScoreByWebtoonId(i);
            float drawingGrade = reviewRepository.avgDrawingScoreByWebtoonId(i);
            float grade = (storyGrade+directionGrade+drawingGrade)/3.0f;
            webtoonRepository.updateGrade(i, grade, drawingGrade, storyGrade, directionGrade);
        }
    }
}