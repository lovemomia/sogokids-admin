package com.sogokids.images.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.images.service.ImagesService;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hoze on 15/6/18.
 */
@Service
public class ImagesServiceImpl implements ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);

    @Autowired
    private Configuration configuration;

    /**
     * 图片上传本服务内部的/upload/images路径的原方法
     * @param req
     * @param intx
     * @param imgname
     * @return
     */
    @Override
    public Images uploadImgs(HttpServletRequest req,int intx,String imgname){
        //转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) req;
        String path = multipartRequest.getSession().getServletContext().getRealPath("/");
        String configpath = configuration.getString(Quantity.UPLOAD_IMAGE);
        String uploadpath = path +"/"+ configpath;
        Images imges = null;
        File folder = new File(uploadpath);
        if(!folder.exists()) {folder.mkdir();}
        Collection<MultipartFile> fileList = null;
        if (intx == 0){
            fileList = multipartRequest.getFileMap().values();
        }else{
            fileList = multipartRequest.getFiles(imgname);
        }
        for (MultipartFile file : fileList) {
            imges = new Images();
            String type = file.getContentType();
            String filename = file.getOriginalFilename();
            if (filename == null || filename.equals("")){
                imges.setName("");
                imges.setUrl("");
                imges.setWidth(0);
                imges.setHeigth(0);
            }else{
                Map<String,String> mapType = getReturnMap(filename);
                if(filename != null && mapType.get("reData").equals("true")){
                    filename = mapType.get("reKey");
                }else {
                    continue;
                }

                try {
                    InputStream stream = file.getInputStream();
                    BufferedImage bi = ImageIO.read(stream);
                    int width = bi.getWidth();
                    int height = bi.getHeight();
                    imges.setName(filename);
                    imges.setUrl(configpath + filename);
                    imges.setWidth(width);
                    imges.setHeigth(height);
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadpath, filename));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
            return imges;
    }

    /**
     * 物理删除图片
     * @param req
     * @param url
     * @return
     */
    @Override
    public boolean delimg(HttpServletRequest req, String url){
        boolean reData = false;
        String delfilepath = req.getSession().getServletContext().getRealPath("/") + url;

        File file = new File(delfilepath);
        reData = file.delete();
        return reData;
    }

    /**
     * 获取图片生成名称日期＋时间＋随机数
     * @return
     */
    private String getImgsNameStr(){
        String reStr = "";
        Random rd = new Random();
        Calendar time = Calendar.getInstance();
        reStr = String.valueOf(time.get(Calendar.YEAR))
                + String.valueOf(time.get(Calendar.MONTH)+1)
                + String.valueOf(time.get(Calendar.DAY_OF_MONTH))
                + String.valueOf(time.get(Calendar.HOUR_OF_DAY))
                + String.valueOf(time.get(Calendar.MINUTE))
                + String.valueOf(time.get(Calendar.SECOND))
                + String.valueOf(rd.nextInt(100));
        return reStr;
    }

    /**
     * 验证图片类型，返回图片类型是否为true及名称
     * @param file_type
     * @return
     */
    private Map<String,String> getReturnMap(String file_type){

        Map<String,String> reMap = new HashMap<String, String>();
        List<String> ls = Arrays.asList(new String[]{".jpg", ".jpeg", ".bmp",".gif",".png"});

        for (int i = 0; i < ls.size() ; i++) {
            if (file_type.endsWith(ls.get(i))){
                reMap.put("reData","true");
                reMap.put("reKey",getImgsNameStr() + ls.get(i));
                break;
            }
        }
        return reMap;

    }

    /**
     * 图片上传服务器
     * @param req
     * @param isName:是否按照页面控件名称得到文件内容
     * @param imgName:页面控件的名称
     * @return
     */
    @Override
    public Images uploadImg(HttpServletRequest req,int isName,String imgName,String isCut) {
        Images result = new Images();
        int int_flag = 0;
        String upload_url = configuration.getString(Quantity.UPLOAD_IMAGE);
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) req;
        }catch(Exception _ex){
            log.error("ImagesServiceImpl->uploadImg->error:" + _ex.getMessage());
        }
        Collection<MultipartFile> fileList = getMultipartFiles(isName, imgName, multipartRequest);//获取页面请求数据的File列表
        File file_n = null;
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            if (filename == null || filename.equals("")){
                getImagesResult(result,null,0);//文件不存在时返回的images对象
                int_flag = 1;//文件是否存在标识，0 存在，1不存在
            }else{
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                file_n = fi.getStoreLocation();
            }
        }
        if (int_flag == 0){
            try {
                sendHttpPost(upload_url, file_n, result, isCut);//发送文件上传请求并得到返回结果
            } catch (IOException e) {
                log.error("ImagesServiceImpl->uploadImg->error:"+e.getMessage());
            }
        }

        return result;
    }

    @Override
    public List<Images> uploadMoreImg(HttpServletRequest req,int isName,String imgName,String isCut){
        List<Images> images = new ArrayList<Images>();
//        int int_flag = 0;
        String upload_url = configuration.getString(Quantity.UPLOAD_IMAGE);
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) req;
        } catch (Exception _ex) {
            log.error("ImagesServiceImpl->uploadMoreImg->error:"+_ex.getMessage());
        }
        Collection<MultipartFile> fileList = getMultipartFiles(isName, imgName, multipartRequest);//获取页面请求数据的File列表
        File file_n = null;
        for (MultipartFile file : fileList) {
            Images result = new Images();
            String filename = file.getOriginalFilename();
            if (filename == null || filename.equals("")){
                getImagesResult(result,null,0);//文件不存在时返回的images对象
            }else{
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                file_n = fi.getStoreLocation();
                try {
                    sendHttpPost(upload_url, file_n, result, isCut);//发送文件上传请求并得到返回结果
                } catch (IOException e) {
                    log.error("ImagesServiceImpl->uploadMoreImg->error:"+e.getMessage());
                }
            }
            images.add(result);
        }

        return images;
    }

    /**
     *  获取页面请求数据的File列表
     * @param isName
     * @param imgName
     * @param multipartRequest
     * @return
     */
    private Collection<MultipartFile> getMultipartFiles(int isName, String imgName, MultipartHttpServletRequest multipartRequest){
        Collection<MultipartFile> fileList = null;
        if (isName == 0){
            fileList = multipartRequest.getFileMap().values();
        }else{
            fileList = multipartRequest.getFiles(imgName);
        }

        return fileList;
    }

    /**
     * httpClient发送图片上传数据到服务器
     * @param upload_url
     * @param file_n
     * @param result
     * @throws IOException
     */
    private void sendHttpPost(String upload_url, File file_n, Images result, String isCut) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(upload_url);
        FileBody bin = null;
        if (file_n != null) {
            bin = new FileBody(file_n);
        }

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("file", bin);
        reqEntity.addPart("cut", new StringBody(isCut, Charset.defaultCharset()));
        httpPost.setEntity(reqEntity);

        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            log.error("fail to execute request: " + httpPost);
//            throw new RuntimeException("fail to execute request: " + httpPost);
        }
        HttpEntity resEntity = response.getEntity();
        String entityStr = EntityUtils.toString(resEntity);
        Map<String, Object> map = StringUtil.parseJSON2Map(entityStr);
        if (map.get("errmsg").equals("success")) {
            getImagesResult(result,map,1);
        } else {
            log.error("image upload failure:errorCode->"+map.get("errmsg"));
//            throw new RuntimeException("image upload failure:errorCode-" + map.get("errmsg"));
        }
    }

    /**
     * 返回images对象结果
     * @param result
     * @param map
     * @param mark
     * @return
     */
    private Images getImagesResult(Images result,Map<String, Object> map,int mark){

        if (mark == 0){
            result.setName("");
            result.setUrl("");
            result.setWidth(0);
            result.setHeigth(0);
        }else{
            Map<String, Object> map_data = StringUtil.parseJSON2Map(map.get("data").toString());
            result.setWidth(Integer.parseInt(map_data.get("width").toString()));
            result.setHeigth(Integer.parseInt(map_data.get("height").toString()));
            result.setUrl(map_data.get("path").toString());
        }

        return result;

    }

}
