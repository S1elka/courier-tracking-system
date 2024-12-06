package com.example.courier.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(Long id) {
        this.id = id;
        this.name = id == 1L ? "ROLE_ADMIN" : "ROLE_USER";
    }
}
