package com.sunjh.xiyiji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunjh.xiyiji.data.result.BaseResult;
import com.sunjh.xiyiji.logic.TestLogic;
import com.sunjh.xiyiji.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 10:41 上午
 */
@RestController
public class TestController extends BaseController {

    @Autowired
    private TestLogic testLogic;

    @GetMapping("/test/get")
    public BaseResult<String> testGet(@RequestParam Long userId) {
        BaseResult<String> result = new BaseResult<>();
        result.setCode("200");
        result.setMessage("success");
        result.setSuccess(true);
        result.setData(testLogic.sayHello(userId));
        return result;
    }

    @PostMapping("/test/post")
    public BaseResult<String> testPost() {
        return null;
    }

    @GetMapping("/test/read")
    public Object readFile() throws Exception {
        String filePath = "/Users/sunjh/IdeaProjects/www/log/aspect-myLog.log";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String str = null;
        int flag = 0;
        String pattern = ".*\\[cn\\.iselab\\.mooctest\\.site\\.common\\.aspect\\.LogControllerAspect\\] (.*)";
        Pattern r = Pattern.compile(pattern);
        while ((str = br.readLine()) != null) {
            Matcher m = r.matcher(str);
            if (m.find()) {
                System.out.println(m.group(1));
                JSONObject jsonObject = JSONObject.parseObject(m.group(1));
                System.out.println(jsonObject.get("type"));
            }
        }
        return null;
    }
    @GetMapping("/test/test")
    public Object test(){
        String test = "{\"name\":\"zhangsan\",\"no\":\"shige\"}";
        JSONObject jsonObject = JSONObject.parseObject(test);
        return null;
    }
}
