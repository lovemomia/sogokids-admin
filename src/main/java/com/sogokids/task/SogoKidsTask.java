package com.sogokids.task;

import com.sogokids.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

/**
 * Created by hoze on 16/4/8.
 */
@Component
public class SogoKidsTask {

    @Autowired
    private TaskService taskService;
    /**
     * 定时计算。每天凌晨 01:00 执行一次
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void show(){
        System.out.println("Annotation：is show run");
        taskService.addRegUser();
        taskService.addOrder();
    }

    /**
     * 心跳更新。启动时执行一次，之后每隔2秒执行一次
     */
//    @Scheduled(fixedRate = 1000*2)
//    public void print(){
//        System.out.println("Annotation：print run");
//    }
}
