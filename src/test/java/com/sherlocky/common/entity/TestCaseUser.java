package com.sherlocky.common.entity;

public class TestCaseUser {
    private Long id;
    private String name;

    private String email;

    public static TestCaseUser of(Long id, String name, String email) {
        return new TestCaseUser(id, name, email);
    }

    public TestCaseUser() {
    }

    public TestCaseUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestCaseUser(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}