package com.mogul.demo.chat.nickname;

import com.mogul.demo.chat.repository.NicknamePrefixRepository;
import com.mogul.demo.chat.repository.NicknamePostfixRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@Setter
public class NicknameGenerator {

    private Map<Integer, Set<String>> nicknames;

    private long prefixCount;

    private long postfixCount;

    private Random random;

    @Autowired
    NicknamePrefixRepository nickNamePrefixRepository;

    @Autowired
    NicknamePostfixRepository nicknamePostfixRepository;

    public NicknameGenerator(){
        this.nicknames = new HashMap<>();
        this.prefixCount = nickNamePrefixRepository.count();
        this.postfixCount = nicknamePostfixRepository.count();
        this.random = new Random();
    }

    public String generateNickname(int chatRoomId){
        if(!nicknames.containsKey(chatRoomId)){
            nicknames.put(chatRoomId, new HashSet<>());
        }
        StringBuilder userId = new StringBuilder();
        userId.append(nickNamePrefixRepository.findOneById(random.nextLong(prefixCount)+1).getPrefix());
        userId.append(nicknamePostfixRepository.findOneById(random.nextLong(postfixCount)+1).getPostfix());
        while(true){
            userId.append(random.nextInt(10));
            if(!nicknames.get(chatRoomId).contains(userId.toString()))
                break;
        }
        nicknames.get(chatRoomId).add(userId.toString());
        return userId.toString();
    }

    public void RemoveNickname(int chatRoomId, String userId) {
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
