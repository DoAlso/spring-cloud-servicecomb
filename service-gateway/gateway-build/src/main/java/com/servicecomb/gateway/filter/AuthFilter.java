package com.servicecomb.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.vo.CurrentUser;
import com.servicecomb.common.utils.FastJsonUtil;
import com.servicecomb.common.utils.LogUtil;
import com.servicecomb.gateway.auth.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AuthFilter extends ZuulFilter{
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private static AuthHandler authHandler;

    public static void setAuthHandler(AuthHandler authHandler) {
        AuthFilter.authHandler = authHandler;
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        CurrentUser user = (CurrentUser) ctx.get(Constant.CommomKey.CURRENT_USER);
        LogUtil.info(logger,"current authorized user:{}", FastJsonUtil.toJSONString(user));
        //设置全局用户信息,供其他微服务操作
        ctx.addZuulRequestHeader(Constant.CommomKey.CURRENT_USER,FastJsonUtil.toJSONString(user));
        //验证当前的操作权限
        LogUtil.info(logger,"current request uri is:{}",request.getRequestURI());
        if(!authHandler.checkAuth(request.getRequestURI())){
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody(Constant.ResponseMSG.NO_AUTH);
            ctx.setSendZuulResponse(false);
            return null;
        }
        return null;
    }
}
