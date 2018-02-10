package com.servicecomb.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.utils.FastJsonUtil;
import com.servicecomb.common.utils.LogUtil;
import com.servicecomb.gateway.auth.CheckCurrentUserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器实现
 * 可在run方法中实现
 * 想要过滤的业务逻辑
 * @author 胡亚曦
 *
 */
public class TokenFilter extends ZuulFilter{
	private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);
	private static CheckCurrentUserHandler checkCurrentUserHandler;

	public static void setCheckCurrentUserHandler(CheckCurrentUserHandler checkUserHandler) {
		checkCurrentUserHandler = checkUserHandler;
	}


	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String xToken = request.getHeader(Constant.CommomKey.CURRENT_USER_TOKEN);
		String operationUrl = request.getPathInfo();
		LogUtil.info(logger,"您的X-Token是:{}",xToken);
		LogUtil.info(logger,"您请求的地址是:{},您请求的参数是:{}",request.getRequestURL().toString(), FastJsonUtil.toJSONString(request.getParameterMap()));
		return null;
	}


}
