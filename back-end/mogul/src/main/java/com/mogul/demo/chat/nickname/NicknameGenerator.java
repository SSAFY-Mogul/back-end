package com.mogul.demo.chat.nickname;

import com.mogul.demo.chat.repository.NicknamePrefixRepository;
import com.mogul.demo.chat.repository.NicknamePostfixRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@Setter
public class NicknameGenerator {

    private Map<Long, Set<String>> nicknames;

    private long prefixCount;

    private long postfixCount;

    private Random random;

    @Autowired
    private NicknamePrefixRepository nickNamePrefixRepository;

    @Autowired
    private NicknamePostfixRepository nicknamePostfixRepository;

    @Builder
    public NicknameGenerator(){
        this.nicknames = new HashMap<>();
        this.random = new Random();
    }

    public String generateNickname(Long chatRoomId){

        prefixCount = nickNamePrefixRepository.count();
        postfixCount = nicknamePostfixRepository.count();

        if(!nicknames.containsKey(chatRoomId)){
            nicknames.put(chatRoomId, new HashSet<>());
        }
        StringBuilder userId = new StringBuilder();
        userId.append(nickNamePrefixRepository.findOneById(Math.abs(random.nextLong())%prefixCount+1).getPrefix());
        userId.append(nicknamePostfixRepository.findOneById(Math.abs(random.nextLong())%prefixCount+1).getPostfix());
        while(true){
            userId.append(random.nextInt(10));
            if(!nicknames.get(chatRoomId).contains(userId.toString()))
                break;
        }
        nicknames.get(chatRoomId).add(userId.toString());
        return userId.toString();
    }

    public void removeNickname(Long chatRoomId, String userId) {
        if(nicknames.containsKey(chatRoomId)){
            if(nicknames.get(chatRoomId).contains(userId)){
                nicknames.remove(userId);
            }
            if(nicknames.get(chatRoomId).isEmpty()){
                nicknames.remove(chatRoomId);
            }
        }
    }
}
