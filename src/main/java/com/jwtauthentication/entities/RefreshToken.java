package com.jwtauthentication.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor @Table(name = "Token")
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token")
    private Long id;

    private Instant expiryDate;

    private String token;
    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "user_id")
    private UserInfo userInfo;
}
