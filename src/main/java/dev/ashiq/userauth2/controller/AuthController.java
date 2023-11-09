package dev.ashiq.userauth2.controller;

import dev.ashiq.userauth2.DTO.UserDto;
import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.repository.UserRepository;
import dev.ashiq.userauth2.service.UserAuthService;
import dev.ashiq.userauth2.service.UserAuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class AuthController {

    private UserAuthService userAuth;
    private UserRepository userRepository;

    public AuthController(UserAuthService userAuth,UserRepository userRepository){
        this.userAuth=userAuth;
        this.userRepository=userRepository;
    }

    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
       User user=userRepository.findByEmail(userDto.getEmail());

        return new ResponseEntity<>(
                userAuth.login(user),
                HttpStatus.OK
        );
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody UserDto userDto){
        User user= userRepository.findByEmail(userDto.getEmail());
        return new ResponseEntity<>(
                userAuth.validate(user),
                HttpStatus.OK
        );
    }
}
