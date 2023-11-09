package dev.ashiq.userauth2.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link dev.ashiq.userauth2.model.User}
 */
@Getter
@Setter
public class UserDto  {
    String email;
    String pwd;

}