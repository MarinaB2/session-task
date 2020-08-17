package com.sessiontask.demo.models;

import javax.persistence.*;


@Entity
@Table(name =" Users")
public class TheUser {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public String userpassword;

    
    public String getUsername(){
        return username;

    } public String getUserpassword(){

        return userpassword;
    }
    public void setUserUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String password) {
        this.userpassword = password;
    }

}
