package com.servicecomb.base.baseprovider;

import com.servicecomb.common.model.BackEntity;

public interface HelloFacade {

    BackEntity<String> sayHello(String val);
}
