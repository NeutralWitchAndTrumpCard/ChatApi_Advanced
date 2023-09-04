package com.springbootchatapi.member;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.io.Serializable;        // 멤버 클래스 직렬화

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "account")
public class Member implements Serializable{
    // 멤버 클래스 직렬화

    private static final Logger logger = LoggerFactory.getLogger(Member.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "reset_token")
    private String resetToken;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        logger.info("Fetching email.");
        return email;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    // getter and setter methods
}
