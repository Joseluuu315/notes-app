package com.joseluu.notesapp.entity;

import java.util.Date;

public class Notes {
    private String title, content;
    private Date createDate;

    public Notes(String title, String content, Date createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public Notes() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
