package com.servicecomb.base.api;

import com.servicecomb.base.exception.CustomException;
import com.servicecomb.base.provider.UserFacade;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.model.BaseParam;
import com.servicecomb.common.utils.BackEntityUtil;
import com.servicecomb.common.utils.FastJsonUtil;
import com.servicecomb.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/register")
    public BackEntity register(@RequestBody BaseParam<String> baseParam) {
        LogUtil.info(logger,"request params is:{}", FastJsonUtil.toJSONString(baseParam));
        try{
            return userFacade.register(baseParam.getParams());
        }catch (Exception e){
            LogUtil.error(logger,"register error:{}",e.getMessage());
            return BackEntityUtil.getReponseResult(null, e.getMessage(),Constant.ResponseCode.SYSTEM_BUSY);
        }
    }
}
