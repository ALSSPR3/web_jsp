package com.tenco.listeners;

import java.util.logging.Logger;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/*
 * 세션이 생성될때 감지.. 리스너 등록
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

	private static final Logger logger = Logger.getLogger(MySessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 세션 생성시 실행 됨
		logger.info("새로운 세션이 생성 됨 : " + se.getSession().getId());
		se.getSession().setAttribute("loginTime : ", System.currentTimeMillis());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("------------------------");
		// 세션 소멸시 실행 됨
		Long loginTime = (Long) se.getSession().getAttribute("loginTime");
		Long logoutTime = System.currentTimeMillis();

		System.out.println(loginTime);
		if (loginTime != null) {

			Long sessionDurationMs = logoutTime - loginTime; // 밀리초 단위
			double sessionDurationSec = sessionDurationMs / 1000.0; // 초 단위로 변환
			System.out.println("세션 지속 시간 : " + sessionDurationSec);
		}
		System.out.println("------------------------");
	}
}
