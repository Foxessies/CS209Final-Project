package com.example.springbootdemo.model;


import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Developer {
    String name;
    String email;
    String repos_url;
    String id;
    String login;

    public Developer(String name, String email, String repos_url, String id, String login) {
        this.name = name;
        this.email = email;
        this.repos_url = repos_url;
        this.id = id;
        this.login = login;
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

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
