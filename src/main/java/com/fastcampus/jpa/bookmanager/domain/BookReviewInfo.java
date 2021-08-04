package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long bookId;

    // DB상에는 ID값(Long)이 찍히지만 JPA에서는 해당정보를 바탕으로 MAPPING한다
    // optional = true  --> left outer join(합집합) --> 값이 존재하지 않을 수 있기 때문
    // optional = false --> inner join(교집합) --> 값이 무조건 존재 --> not null
    @OneToOne(optional = false)
    private Book book;

    // Float(대문자) 자료형 : NotNull = false , 추후 NullPointerException 발생 가능
    // int로 할 경우 not null = true --> default인 0.0이 초기화
    private float averageReviewScore;

    // Integer 자료형 : NotNull = false , 추후 NullPointerException 발생 가능
    // int로 할 경우 not null = true --> default인 0이 초기화

    private int reviewCount;

}
