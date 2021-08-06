package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Gender;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    //@Transactional
    void crud(){
        System.out.println("---------------findAll-----------------------");
        // findAll
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        users.forEach(System.out::println);

        System.out.println("-----------------findAllById---------------------");

        // findAllById
        // Lists : test용 method
        users = userRepository.findAllById(Lists.newArrayList(1L, 3L,5L));
        users.forEach(System.out::println);

        System.out.println("------------------saveAll--------------------");

        // saveAll
        User user1 = new User("jack","jack@fastcampus.com");
        User user2 = new User("steve", "steve@naver.com");
        userRepository.saveAll(Lists.newArrayList(user1, user2));

        users = userRepository.findAll();
        users.forEach(System.out::println);

        System.out.println("--------------save------------------------");

        // save
        System.out.println(user1);
        User user3 = new User("updateName", "dfjkl@dsjfo.com");
        userRepository.save(user3);
        users = userRepository.findAll();
        users.forEach(System.out::println);

        System.out.println("--------------getOne------------------------");

        // getOne : @Transactional을 사용해야함
//        User user4 = userRepository.getOne(1L);
//        System.out.println(user4);

        System.out.println("--------------findById------------------------");

        // findById
        User user5 = userRepository.findById(2L).orElse(null);
        System.out.println(user5);

        System.out.println("--------------saveAndFlush------------------------");

        // saveAndFlush : commit 개념인가?? 현재로써는 동작을 확인하기 어렵다.
        userRepository.saveAndFlush(new User("new martin","newmartin@fdkj.com"));
        userRepository.findAll().forEach(System.out::println);

        System.out.println("--------------count------------------------");

        // count
        long count = userRepository.count();
        System.out.println(count);


        System.out.println("--------------existsById------------------------");

        // existsByID : count를 사용하여 값을 불러온다.
        boolean exists = userRepository.existsById(1L);
        System.out.println(exists);


        System.out.println("--------------delete------------------------");

        // delete : select문이 2번 실행
        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));


        System.out.println("--------------deleteById------------------------");

        // deleteByID : select문이 1번 실행
        userRepository.deleteById(2l);
        userRepository.findAll().forEach(System.out::println);


        System.out.println("--------------deleteAll------------------------");

        // deleteAll :  성능이슈 --> select문이 많이 호출
        // userRepository.deleteAll();
        userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(3l,4l)));
        userRepository.findAll().forEach(System.out::println);


        System.out.println("--------------deleteInBatch------------------------");

        // deleteInBatch : 성능이슈 해결 --> select문 안불러오고 drop 문도 하나
        userRepository.deleteInBatch(userRepository.findAllById(Lists.newArrayList(5l,6l)));
        userRepository.findAll().forEach(System.out::println);

        // userRepository.deleteAllInBatch();


        System.out.println("-----------Paging----------");
        List<User> addUser = new ArrayList<>();

        for(int i=0;i<20;i++)
            addUser.add(new User("add"+i,"sample@add.com"));

        userRepository.saveAll(addUser);
        // System.out.println(userRepository.count());


        // Paging
        // page 0부터 시작
        Page<User> userPage = userRepository.findAll(PageRequest.of(7,3));
        System.out.println("page: " + userPage);
        System.out.println("totalElements: "+ userPage.getTotalElements());
        System.out.println("totalPagesL " + userPage.getTotalPages());
        System.out.println("numberOfElements : " + userPage.getNumberOfElements()); // 현재페이지 가져온 정보
        System.out.println("sort :" + userPage.getSort());
        System.out.println("size : " + userPage.getSize());

        userPage.getContent().forEach(System.out::println);


        System.out.println("-----------Example----------");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")   // 없으면 existMatch를 한다. --> mo를 찾고
                .withMatcher("email",endsWith());  // like add.com%

        // Prob : false Entity, 비교대상객체
        // Example.of : prob로 넣은 entity에 대해 같은 값이 있는지를 검색한다
        // matcher의 경우 like절로 변화하여 검색한다.
        Example<User> example = Example.of(new User("mo","add.com"),matcher);

        userRepository.findAll(example).forEach(System.out::println);



        System.out.println("-----------Example2----------");
        // 비교할 단어
        User searchUser = new User();
        searchUser.setEmail("mpl");

        // like절 생성
        ExampleMatcher matcher1 = ExampleMatcher.matching()
                .withMatcher("email",contains()); // like %mpl%

        // 쿼리 생성
        Example<User> search = Example.of(searchUser,matcher1);

        // 쿼리 실행
        userRepository.findAll(search).forEach(System.out::println);



        System.out.println("-----------Update----------");

        // insert문 실행
        userRepository.save(new User("david","dfkdf@naver.com"));

        User user = userRepository.findById(25L).orElseThrow(RuntimeException::new);
        user.setEmail("updateEmail@naver.com");

        // select문 실행후 update
        userRepository.save(user);

    }

    @Test
    void select(){
        // userRepository.findByName("martin").forEach(System.out::println);
//        System.out.println(userRepository.findByName("dennis"));
//
//        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println("getByEmail : " + userRepository.getByEmail("martin@fastcampus.com"));
//        System.out.println("readByEmail : " + userRepository.readByEmail("martin@fastcampus.com"));
//        System.out.println("queryByEmail : " + userRepository.queryByEmail("martin@fastcampus.com"));
//        System.out.println("searchByEmail : " + userRepository.searchByEmail("martin@fastcampus.com"));
//        System.out.println("streamByEmail : " + userRepository.streamByEmail("martin@fastcampus.com"));
//        System.out.println("findUserByEmail : " + userRepository.findUserByEmail("martin@fastcampus.com"));
//        System.out.println("findSomethingByEmail : " + userRepository.findSomethingByEmail("martin@fastcampus.com"));
//
//
//        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));
//        System.out.println("findFirst1ByName : " + userRepository.findFirst1ByName("martin"));
//        // System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin"));


//        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@fastcampus.com","martin"));
//        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("martin2@fastcampus.com","martin"));
//
//        // Before/After : 시간에 대한 where절 --> 하지만 숫자값의 비교도 가능은하다
//        // 가독성등 여러가지의 이유로 날짜만 대부분 사용
//        System.out.println("findByCreatAtAfter : " + userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
//        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(4L));
//
//
//        // 비교연산자의 경우 숫자, 날짜 모두 가능
//        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
//        System.out.println("findByCreatedAtGreaterThanEqual : " + userRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1L)));
//
//
//        System.out.println("findByCreateAtBetween : " + userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1l)));
//
//        // id값의 경우 1~3 총 3개가 출력됨
//        // between : 양끝단의 값을 포함
//        System.out.println("findByIdBetween : " + userRepository.findByIdBetween(1L,3L));
//        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : " + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L,3L));
//
//
//        System.out.println("findByIdNotNull : " + userRepository.findByIdNotNull());
//
//        // IsNotEmpty can only be used on collection properties!
//        System.out.println("findByAddressIsNotEmpty : " + userRepository.findByAddressIsNotEmpty());


        System.out.println("findByNameIn : " + userRepository.findByNameIn(Lists.newArrayList("martin","dennis")));

        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("tin"));
        System.out.println("findByNameContaining : " + userRepository.findByNameContaining("art"));
        System.out.println("findByNameLike : " + userRepository.findByNameLike("%art%"));


    }

    @Test
    void pagingAndSortingTest(){
        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));
        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin"));
        System.out.println("findTop1ByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));

        // 단일 또는 리스트로 받을 수 있음
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));

        // page = zero base
        Page<User> test = userRepository
                .findByName("martin", PageRequest.of(0,1,Sort.by(Sort.Order.desc("id"))));
        System.out.println("findByNameWithPaging : " + test.getContent());

        test = userRepository
            .findByName("martin",test.nextPageable());
        System.out.println(test);
        System.out.println(test.getContent());


    }

    private Sort getSort(){
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.asc("email"),
                Sort.Order.desc("createdAt"),
                Sort.Order.asc("updatedAt")
        );
    }

    @Test
    void insertAndUpdateTest(){

        User user = new User();
        user.setName("MARTIN");
        user.setEmail("martin2@fastcamplus.com");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrrtin");

        userRepository.save(user2);
    }


    @Test
    void enumTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        // Gender : index번호가 출력된다. 이것또한 어노테이션으로 변경가능
        System.out.println(userRepository.findRawRecord().get("gender"));
    }


    @Test
    void listenerTest(){
        User user = new User();
        user.setEmail("martin2@naver.com");
        user.setName("martin");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrrrtin");

        userRepository.save(user2);

        userRepository.deleteById(4L);
    }

    @Test
    void prePersistTest(){
        User user = new User();
        user.setEmail("martin2@naver.com");
        user.setName("martin");
        //user.setCreatedAt(LocalDateTime.now());
        //user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        System.out.println(userRepository.findByEmail("martin2@naver.com"));
    }

    @Test
    void preUpdateTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        System.out.println("as-is : " + user);

        user.setName("martin22");

        userRepository.save(user);

        System.out.println("to-be : " + userRepository.findAll().get(0));

    }

    @Test
    void userHistoryTest(){
        User user = new User();
        user.setEmail("martin-new@naver.com");
        user.setName("martin-new");

        userRepository.save(user);

        user.setName("martin-new-new");

        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void userRelationTest(){
        User user = new User();
        user.setName("david");
        user.setEmail("david@naver.com");
        user.setGender(Gender.MALE);

        userRepository.save(user);

        user.setName("daniel");
        userRepository.save(user);

        user.setEmail("daniel@gmail.com");
        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);

//        List<UserHistory> result = userHistoryRepository.findByUserId(
//                userRepository.findByEmail("daniel@gmail.com").getId());


        List<UserHistory> result = userRepository.findByEmail("daniel@gmail.com").getUserHistories();
        result.forEach(System.out::println);

        System.out.println("UserHistory.getUser() : " + userHistoryRepository.findAll().get(0).getUser());
    }



}