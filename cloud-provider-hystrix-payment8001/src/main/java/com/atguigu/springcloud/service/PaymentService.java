package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import sun.awt.windows.ThemeReader;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfoOK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_OK，id：" + id + "\t + 😁";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfoTimeOut(Integer id){
        // 报错代码块
//         int age = 10/0;

        int timeNumber = 2;
        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut，id：" + id + "\t + O!No，耗时:" + timeNumber + "秒";
//        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut，id：" + id;
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "paymntInfo_Handler，id：" + id + "系统繁忙/出错，请稍后再试！";
    }

    // ========服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("****Id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();  //UUID.randomUUID().toString(); //去下划线
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "Id不能为负数，请稍后再试。\t" + id;
    }

}
