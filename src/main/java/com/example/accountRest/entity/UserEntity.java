package com.example.accountRest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class UserEntity {
        @jakarta.persistence.Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false,unique = true)
        private Long id;
        private String name;
        private String username;
        @Column( nullable = false, unique = true)
        private String email;
        private String password;
        private String token;
        @Column(columnDefinition = "TIMESTAMP")
        private LocalDateTime tokenCreationDate;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "user_roles",
                joinColumns = {@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id"), @JoinColumn(name = "user_email", nullable = false, referencedColumnName = "email")},
                inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id") )
                private Set<RoleEntity> roles;


}
