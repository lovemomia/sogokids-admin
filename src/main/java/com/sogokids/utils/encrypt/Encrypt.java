package com.sogokids.utils.encrypt;

import java.io.UnsupportedEncodingException;

/**
 * Created by hoze on 15/8/25.
 */
public class Encrypt {

    /**
     * 加密
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new Base64().encode(b);
        }
        return s;
    }

    /**
     * 解密
     * @param s
     * @return
     */
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            Base64 decoder = new Base64();
            try {
                b = decoder.decode(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
