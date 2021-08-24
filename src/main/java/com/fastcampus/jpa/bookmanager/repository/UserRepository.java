package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// Entity type, Entity의 primary 자료형

public interface UserRepository extends JpaRepository<User,Long> {

    // 자료형은 여러가지가 올 수 있다
    // List<User> findByName(String name);
    // User findByName(String name);
    // Optional<User> findByName(String name);

    // Equals의 여러가지 표현법
    Set<User> findByName(String name);
    Set<User> findUserByName(String name);
    Set<User> findUserByNameIs(String name);
    Set<User> findUserByNameEquals(String name);



    // method naming시 여러가지 접두사 표현법
    User findByEmail(String email);
    User getByEmail(String email);
    User readByEmail(String email);
    User queryByEmail(String email);
    User searchByEmail(String email);
    User streamByEmail(String email);
    User findUserByEmail(String email);


    // 숫자, 시간 비교연산
    // Before/After : 시간에 대한 where절 --> 하지만 숫자값의 비교도 가능은하다 ,가독성등 여러가지의 이유로 날짜만 대부분 사용
    List<User> findByCreatedAtAfter(LocalDateTime yesterday);
    List<User> findByIdAfter(Long id);
    List<User> findByCreatedAtGreaterThan(LocalDateTime yesterday);
    List<User> findByCreatedAtGreaterThanEqual(LocalDateTime yesterday);
    List<User> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);
    List<User> findByIdBetween(Long id1, Long id2);



    // 2개 이상의 where 표현
    List<User> findByEmailAndName(String email, String name);
    List<User> findByEmailOrName(String email, String name);
    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long id1, Long id2);
    List<User> findByNameIn(List<String> name);



    // Null / NotNull 조건 검색
    List<User> findByIdNotNull();



    // empty 조건 검색
    // IsNotEmpty can only be used on collection properties!
    // 해당 내용과는 다름 name is not null and name != ''
    // List<User> findByAddressIsNotEmpty();



    // True / False 조건 검색
    // List<User> findByNameIsTrue();




    // like절
    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContaining(String name);
    List<User> findByNameLike(String name);     // name = %art%



    // findXXX : keyword 표현
    // JPA에서 인식하지 않는 keyword(Filter)
    User findSomethingByEmail(String email);
    List<User> findLast1ByName(String name);



    // limit절
    // findFirst88ByName(String name)
    List<User> findFirst1ByName(String name);
    List<User> findTop1ByName(String name);



    // Order절
    List<User> findTopByNameOrderByIdDesc(String name); // Default limit = 1
    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);   // 2개 이상의 order 조건
    List<User> findFirstByName(String name, Sort sort);


    //page
    Page<User> findByName(String name, Pageable pageable);

    //enum
    @Query(value = "select * from user limit 1", nativeQuery = true)
    Map<String, Object> findRawRecord();

    @Query(value = "select * from user", nativeQuery = true)
    List<Map<String, Object>> findAllRawRecord();
}
