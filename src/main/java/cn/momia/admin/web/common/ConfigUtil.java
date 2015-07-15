package cn.momia.admin.web.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hoze on 15/6/15.
 */
public class ConfigUtil {

    private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

   // public static final String CONFIG_FILE = ConfigUtil.class.getResource("/").getPath().toString().replaceAll("file:/", "") + "beans/config.properties";
   // public final static String CONFIG_FILE = Object.class.getResourceAsStream("/beans/config.properties").getClass().get;
   /* public static void main(String[] args) {

        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream(CONFIG_FILE));
        prop.load(in);     ///加载属性列表
        Iterator<String> it = prop.stringPropertyNames().iterator();
        while(it.hasNext()){
            String key=it.next();
            System.out.println(key+":"+prop.getProperty(key));
        }
        in.close();

    }catch (Exception e){
        logger.error("系统配置文件" + CONFIG_FILE + "不存在!");
        throw new IllegalArgumentException("系统配置文件"+CONFIG_FILE+"不存在!");
    }
    }*/

    public static Properties loadProperties() {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            //读取属性文件config.properties
            //InputStream in = new BufferedInputStream(new FileInputStream(CONFIG_FILE));
            in = Object.class.getResourceAsStream("/beans/config.properties");
            prop.load(in);     ///加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                // System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();

        } catch (Exception e) {
            logger.error("System configuration file '" + "aaa" + "' Does not exist!");
            throw new IllegalArgumentException("System configuration file '" + "bbb" + "' Does not exist!");
        }

        return prop;
    }
}

