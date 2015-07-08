package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.ConfigUtil;
import cn.momia.admin.web.common.StringUtil;
import cn.momia.admin.web.entity.Images;
import cn.momia.admin.web.service.ImagesService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    @Override
    public Images uploadImgs(HttpServletRequest req,int intx,String imgname){
        //转型为MultipartHttpRequest(重点的所在)
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) req;
        String path = multipartRequest.getSession().getServletContext().getRealPath("/");
        String configpath = ConfigUtil.loadProperties().get("uploadPath").toString();
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

    @Override
    public boolean delimg(HttpServletRequest req, String url){
        boolean reData = false;
        String delfilepath = req.getSession().getServletContext().getRealPath("/") + url;

        File file = new File(delfilepath);
        reData = file.delete();
        return reData;
    }
    /**
     * 获取图片生成名称
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

    @Override
    public Images uploadImg(HttpServletRequest req,int intx,String imgname) {
        Images result = new Images();
        int intflag = 0;
        String upload_url = ConfigUtil.loadProperties().getProperty("img.server.upload.path");
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) req;
        Collection<MultipartFile> fileList = null;
        if (intx == 0){
            fileList = multipartRequest.getFileMap().values();
        }else{
            fileList = multipartRequest.getFiles(imgname);
        }
        File file_n = null;
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            if (filename == null || filename.equals("")){
                result.setName("");
                result.setUrl("");
                result.setWidth(0);
                result.setHeigth(0);
                intflag = 1;
            }else{
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                file_n = fi.getStoreLocation();
            }
        }
        if (intflag == 0) {
            try {
                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(upload_url);
                FileBody bin = null;
                if (file_n != null) {
                    bin = new FileBody(file_n);
                }
                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("file", bin);
                httpPost.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new RuntimeException("fail to execute request: " + httpPost);
                }
                HttpEntity resEntity = response.getEntity();
                String entityStr = EntityUtils.toString(resEntity);
                //System.out.println(entityStr);
                Map<String, Object> map = StringUtil.parseJSON2Map(entityStr);
                if (map.get("errmsg").equals("success")) {
                    Map<String, Object> mapdata = StringUtil.parseJSON2Map(map.get("data").toString());
                    result.setWidth(Integer.parseInt(mapdata.get("width").toString()));
                    result.setHeigth(Integer.parseInt(mapdata.get("height").toString()));
                    result.setUrl(mapdata.get("path").toString());
                } else {
                    throw new RuntimeException("上传图片信息失败：错误码－" + map.get("errmsg"));
                }
            } catch (Exception e) {
                throw new RuntimeException("error : yuyu!");
            }
        }
        return result;
    }

}
