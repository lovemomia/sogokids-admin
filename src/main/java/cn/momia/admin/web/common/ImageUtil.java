package cn.momia.admin.web.common;

import cn.momia.admin.web.entity.ImageBean;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by hoze on 15/7/16.
 */
public class ImageUtil {

    /**
     * 图片按比例或宽高进行缩放
     * @param entity
     * @throws IOException
     */
    public void ImageZoom(ImageBean entity) throws IOException {
        if (entity.getMark() == FinalUtil.IMAGE_SCALE){
            //scale(比例)
            Thumbnails.of(entity.getUploadUrl()).scale(entity.getScale()).toFile(entity.getToFileUrl());
        }else if (entity.getMark() == FinalUtil.IMAGE_WH) {
        /*
         * 若图片横比width小，高比300小，不变
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变
         * 若图片横比200大，高比300小，横缩小到200，图片比例不变
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
         */
            Thumbnails.of(entity.getUploadUrl()).size(entity.getWidth(), entity.getHeight()).toFile(entity.getToFileUrl());
        }else{
            //keepAspectRatio(false)默认是按照比例缩放的
            Thumbnails.of(entity.getUploadUrl()).size(entity.getWidth(), entity.getHeight()).keepAspectRatio(true).toFile(entity.getToFileUrl());
        }

    }

    /**
     * 图片旋转
     * @param entity
     * @throws IOException
     */
    public void ImageRotation(ImageBean entity) throws IOException {

        //rotate(角度),正数：顺时针  负数：逆时针
        Thumbnails.of(entity.getUploadUrl()).size(entity.getWidth(), entity.getHeight()).rotate(entity.getRotate()).toFile(entity.getToFileUrl());
    }


    /**
     * 图片裁减
     * @param entity
     * @throws IOException
     */
    public void ImageSourceRegion(ImageBean entity) throws IOException {
        if (entity.getMark() == FinalUtil.IMAGE_SCALE){
            //图片中心400*400的区域
            Thumbnails.of(entity.getUploadUrl()).sourceRegion(Positions.CENTER,entity.getR_width(),entity.getR_height()).size(entity.getWidth(), entity.getHeight()).keepAspectRatio(false).toFile(entity.getToFileUrl());
        }else if (entity.getMark() == FinalUtil.IMAGE_WH){
            //图片右下400*400的区域
            Thumbnails.of(entity.getUploadUrl()).sourceRegion(Positions.BOTTOM_RIGHT, entity.getR_width(), entity.getR_width()).size(entity.getWidth(), entity.getHeight()).keepAspectRatio(false).toFile(entity.getToFileUrl());
        }else{
            //指定坐标
            Thumbnails.of(entity.getUploadUrl()).sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false).toFile(entity.getToFileUrl());
        }

    }


    /**
     * 水印
     * @param width
     * @param height
     * @param scale
     * @param uploadUrl
     * @param toFileUrl
     * @param mark
     * @throws IOException
     */
    public void ImageWatermark(int width, int height, float scale, String uploadUrl, String toFileUrl, int mark) throws IOException {
        //watermark(位置，水印图，透明度)
        if (mark == FinalUtil.IMAGE_SCALE){
            Thumbnails.of(uploadUrl)
                    .size(1280,1024)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("image/watermark.png")),0.5f)
                    .outputQuality(0.8f)
                    .toFile(toFileUrl);
        }else{
            Thumbnails.of(uploadUrl)
                    .size(1280,1024)
                    .watermark(Positions.CENTER,ImageIO.read(new File("images/watermark.png")),0.5f)
                    .outputQuality(0.8f)
                    .toFile(toFileUrl);
        }

    }

    /**
     * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
     * @param file
     * @param entity
     * @throws Exception
     */
    private static void Image(File file,ImageBean entity) throws Exception {
        String imagePath = "/Users/hoze/Downloads/美田创意(1)/音乐盒/11111.jpg";
        BufferedImage image = ImageIO.read(new File(imagePath));
        Thumbnails.Builder<BufferedImage> builder = null;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if ((float)entity.getR_height() / entity.getR_width() != (float)imageWidth / imageHeight) {
            if (imageWidth > imageHeight) {
                image = Thumbnails.of(imagePath).height(entity.getR_height()).asBufferedImage();
            } else {
                image = Thumbnails.of(imagePath).width(entity.getR_width()).asBufferedImage();
            }
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, entity.getR_width(), entity.getR_height()).size(entity.getR_width(), entity.getR_height());
        } else {
            builder = Thumbnails.of(image).size(entity.getR_width(), entity.getR_height());
        }
        builder.toFile(file);
        System.out.print("filename9090:"+file.getName());
        builder.outputFormat("jpg").toFile("/Users/hoze/Downloads/美田创意(1)/音乐盒/11111.jpg");
    }

    /**
     * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
     * @param url
     * @param width
     * @param height
     * @throws Exception
     */
    private static void cutImage(String url,int width,int height,String tourl) throws Exception {
        File file = new File(url);
        String filename = file.getName();
        BufferedImage image = ImageIO.read(file);
        Thumbnails.Builder<BufferedImage> builder = null;

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if ((float)height / width != (float)imageWidth / imageHeight) {
            if (imageWidth > imageHeight) {
                //image = Thumbnails.of(url).height(height).outputQuality(1.0).asBufferedImage();
                image = Thumbnails.of(url).size(width, height).asBufferedImage();
            } else {
                image = Thumbnails.of(url).width(width).outputQuality(1.0).asBufferedImage();
            }
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width, height).outputQuality(1.0);
        } else {
            builder = Thumbnails.of(image).size(width, height).outputQuality(1.0);
        }
        builder.outputFormat("jpg").toFile(tourl);
    }

    public static void main(String[] args) throws Exception {
        String url = "/Users/hoze/Downloads/1aduola/yt.jpg";
        String tourl = "/Users/hoze/Downloads/1aduola/yt_ll";
        String url2 = "/Users/hoze/Downloads/1aduola/yt_ll.jpg";
        String tourl2 = "/Users/hoze/Downloads/1aduola/yt_m";
        String url3 = "/Users/hoze/Downloads/1aduola/yt_m.jpg";
        String tourl3 = "/Users/hoze/Downloads/1aduola/yt_s";

        //cutImage(url,800,600,tourl);
        cutImage(url2,240,180,tourl2);
        //cutImage(url3,90,90,tourl3);
    }
}
