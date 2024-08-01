package com.tenco.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TodoDAOImpl implements TodoDAO {

	private DataSource dataSource;

	public TodoDAOImpl() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTodo(TodoDTO dto, int pricipalId) {
		String sql = " INSERT INTO todos (user_id, title, description, due_date, completed) " + " VALUES (?,?,?,?,?) ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setInt(1, pricipalId);
				pstmt.setString(2, dto.getTitle());
				pstmt.setString(3, dto.getDescription());
				pstmt.setString(4, dto.getDueDate());
				pstmt.setInt(5, dto.getCompleted() == "true" ? 1 : 0);
				pstmt.executeUpdate();

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TodoDTO getTodoById(int id) {
		String sql = " SELECT * FROM todos WHERE id = ? ";
		TodoDTO todoDTO = null;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					todoDTO = TodoDTO.builder().id(rs.getInt("id")).title(rs.getString("title"))
							.description(rs.getString("description")).dueDate(rs.getString("due_date"))
							.completed(rs.getString("completed")).userId(rs.getInt("user_id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoDTO;
	}

	@Override
	public List<TodoDTO> getTodosByUserId(int userId) {
		String sql = " SELECT * FROM todos WHERE user_id = ? ";
		List<TodoDTO> list = new ArrayList<TodoDTO>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, userId);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					TodoDTO todoDTO = new TodoDTO();
					todoDTO = TodoDTO.builder().id(rs.getInt("id")).title(rs.getString("title"))
							.description(rs.getString("description")).dueDate(rs.getString("due_date"))
							.completed(rs.getString("completed")).userId(rs.getInt("user_id")).build();
					list.add(todoDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		String sql = " SELECT * FROM todos ";
		List<TodoDTO> list = new ArrayList<TodoDTO>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					TodoDTO todoDTO = new TodoDTO();
					todoDTO = TodoDTO.builder().id(rs.getInt("id")).title(rs.getString("title"))
							.description(rs.getString("description")).dueDate(rs.getString("due_date"))
							.completed(rs.getString("completed")).userId(rs.getInt("user_id")).build();
					list.add(todoDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateTodo(TodoDTO dto, int pricipalId) {
		String sql = " UPDATE todos SET title = ? , description = ?, " 
				+ " due_date = ?, completed = ? "
				+ " WHERE id = ? AND user_id = ? ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getDescription());
				pstmt.setString(3, dto.getDueDate());
				pstmt.setInt(4, dto.getCompleted() == "true" ? 1 : 0);
				pstmt.setInt(5, dto.getId());
				pstmt.setInt(6, pricipalId);
				pstmt.executeUpdate();

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTodo(int id, int pricipalId) {
		String sql = " DELETE FROM todos WHERE id = ? and user_id = ? ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setInt(1, id);
				pstmt.setInt(2, pricipalId);
				pstmt.executeUpdate();

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
