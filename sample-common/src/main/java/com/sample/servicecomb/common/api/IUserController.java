package com.sample.servicecomb.common.api;

import com.sample.servicecomb.common.bean.User;

public interface IUserController {
    User getUserById(Long id);
}
