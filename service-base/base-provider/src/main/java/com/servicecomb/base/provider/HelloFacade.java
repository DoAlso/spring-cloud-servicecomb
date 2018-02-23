package com.servicecomb.base.provider;

import com.servicecomb.common.model.BackEntity;

public interface HelloFacade {

    BackEntity<String> sayHello(String val);
}
