package dev.ashiq.userauth2.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSession extends BaseModel{
    @Lob
    @Column(length=1000000)
    private String token;


    private Date expireAt;


    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;

    public UserSession(Date expireAt) {
        this.expireAt=expireAt;
    }


//    public UserSession(Date expireAt){
//        this.expireAt=expireAt;
//    }


//    public UserSession(Date expireAt) {
//        this.expireAt = expireAt;
//    }
}
