package cn.momia.admin.web.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/6/25.
 */
public class DataBean {

    private String title;
    private String style;
    private List<Content> body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Content> getBody() {
        return body;
    }

    public void setBody(List<Content> body) {
        this.body = body;
    }
}
