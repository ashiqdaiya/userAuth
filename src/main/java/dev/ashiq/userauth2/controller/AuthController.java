package dev.ashiq.userauth2.controller;

import dev.ashiq.userauth2.DTO.*;
import dev.ashiq.userauth2.exception.PasswordDoesNotMatch;
import dev.ashiq.userauth2.exception.UserAlreadyExists;
import dev.ashiq.userauth2.exception.UserDoesNotExistException;
import dev.ashiq.userauth2.model.SessionStatus;
import dev.ashiq.userauth2.repository.UserRepository;
import dev.ashiq.userauth2.service.UserAuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserAuthService userAuth;
    private UserRepository userRepository;

    public AuthController(UserAuthService userAuth,UserRepository userRepository){
        this.userAuth=userAuth;
        this.userRepository=userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto) throws UserAlreadyExists {
        System.out.println(requestDto.getEmail());
        System.out.println(requestDto.getRoles());
        UserDto userDto = userAuth.signUp(requestDto.getEmail(),requestDto.getPwd(), requestDto.getFullName(), requestDto.getRoles());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @PostMapping ("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesNotExistException, PasswordDoesNotMatch {
        return userAuth.login(loginRequestDto.getEmail(), loginRequestDto.getPwd());
    }
    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token) {
        userAuth.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto requestTokenDto){
        Optional <UserDto> userDto = userAuth.validate(requestTokenDto.getToken(), requestTokenDto.getUserId());
        if(userDto.isEmpty()){
           ValidateTokenResponseDto response = new ValidateTokenResponseDto();
           response.setSessionStatus(SessionStatus.INVALID);
           return new ResponseEntity<>(response,HttpStatus.OK);
        }
        ValidateTokenResponseDto response = new ValidateTokenResponseDto();
        response.setSessionStatus(SessionStatus.ACTIVE);
        response.setUserDto(userDto.get());
       return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
        return userAuth.logOut(request.getToken(), request.getUserId());
    }
}
