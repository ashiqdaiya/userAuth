package dev.ashiq.userauth2.security.services;

import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.repository.UserRepository;
import dev.ashiq.userauth2.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(username));
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username+"doesn't exist");
        }

        return new CustomUserDetails(user.get());
    }
}
