package com.example.web_shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {

    @EmbeddedId
    private AuthorityId authorityId;

    public Authorities() {
    }

    public AuthorityId getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(AuthorityId authorityId) {
        this.authorityId = authorityId;
    }
}
