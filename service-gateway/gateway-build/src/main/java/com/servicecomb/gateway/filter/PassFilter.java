package com.servicecomb.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.servicecomb.common.utils.FastJsonUtil;
import com.servicecomb.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class PassFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(PassFilter.class);
    @Override
    public String filterType() {
        return "pre";
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
        String operation = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
        LogUtil.info(logger,"operation is:{}",operation);
        if(operation.equals("register") || operation.equals("login")){
            ctx.set("verify",false);
        }else{
            ctx.set("verify",true);
        }
        return null;
    }
}
