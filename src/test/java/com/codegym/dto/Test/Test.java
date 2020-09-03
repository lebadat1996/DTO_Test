package com.codegym.dto.Test;



public class Test {
    private String name;
    private String email;

    public Test() {
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

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
