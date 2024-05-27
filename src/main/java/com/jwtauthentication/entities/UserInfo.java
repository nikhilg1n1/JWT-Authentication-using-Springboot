package com.jwtauthentication.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(unique = true,nullable = false,length = 100)
    private String email;
    @Column(unique = true)
    private String password;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<UserRole>userRole=new HashSet<>();
}
