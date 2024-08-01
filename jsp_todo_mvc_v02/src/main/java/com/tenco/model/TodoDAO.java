package com.tenco.model;

import java.util.List;

public interface TodoDAO {

	// 저장 기능
	void addTodo(TodoDTO dto, int pricipalId);

	TodoDTO getTodoById(int id);

	List<TodoDTO> getTodosByUserId(int userId);

	List<TodoDTO> getAllTodos();

	void updateTodo(TodoDTO dto, int pricipalId);

	void deleteTodo(int id, int pricipalId);
}
