package com.sessiontask.demo.models;

import javax.persistence.*;


@Entity
@Table(name =" Users")
public class TheUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String userName;

    @Column(nullable = false)
    public String password;

    public String getUserName(){
        return userName;

    } public String getPassword(){

        return password;
    }

}
