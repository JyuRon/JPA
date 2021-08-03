package com.fastcampus.jpa.bookmanager.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MyEntityListener {

    @PrePersist
    // Entity에서는 파라미터가 없어도 this를 통해서 사용 가능하지만
    // listener의 경우 object로 명시해줘야함
    public void prePersist(Object o){
        if(o instanceof Auditable){
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }

    }

    @PreUpdate
    public void preUpdate(Object o){
        if(o instanceof Auditable) {
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
