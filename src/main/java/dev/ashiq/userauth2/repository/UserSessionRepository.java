package dev.ashiq.userauth2.repository;

import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    UserSession findByUser(User user);
}