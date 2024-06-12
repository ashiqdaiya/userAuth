package dev.ashiq.userauth2.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.ashiq.userauth2.model.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

//    private Role role;
private String authority;

    public CustomGrantedAuthority(Role role){
        this.authority=role.getRole();
    }
    @Override
    public String getAuthority() {
        System.out.println("role: "+ this.authority);
        return this.authority;
    }
}
