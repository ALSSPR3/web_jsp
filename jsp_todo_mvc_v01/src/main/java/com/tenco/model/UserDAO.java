package com.tenco.model;

import java.util.List;

public interface UserDAO {

	int addUser(UserDTO userDTO);

	UserDTO getUserByID(int id);

	UserDTO getUserByName(String username);

	List<UserDTO> getAllUsers();

	int updateUser(UserDTO userDTO, int principalId); // 권한 (내정보) - 인증 검사(세션 ID)

	int deleteUser(int id);

}
