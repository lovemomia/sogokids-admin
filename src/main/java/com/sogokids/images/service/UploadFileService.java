package com.sogokids.images.service;



import com.sogokids.images.model.Images;
import com.sogokids.images.model.UploadFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/6/18.
 */
public interface UploadFileService {
    public UploadFile uploadFile(HttpServletRequest req) ;
}
