package com.example.firebasetemplate.model;

import java.util.HashMap;

public class Comment {

    public String commentid;
    public String text;
    public String authorName;
    public String authorImage;

    public HashMap<String, Boolean> likes = new HashMap<>();

}
