package dev.ashiq.userauth2.service;

import dev.ashiq.userauth2.DTO.UserDto;
import dev.ashiq.userauth2.exception.PasswordDoesNotMatch;
import dev.ashiq.userauth2.exception.UserAlreadyExists;
import dev.ashiq.userauth2.exception.UserDoesNotExistException;
import dev.ashiq.userauth2.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public interface UserAuthService {

    public UserDto signUp(String email, String pwd, String fullName, ArrayList<String> role) throws UserAlreadyExists;

    public ResponseEntity<UserDto> login(String email,String pwd) throws UserDoesNotExistException, PasswordDoesNotMatch;

    public void validateToken(String token);

    public Optional<UserDto> validate(String token, Long userId);

    public ResponseEntity<Void> logOut(String token,Long userId);

}
