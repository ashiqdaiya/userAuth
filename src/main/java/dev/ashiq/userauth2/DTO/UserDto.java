package dev.ashiq.userauth2.DTO;

import dev.ashiq.userauth2.model.Role;
import dev.ashiq.userauth2.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link dev.ashiq.userauth2.model.User}
 */
@Getter
@Setter
public class UserDto  {
    private String email;
    private Collection<Role> role;

    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoles());
        return userDto;

    }

}