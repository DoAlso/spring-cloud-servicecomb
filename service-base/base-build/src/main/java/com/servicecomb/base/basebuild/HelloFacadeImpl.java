package com.servicecomb.base.basebuild;

import com.servicecomb.base.baseprovider.HelloFacade;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.utils.BackEntityUtil;
import org.springframework.stereotype.Service;

@Service
public class HelloFacadeImpl implements HelloFacade{

    @Override
    public BackEntity<String> sayHello(String val) {
        return BackEntityUtil.getReponseResult(val, Constant.ResponseMSG.REQUEST_OK, Constant.ResponseCode.REQUEST_OK);
    }
}
