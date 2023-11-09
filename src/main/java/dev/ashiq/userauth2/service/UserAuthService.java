package dev.ashiq.userauth2.service;

import dev.ashiq.userauth2.model.User;

public interface UserAuthService {

    public  String login(User user);

    Boolean validate(User user);
}
