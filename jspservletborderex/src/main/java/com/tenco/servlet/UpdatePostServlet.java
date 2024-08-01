package com.tenco.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/update-post")
public class UpdatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePostServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// 일반적인 로직 수정 - 권환확인 (세션정보, 작성자 정보 비교)
		String id = request.getParameter("boardId");
		// SELECT * FROM posts WHERE id = 3; <----- 작성자
		
		
		// 유효성 검사 (사용자의 새로 입력한 정보의 유효성 검사)
		
		// UPDATE 구문 처리
		// 트랜잭션 처리를 해야한다. (commit, rollback)
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		try (Connection conn = DBUtil.getConnection()) {
			String sql = " UPDATE posts SET title = ? , content = ? WHERE id = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, Integer.parseInt(id));
			pstmt.executeUpdate();
			
			response.sendRedirect("result.jsp?message=update-success");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("result.jsp?message=error");
		}
	}
}
