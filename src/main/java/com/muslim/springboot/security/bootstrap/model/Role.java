package com.muslim.springboot.security.bootstrap.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;


    @Override
    public String getAuthority() {
        return getName();
    }

    public Role() {
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        if (name.equals("ROLE_USER"))
            return "USER";
        return "ADMIN";
    }
}
