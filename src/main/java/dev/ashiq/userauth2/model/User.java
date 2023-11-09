package dev.ashiq.userauth2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User extends BaseModel{

    private String email;
    private String pwd;
    private String fullName;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    private List<UserSession> userSession;


}
