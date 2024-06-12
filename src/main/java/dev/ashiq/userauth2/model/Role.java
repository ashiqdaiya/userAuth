package dev.ashiq.userauth2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role extends BaseModel {
   private String role;
//   @ManyToMany(mappedBy = "roles")
//   private Collection<User> users;
}
