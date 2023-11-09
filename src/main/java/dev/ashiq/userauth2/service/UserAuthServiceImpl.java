package dev.ashiq.userauth2.service;

import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.model.UserSession;
import dev.ashiq.userauth2.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserSessionRepository sessionRepository;

    public UserAuthServiceImpl(UserSessionRepository sessionRepository){
        this.sessionRepository=sessionRepository;
    }


    @Override
    public String login(User user) {
        String fullName= user.getFullName();
        String email=user.getEmail();
        String pwd=user.getPwd();
        String token = email.substring(0, email.length()/2) + "#" +
                pwd.substring(0, pwd.length()/2) + "#" +
                fullName.substring(0, fullName.length()/2);
        UserSession session = new UserSession();
        session.setUser(user);
        session.setToken(token);

        sessionRepository.save(session);

        return token;
    }

    @Override
    public Boolean validate(User user) {
        UserSession session=sessionRepository.findByUser(user);
        return session.getToken() != null;
    }
}
