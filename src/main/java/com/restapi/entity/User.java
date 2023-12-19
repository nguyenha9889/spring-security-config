package com.restapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length = 50, nullable = false)
   private String fullName;

   @Column(unique = true)
   private String username;

   @Column(unique = true)
   private String email;

   @Column(unique = true)
   private String phone;

   private String password;
   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
         name = "users_roles",
         joinColumns = @JoinColumn(name = "userId"),
         inverseJoinColumns = @JoinColumn(name = "roleId"))
   public Set<Role> roles = new HashSet<>();

   public User(String fullName, String username, String email, String phone) {
      this.fullName = fullName;
      this.username = username;
      this.email = email;
      this.phone = phone;
   }
}
