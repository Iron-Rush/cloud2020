package com.atguigu.springcloud.ailibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(4444, "客户自定义,global ExceptionHandler————1");
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(4444, "客户自定义,global ExceptionHandler————2");
    }
}
