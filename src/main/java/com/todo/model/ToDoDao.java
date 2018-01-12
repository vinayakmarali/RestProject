package com.todo.model;

import java.util.HashMap;
import java.util.Map;

public class ToDoDao {

	private static Map<Long, ToDo> todoMap = new HashMap<>();

	public static ToDo getTodoMap(long id) {
		return todoMap.get(id);
	}

	public static Map<Long, ToDo> getTodoMap() {
		return todoMap;
	}

	public static void setTodoMap(long id, ToDo value) {
		todoMap.put(id, value);
	}
}
