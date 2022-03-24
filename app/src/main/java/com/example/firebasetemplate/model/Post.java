package com.example.firebasetemplate.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Post {
    public String postid;
    public String content;
    public String authorName;
    public String authorImageUrl;
    public String date;
    public String imageUrl;

    public HashMap<String, Boolean> likes = new HashMap<>();
    public List<Comment> comments = new ArrayList<>();
}
