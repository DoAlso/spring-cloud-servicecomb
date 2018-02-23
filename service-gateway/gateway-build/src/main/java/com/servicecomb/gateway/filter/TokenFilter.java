package com.servicecomb.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.vo.CurrentUser;
import com.servicecomb.common.utils.FastJsonUtil;
import com.servicecomb.common.utils.LogUtil;
import com.servicecomb.gateway.auth.CheckCurrentUserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

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
		RequestContext ctx = RequestContext.getCurrentContext();
		return (boolean)ctx.get("verify");
	}

	@Override
	public int filterOrder() {
		return 1;
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
		LogUtil.info(logger,"your request header token is:{}",xToken);
		if(request.getMethod().equals(HttpMethod.GET)){
			LogUtil.info(logger,"your request path is:{},your query params is:{}",request.getRequestURI(),request.getQueryString());
		}else{
			LogUtil.info(logger,"your request path is:{},your query params is:{}",request.getRequestURI(), FastJsonUtil.toJSONString(request.getParameterMap()));
		}
		CurrentUser user = checkCurrentUserHandler.checkCurrentUser(xToken);
		//Token验证错误响应401
		if(user == null){
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody(Constant.ResponseMSG.TOKEN_ERROR);
			ctx.setSendZuulResponse(false);
			return null;
		}
		//将用户信息传递到下一个过滤器进行权限的验证
		ctx.set(Constant.CommomKey.CURRENT_USER,user);
		return null;
	}


}
