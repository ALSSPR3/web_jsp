package com.tenco;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Todo {

	private int id;
	private String title;
	private boolean completed;

	// 응용
	@Override
	public String toString() {

		return "{\"id\" : " + id + "}";
	}
}
