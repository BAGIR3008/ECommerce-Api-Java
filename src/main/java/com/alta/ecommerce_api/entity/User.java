package com.alta.ecommerce_api.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Product> products;
}
