package com.tenco.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/*
 * 리스너 사용해 보기
 * ServletContext 구현 해야 한다.
 * 동작 트리거, web.xml 파일과 어노테이션 기반으로 설정 간으
 */

@WebListener
public class AppLifecycleListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(AppLifecycleListener.class.getName());

	// 5분 formater 메서드

	private String timeFormat() {
		// yyyy-MM-dd HH:mm:ss
		SimpleDateFormat timeformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return timeformatter.format(new Date());
	}

	// 애플리케이션이 언제 시작을 했는지 로그, 파일을 남겨야 될 때 사용한다라고 가정하자.
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("-------------------------");
		logger.info("웹 어플리케이션 실행됨 >>>> " + timeFormat());
		System.out.println("-------------------------");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("-------------------------");
		logger.info("웹 어플리케이션 종료됨 >>>> " + timeFormat());
		System.out.println("-------------------------");
	}

}
