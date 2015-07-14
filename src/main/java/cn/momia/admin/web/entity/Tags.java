package cn.momia.admin.web.entity;

/**
 * Created by hoze on 15/7/14.
 */
public class Tags {

    private int id;
    private String name;
    private int checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
