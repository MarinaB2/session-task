package com.sessiontask.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String content;

    @Column(nullable = false)
    public Date published;

    @Column
    public byte[] image;

    @ManyToOne
    public Notice noticeId;

   @ManyToOne
    public TheUser userId;

}
