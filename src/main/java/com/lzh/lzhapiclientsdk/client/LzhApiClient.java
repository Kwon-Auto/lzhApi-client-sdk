package com.lzh.lzhapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lzh.lzhapiclientsdk.model.User;
import com.lzh.lzhapiclientsdk.utils.GenSignUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Hutool工具类调用第三方接口
 *
 * @autor lzh
 */
public class LzhApiClient {

    private String accessKey;
    private String secretKey;

    public LzhApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result3= HttpUtil.get("http://localhost:8123/api/nameApi/", paramMap);
        System.out.println(result3);
        return result3;
    }

    public String getNameByPost(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result3= HttpUtil.post("http://localhost:8123/api/nameApi/", paramMap);
        System.out.println(result3);
        return result3;

    }
    //把ak和sk放入到请求头中
    private Map<String,String> putHeaders(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //一定不能直接发送
        //hashMap.put("secretKey",secretKey);
        //生成长度为5的随机数
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        //获取当前时间的时间戳
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));//当前时间的毫秒数除以1000,得到的是一个长度为10的时间戳
        hashMap.put("body",body);
        hashMap.put("sign", GenSignUtils.genSign(body,secretKey));
        return hashMap;
    }

    //调用Restful接口
    public String getNameOfRestful(User user){
        //将前端传递过来的user对象转换成json字符串的形式
        String json = JSONUtil.toJsonStr(user);
        //返回的是一个响应，而只有响应中的body才是真正的响应内容
//        String result2 = HttpRequest.post("http://localhost:8123/nameApi/user")
//                .body(json)
//                .execute().body();
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/nameApi/user")
                .addHeaders(putHeaders(json))//把用户参数放入到请求头中(这里用到了用户参数进行加密)
                .body(json)
                .execute();
        //得到响应的状态码
        int status = httpResponse.getStatus();
        System.out.println(status);
        String result = httpResponse.body();
        System.out.println(httpResponse);
        return result;
    }

}
