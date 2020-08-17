package com.sessiontask.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String content;

    @Column
    public byte[] image;

    @Column(nullable = false)
    public Date published;

    @ManyToOne
    public TheUser userId;



}
