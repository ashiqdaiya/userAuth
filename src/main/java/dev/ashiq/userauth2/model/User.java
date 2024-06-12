package dev.ashiq.userauth2.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class User extends BaseModel{

    private String email;
    private String pwd;
    private String fullName;
//    private byte[] jsonAsString;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    private List<UserSession> userSession;


}
