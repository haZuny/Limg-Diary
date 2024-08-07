package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends BaseTimeEntity {
    @Column(name="USERNAME", unique = true)
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NICKNAME")
    private String nickname;
}
