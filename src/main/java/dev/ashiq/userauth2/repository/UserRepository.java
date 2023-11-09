package dev.ashiq.userauth2.repository;

import dev.ashiq.userauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

User findByEmail(String email);

}
