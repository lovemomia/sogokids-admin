package com.sogokids.images.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.images.model.UploadFile;
import com.sogokids.images.service.UploadFileService;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by hoze on 16/7/6.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Autowired
    private Configuration configuration;

    @Override
    public UploadFile uploadFile(HttpServletRequest req) {
        UploadFile uploadFile = new UploadFile();
        String upload_url = configuration.getString(Quantity.UPLOAD_AUDIO);
        int int_flag = 0;
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) req;
        }catch(Exception _ex){
            log.error("UploadFileServiceImpl->uploadAudio->error:" + _ex.getMessage());
        }
        Collection<MultipartFile> fileList = multipartRequest.getFileMap().values();//获取页面请求数据的File列表

        File file_n = null;
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            if (filename == null || filename.equals("")){
                uploadFile.setFileName(null);
                uploadFile.setFilePath(null);
                int_flag = 1;//文件是否存在标识，0 存在，1不存在
            }else{
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                file_n = fi.getStoreLocation();
            }
        }

        if (int_flag == 0){
            try {
                sendHttpPost(upload_url, file_n, uploadFile);//发送文件上传请求并得到返回结果
            } catch (IOException e) {
                log.error("UploadFileServiceImpl->uploadAudio->error:"+e.getMessage());
            }
        }

        return uploadFile;
    }

    private void sendHttpPost(String upload_url, File file_n, UploadFile uploadFile) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(upload_url);
        FileBody bin = new FileBody(file_n);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("file", bin);
        httpPost.setEntity(reqEntity);

        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            log.error("fail to execute request: " + httpPost);
        }
        HttpEntity resEntity = response.getEntity();
        String entityStr = EntityUtils.toString(resEntity);
        Map<String, Object> map = StringUtil.parseJSON2Map(entityStr);
        if (map.get("errmsg").equals("success")) {
            Map<String, Object> map_data = StringUtil.parseJSON2Map(map.get("data").toString());
            uploadFile.setFileName("fileName");
            uploadFile.setFilePath(map_data.get("path").toString());
        } else {
            log.error("audio upload failure:errorCode->"+map.get("errmsg"));
        }
    }


}
