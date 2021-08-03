package com.fastcampus.jpa.bookmanager.domain.listener;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import com.fastcampus.jpa.bookmanager.repository.UserHistoryRepository;
import com.fastcampus.jpa.bookmanager.support.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class UserEntityListener {
    // entity listener는 spring bean을 주입받지 못 한다
//    @Autowired
//    UserHistoryRepository userHistoryRepository;

    @PreUpdate
    @PrePersist
    public void prePersistAndPreUpdate(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);
        User user = (User) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(user.getId());
        userHistory.setEmail(user.getEmail());
        userHistory.setName(user.getName());

        userHistoryRepository.save(userHistory);
    }
}
