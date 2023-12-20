package com.restapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "refreshToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @OneToOne
   @JoinColumn(name = "userId", referencedColumnName = "id")
   private User user;
   @Column(nullable = false, unique = true)
   private String token;
   @Column(nullable = false)
   private Instant expiryDate;
}
