package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@fastcampus.com");

        entityManager.persist(user);
        entityManager.detach(user);   // 영속성 -> 준영속성 (영속성에서 제외하고 꺼내옴)

        user.setName("newUserAfterPersist");
        entityManager.merge(user);    // 준영속성 -> 영속성
        entityManager.flush();      // clear사용시 flush사용을 권고
        entityManager.clear();   // detach 상태 : clear, close도 있음, 변경 예약이 예정되있던 사항도 모두 drop 시킴

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1); // 영속성 상태인 entity에서만  동작

        entityManager.clear();  // remove까지 예약포함
    }
}
