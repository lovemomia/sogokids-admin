package cn.momia.admin.web.entity;

/**
 * Created by hoze on 15/7/21.
 */
public class ImageBean {
    private int width;
    private int height;
    private float scale;
    private String uploadUrl;
    private String toFileUrl;
    private int mark;
    private int r_width;
    private int r_height;
    private double rotate;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getToFileUrl() {
        return toFileUrl;
    }

    public void setToFileUrl(String toFileUrl) {
        this.toFileUrl = toFileUrl;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getR_width() {
        return r_width;
    }

    public void setR_width(int r_width) {
        this.r_width = r_width;
    }

    public int getR_height() {
        return r_height;
    }

    public void setR_height(int r_height) {
        this.r_height = r_height;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }
}
