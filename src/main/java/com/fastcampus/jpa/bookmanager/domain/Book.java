package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private Long publisherId;

//  mappedBy를 사용하는 이유
//	관계를 소유하는 필드입니다. 이 요소는 연결의 역방향(소유자가 아님)에서만 지정됩니다.
//  연관키를 해당테이블에서 가지지 않게 된다. --> 객체에서는 살아있음
//	book에 BookReviewInfo 정보를 insert하지 않아도 book에서도  BookReviewInfo 참조 가능
//	이 경우 toString이 순환참조를 하게 되어 stackOverflow ---> @ToString.Exclude로 해결
    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;


//    @PrePersist // insert method가 실행되기 전
//    public void prePersist(){
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate(){
//        this.updatedAt = LocalDateTime.now();
//    }
}
