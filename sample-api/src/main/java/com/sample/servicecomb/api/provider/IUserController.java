package com.sample.servicecomb.api.provider;

import com.sample.servicecomb.api.provider.bo.UserBO;

public interface IUserController {
    UserBO getUserById(Long id);
}
