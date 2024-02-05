package com.mogul.demo.library.service;

import com.mogul.demo.library.repository.LibraryRepository;
import com.mogul.demo.library.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibrarySubscriberSchedulServiceImpl implements LibrarySubscriberScheduleService{

    private final LibraryRepository libraryRepository;

    private final LibraryUserRepository libraryUserRepository;

    @Scheduled(fixedRate = 3600000)
    @Override
    @Transactional
    public void getSubscriberNumber(){
        Long min = libraryRepository.getMinId();
        Long max = libraryRepository.getMaxId();
        for(Long i = min; i <= max; i++){
            if(libraryRepository.findIsDeletedById(i)){
                continue;
            }
            Long subscriberNumber = libraryUserRepository.countByLibraryId(i);
            libraryRepository.updateSubscriberNumberById(i, subscriberNumber);
        }
    }

}
