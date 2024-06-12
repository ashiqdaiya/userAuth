package dev.ashiq.userauth2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Date createdAt;

    private Date lastUpdatedAt;

    @PrePersist

    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = new Date();
    }

    private boolean isBoolean;
}
