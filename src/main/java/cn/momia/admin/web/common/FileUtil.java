package cn.momia.admin.web.common;

import cn.momia.common.config.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hoze on 15/6/15.
 */
public class FileUtil {

    //用户相关文件跳转
    public static final String USER_LOGIN = "login";
    public static final String USER_LOGIN_SUCESS = "index";
    public static final String USER = "user";
    public static final String USER_ADD = "user_add";
    public static final String USER_EDIT = "user_edit";

    //分类相关文件跳转
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ADD = "category_add";
    public static final String CATEGORY_EDIT = "category_edit";

    //地址信息相关文件跳转
    public static final String PLACE = "place";
    public static final String PLACE_ADD = "place_add";
    public static final String PLACE_EDIT = "place_edit";
    public static final String PLACE_IMG = "place_img";

    //商品相关文件跳转
    public static final String PRODUCT = "product";
    public static final String PRODUCT_ADD = "product_add";
    public static final String PRODUCT_EDIT = "product_edit";
    public static final String PRODUCT_IMG = "product_img";
    public static final String PRODUCT_OTHER_ADD = "product_other_add";
    public static final String PRODUCT_OTHER_EDIT = "product_other_edit";
    public static final String PRODUCT_PREVIEW = "product_preview";

    //
    public static final String PRODUCT_CONTENT = "product_content";
    public static final String PRODUCT_CON_EDIT = "content_edit";
    public static final String PRODUCT_CON_BODY = "content_body";
    public static final String PRODUCT_BODY_EDIT = "body_edit";

    //属性相关文件跳转
    public static final String PROPERTY = "property";
    public static final String PROPERTY_ADD = "property_add";
    public static final String PROPERTY_EDIT = "property_edit";
    public static final String PROPERTY_VALUE = "property_value";

    //SKU相关文件跳转
    public static final String SKU = "sku";
    public static final String SKU_ADD = "sku_add";
    public static final String SKU_EDIT = "sku_edit";
    public static final String SKU_PRODUCT = "sku_product";
    public static final String SKU_PRICE = "sku_price";

    //城市区域相关文件跳转
    public static final String CITY = "city";
    public static final String CITY_ADD = "city_add";
    public static final String CITY_EDIT = "city_edit";
    public static final String REGION = "region";
    public static final String REGION_ADD = "region_add";
    public static final String REGION_EDIT = "region_edit";

    //红包信息相关文件跳转
    public static final String COUPON = "coupon";
    public static final String COUPON_ADD = "coupon_add";
    public static final String COUPON_EDIT = "coupon_edit";

    public void setServerPath(String serverPath) {
        SERVER_PATH = serverPath;
    }

    public static String SERVER_PATH = "";

    public static InputStream openFileInputStream(String fileName) {
        InputStream inputStream = null;

        // 先尝试直接打开文件
        try {
            inputStream = new FileInputStream(fileName);
        } catch (Exception e) {
            // 直接打开文件失败时忽略异常，尝试从classpath下加载文件
        }

        if (inputStream == null) {
            try {
                File file = getFileInClassPath(fileName);
                inputStream = new FileInputStream(file);
            } catch (Exception e) {
                throw new RuntimeException("fail to open file input stream of file: " + fileName);
            }
        }

        return inputStream;
    }

    private static File getFileInClassPath(String fileName) throws FileNotFoundException {
        String[] classPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));

        for (String path : classPaths) {
            File parentFile = new File(path);
            if (parentFile.exists() && parentFile.isDirectory()) {
                File file = new File(parentFile, fileName);
                if (file.exists()) return file;
            }
        }

        throw new FileNotFoundException(fileName);
    }

}
