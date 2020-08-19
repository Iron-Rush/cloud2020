package com.atguigu.springcloud.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class TestAmnt {

    public static void main(String[] args) {
        TestAmnt ifc=new TestAmnt();
//        System.out.println(getAmnt());
        ifc.toRequest(ifc.getAmnt());
//        Thread th1=new Thread() {
//            public void run(){
//                // String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//                // String a = push.replace("wahaha", uuid);
//                ifc.toRequest(ifc.getAmnt());
//            }
//        };
//        Thread th2=new Thread() {
//            public void run(){
//                // String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//                // String a = push1.replace("wahaha", uuid);
//                ifc.toRequest(ifc.getAmnt());
//            }
//        };
//        while (true){
//            th1.run();
//
//            th2.run();
//        }

    }

    private String getAmnt(){
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,Object> headMap=new HashMap<String,Object>();
        Map<String,Object> bodyMap=new HashMap<String,Object>();
        headMap.put("SourceID","WX");
        headMap.put("TransExeDate","2019-4-11 9:24:30");
        headMap.put("User","czl");
        headMap.put("Token","44327e81945b105686b4cfb7a3b6538a");
        bodyMap.put("Name", "So"+getAutoNumber(0,10000));
        bodyMap.put("Sex", (String.valueOf((int)(Math.random()*100)%2)));
        bodyMap.put("Birthday", getAutoNumber(1900,2020)+"-"+getAutoNumber(1,12)+"-"+getAutoNumber(1,31));
        bodyMap.put("IDType", "8");
        bodyMap.put("IDNo", String.valueOf(getAutoNumber(10000000,99999999)));
        bodyMap.put("AgentCode", String.valueOf(getAutoNumber(1000000000,999999999)));
        bodyMap.put("Life", String.valueOf(getAutoNumber(0, 2500000)));
        bodyMap.put("Illness", String.valueOf(getAutoNumber(0, 2200000)));
        bodyMap.put("Accident", String.valueOf(getAutoNumber(0, 3000000)));
        bodyMap.put("Medical", String.valueOf(getAutoNumber(0, 3040000)));
        map.put("TransHead", headMap);
//        map.put("TransBody", bodyMap);
        map.put("TransBody", amnt);
        return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

    private Integer getAutoNumber(int Min, int Max){
        int random = (int)(Math.random()*(Max-Min)+Min);
        return random;
    }

    private static String amnt = "{\n" +
            "\"Name\": \"Monphy\",\n" +
            "\t\t\"Sex\": \"0\",\n" +
            "\t\t\"Birthday\": \"1950-08-17\",\n" +
            "\t\t\"IDType\": \"8\",\n" +
            "\t\t\"IDNo\": \"98762573\",\n" +
            "\t\t\"AgentCode\": \"8601000037\",\n" +
            "\t\t\"Life\": 250000,\n" +
            "\t\t\"Illness\": 300000,\n" +
            "\t\t\"Accident\": 100000,\n" +
            "\t\t\"Medical\": 2000000" +
            "}";

    public void toRequest(String json) {
        long a=System.currentTimeMillis();
        String r=sendPost("http://localhost:8008/restful/amnt", json);
        long b=System.currentTimeMillis();
        System.out.println("接口耗时"+(b-a));
        System.out.println(r);
        JSONObject jsonObject = JSONObject.parseObject(r);
        JSONObject jsonHead =jsonObject.getJSONObject("TransHead");
        JSONObject jsonBody =jsonObject.getJSONObject("TransBody");
        String code=jsonBody.getString("Return_Code");
        String codeName=jsonBody.getString("Return_Message");
        if (!("000000".equals(code))) {
            System.out.println(code+":"+codeName);
        }
    }

    /**
     * post请求方法（此方法中文不会乱码）
     * @param url
     * @param data
     * @return
     */
    public static  String sendPost(String url, String data) {
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data,
                        ContentType.create("application/json", "UTF-8"));//发送json数据需要设置contentType
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                int statusCode=httpresponse.getStatusLine().getStatusCode();
                //                System.out.println(statusCode);
                if(statusCode==HttpStatus.SC_OK) {
                    response = EntityUtils.toString(httpresponse.getEntity());//返回json字符串
                }

            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
