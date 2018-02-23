package com.servicecomb.base.exception;

import javax.servlet.http.HttpServletRequest;

import com.servicecomb.common.model.BackEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		HttpStatus status = getStatus(request);
		BackEntity<?> entity = new BackEntity<>();
		entity.setResMsg(ex.getMessage());
		entity.setResCode(String.valueOf(status.value()));
		return new ResponseEntity<>(entity, status);
	}
	
	
	@ExceptionHandler(CustomException.class)
	@ResponseBody
	ResponseEntity<?> handleControllerCustomException(HttpServletRequest request, Throwable ex) {
		logger.info("[ServiceBase-handleControllerCustomException:throw success]");
		HttpStatus status = getStatus(request);
		BackEntity<?> entity = new BackEntity<>();
		entity.setResMsg(ex.getMessage());
		entity.setResCode(String.valueOf(status.value()));
		return new ResponseEntity<>(entity, status);
	}
	
	private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
	
	

}
