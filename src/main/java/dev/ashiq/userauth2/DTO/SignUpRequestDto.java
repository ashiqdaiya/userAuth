package dev.ashiq.userauth2.DTO;

import dev.ashiq.userauth2.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String pwd;
    private String fullName;
    private ArrayList<String> roles;
}
