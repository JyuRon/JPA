package com.fastcampus.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void test(){
        User user = new User();
        user.setName("jyuka");
        user.setEmail("dfjk@dkf.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //User user1 = new User(null, "jyuka", "dkjfl@dkf.com", LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User("jyuka","djkfj@dofj.com");
        System.out.println(">>> " + user);

        var user3 = User.builder()
                .name("jdkf")
                .email("fkdjk!fkd.cm")
                .build();

        System.out.println(user3);
    }
}