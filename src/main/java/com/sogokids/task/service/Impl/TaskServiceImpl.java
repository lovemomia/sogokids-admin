package com.sogokids.task.service.Impl;

import com.sogokids.task.model.RegUser;
import com.sogokids.task.service.RegUserService;
import com.sogokids.task.service.ReportOrderService;
import com.sogokids.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hoze on 16/4/8.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private RegUserService regUserService;

    @Autowired
    private ReportOrderService reportOrderService;

    @Override
    public void addRegUser(){
        RegUser regUser = regUserService.getDateRegUser();
        int reDate = regUserService.insert(regUser);
        if (reDate > 0){
            log.info("TaskServiceImpl－>添加统计注册用户成功!");
        }else{
            log.info("TaskServiceImpl－>添加统计注册用户失败!");
        }
    }

    @Override
    public void addOrder(){
        reportOrderService.addReportOrder();
    }

}
