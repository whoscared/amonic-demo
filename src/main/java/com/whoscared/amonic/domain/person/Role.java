package com.whoscared.amonic.domain.person;

import jakarta.persistence.*;


@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private TypeOfRole role;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfRole getRole() {
        return role;
    }

    public void setRole(TypeOfRole role) {
        this.role = role;
    }
}
