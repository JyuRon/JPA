package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString(callSuper = true) // 상속받는 클래스 까지 toString을 하겠다
@EqualsAndHashCode(callSuper = true)
@Builder
@EntityListeners(value = {UserEntityListener.class})
// @Table(name="user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
// 해당 어노테이션을 작성할 경우 primary key field를 만들어야한다.
@Entity // 해당 객체가 JPA에서 관리함을 알림
public class User extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue // 자동으로 증가하는 숫자값
    private Long id;

    @NonNull
    private String name;

    @NonNull  //lombok의 어노테이션
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

//    @Column(updatable = false)
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;


   // @Transient // 영속성 처리에서 제외됨 : DB에는 반영X
    //private String testData;

    //@OneToMany(fetch = FetchType.EAGER)
    //private List<Address> address;

}
