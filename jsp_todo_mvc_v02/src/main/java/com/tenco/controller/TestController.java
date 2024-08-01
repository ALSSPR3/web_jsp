package com.tenco.controller;

import java.io.IOException;
import java.sql.Date;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDAO;
import com.tenco.model.UserDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test/*")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private TodoDAO todoDAO;

	public TestController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAOImpl();
		todoDAO = new TodoDAOImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/byId":
			// http://localhost:8080/mvc/test/byId
//			userDAO.getUserByID(1);
//			userDAO.getUserByName("홍길동");
//			List<UserDTO> list = userDAO.getAllUsers();
//			System.out.println(userDAO.deleteUser(4));
//			UserDTO dto = UserDTO.builder().password("999").email("h@naver.com").build();
//			int count = userDAO.updateUser(dto, 3);
//			System.out.println("count : " + count);

//			System.out.println(todoDAO.getTodoById(1));
			System.out.println(todoDAO.getTodosByUserId(1));
//			System.out.println(todoDAO.getAllTodos());
//			TodoDTO dto = TodoDTO.builder().title("할일2").description("공부 또 공부").id(1).build();
//			todoDAO.updateTodo(dto, 1);
//			TodoDTO todo1 = TodoDTO.builder().id(1).title("할일1").description("놀기").dueDate(new Date(2024-07-10)).completed(false).userId(1)
//			.build();
//			todoDAO.addTodo(todo1, 3);
//			todoDAO.deleteTodo(5, 3);

			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
