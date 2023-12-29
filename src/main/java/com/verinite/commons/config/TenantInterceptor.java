package com.verinite.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.verinite.commons.controlleradvice.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TenantInterceptor implements HandlerInterceptor {

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String tenantId = request.getHeader("tenant-id");
		if (tenantId == null || tenantId.isEmpty()) {
			throw new BadRequestException("Tenant-Id is required in the header");
		}
		TenantContext.setCurrentTenant(tenantId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		TenantContext.clear();
	}
}
