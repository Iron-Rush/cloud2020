package com.atguigu.springcloud.ailibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        // 暂停 处理业务
        try {
            TimeUnit.MILLISECONDS.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "——————Test A";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName() + "\t" + "——————TestB");
        return "——————TestB";
    }

    @GetMapping("/testD")
    public String testD(){
        log.info("——————TestD 测试RT");
        return "——————TestD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2){
//        int age = 10/0;       //SentinelResource仅处理Sentinel控制台报错，java异常不作处理
        return "——————TestHoyKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "——————deal_testHotKey, T T";    // sentinel系统默认的提示：blocked by sentinel(flow limiting)
    }

}
