package cn.momia.admin.web.common;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * Created by hoze on 15/7/16.
 */
public class ImageUtil {

    /**
     * 图片按比例或宽高进行缩放
     * @param width 缩放宽度
     * @param height 缩放高度
     * @param scale 缩放比例
     * @param uploadUrl 图片地址
     * @param toFileUrl 图片
     * @param mark
     * @throws IOException
     */
    public void ImageZoom(int width, int height, float scale, String uploadUrl, String toFileUrl, int mark) throws IOException {
        if (mark == FinalUtil.IMAGE_SCALE){
            //scale(比例)
            Thumbnails.of(uploadUrl).scale(scale).toFile(toFileUrl);
        }else{
            /*
             * 若图片横比width小，高比300小，不变
             * 若图片横比200小，高比300大，高缩小到300，图片比例不变
             * 若图片横比200大，高比300小，横缩小到200，图片比例不变
             * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
             */
            Thumbnails.of(uploadUrl).size(width, height).toFile(toFileUrl);
        }

    }
}
