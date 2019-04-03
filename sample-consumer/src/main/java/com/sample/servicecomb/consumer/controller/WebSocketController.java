package com.sample.servicecomb.consumer.controller;

import com.sample.servicecomb.consumer.websocket.WebSocketServer;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WebSocketController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/29 20:34
 */
@RestController
public class WebSocketController {
    @RequestMapping(value = "/IM.html/{userId}")
    public String IM(ModelMap modelMap, @PathVariable String userId){
        modelMap.put("userId",userId);
        return "IM";
    }

    @RequestMapping(value="/pushListToWeb")
    @ResponseBody
    public Map<String,Object> pushVideoListToWeb(String cid, String message) {
        Map<String,Object> result =new HashMap<>();

        try {
            WebSocketServer.sendInfo(message,cid);
            result.put("operationResult", true);
        }catch (IOException e) {
            result.put("operationResult", true);
        }
        return result;
    }
}
