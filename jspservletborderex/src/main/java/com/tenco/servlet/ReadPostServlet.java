package com.tenco.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/read-posts")
public class ReadPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReadPostServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 응답 처리 MIME
		response.setContentType("text/html;charset=UTF-8");

		try (Connection conn = DBUtil.getConnection()) {
			String sql = " SELECT * FROM posts ORDER BY created_at DESC ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			request.setAttribute("resultSet", rs);
			request.getRequestDispatcher("readPosts.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
