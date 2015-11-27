package com.sogokids.utils.entity;

import java.util.List;

/**
 * Created by hoze on 15/11/25.
 */
public class Detail {

    private String title;
    private List<Content> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
