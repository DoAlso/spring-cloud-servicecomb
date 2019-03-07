package com.sample.servicecomb.common.api.provider;

import com.sample.servicecomb.common.bean.provider.User;

public interface IUserController {
    User getUserById(Long id);
}
