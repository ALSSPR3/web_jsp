package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// http://localhost:8080/req-test
@WebServlet("/req-test")
public class RequestTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RequestTest() {
		super();
	}

	// http://localhost:8080/gp/req-test?name="홍길동"
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// METHOD <--- GET 방식일 때
		// 요청을 받고 응답시에 MIME TYPE -> text/html
		response.setContentType("text/html; charset=UTF-8");

		String name = request.getParameter("name");
		String greeting = "Hello," + (name != null ? name : "바보") + " !! ";

		response.getWriter().print("<html><body> <h2> " + greeting + " </h2> </body></html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 요청을 받아서 (인증검사, 요청값 유효성검사

		// HTTP 요청 바디에서 데이터를 추출
		response.setContentType("text/html; charset=UTF-8");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		// 보통 DB에 접근해서 CRUD 작업

		response.getWriter().write("<html>");
		response.getWriter().write("<head>");
		response.getWriter().write("</head>");
		response.getWriter().write("<body>");
		response.getWriter().write("<h1>");
		response.getWriter().write("<p>");
		response.getWriter().write("name : " + name);
		response.getWriter().write("<br>");
		response.getWriter().write("email : " + email);
		response.getWriter().write("</p>");
		response.getWriter().write("</h1>");
		response.getWriter().write("</body>");
		response.getWriter().write("</html>");

		// http://localhost:8080/demo1/req-test?name=%22%EC%95%BC%EC%8A%A4%EC%98%A4%22
	}

}
