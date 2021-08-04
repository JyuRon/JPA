package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString(callSuper = true) // 상속받는 클래스 까지 toString을 하겠다
@EqualsAndHashCode(callSuper = true)
@Builder
@EntityListeners(value = {UserEntityListener.class})


@Entity // 해당 객체가 JPA에서 관리함을 알림
public class User extends BaseEntity{
    @Id
    //@GeneratedValue // 자동으로 증가하는 숫자값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull  //lombok의 어노테이션
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    // NullPointerException 방지, 안해도 문제는 없긴 함
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private List<UserHistory> userHistories = new ArrayList<>();



//    @Column(updatable = false)
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;


//    @Transient // 영속성 처리에서 제외됨 : DB에는 반영X
//    private String testData;

}
