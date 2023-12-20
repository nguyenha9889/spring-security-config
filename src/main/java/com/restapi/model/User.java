package com.restapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email"),
      @UniqueConstraint(columnNames = "phone")
})
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(length = 50)
   private String id;

   @Column(length = 50)
   private String fullName;

   @NotBlank
   @Size(min = 5, max = 20)
   private String username;

   @NotBlank
   @Email
   @Size(max = 50)
   private String email;

   @NotBlank
   @Size(min = 9, max = 15)
   private String phone;

   @NotBlank
   @Size(min = 6, max = 50)
   private String password;
   private Boolean gender;
   private Date birthday;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
         name = "users_roles",
         joinColumns = @JoinColumn(name = "userId"),
         inverseJoinColumns = @JoinColumn(name = "roleId"))
   private Set<Role> roles = new HashSet<>();
}
