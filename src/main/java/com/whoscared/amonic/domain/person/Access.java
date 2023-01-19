package com.whoscared.amonic.domain.person;

import jakarta.persistence.*;

@Entity
@Table(name = "access")
public class Access {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "access")
    @Enumerated(EnumType.STRING)
    private TypeOfAccess access;

    public Access(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfAccess getAccess() {
        return access;
    }

    public void setAccess(TypeOfAccess access) {
        this.access = access;
    }
}
