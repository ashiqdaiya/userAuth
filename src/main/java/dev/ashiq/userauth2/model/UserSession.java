package dev.ashiq.userauth2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserSession extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String token;

}
