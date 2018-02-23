package com.servicecomb.cache.provider;

import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.model.vo.CurrentUser;

/**
 * 该接口提供base
 * 基础服务的redis操作
 */
public interface BaseFacede {

    /**
     * 缓存当前登录用户
     * @param currentUser
     * @return
     * @throws Exception
     */
    BackEntity setLoginUser(CurrentUser currentUser) throws Exception;

    /**
     * 延长当前用户登录时间
     * @param userId
     * @return
     * @throws Exception
     */
    BackEntity expireLoginUser(String userId) throws Exception;


    /**
     * 获取缓存中当前登录的用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    BackEntity getLoginUser(String userId) throws Exception;


    /**
     * 删除缓存中当前登录的用户信息
     * 用户用户登出操作
     * @param userId
     * @return
     * @throws Exception
     */
    BackEntity delLoginUser(String userId) throws Exception;
}
