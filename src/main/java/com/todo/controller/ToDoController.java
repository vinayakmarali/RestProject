package com.todo.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.model.ToDo;
import com.todo.model.ToDoDao;

@RestController
public class ToDoController {

	private static long counter = 2;

	@RequestMapping(value = "/todo/all", method = RequestMethod.GET)
	public Collection<ToDo> getAll() {
		return ToDoDao.getTodoMap().values();
	}

	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public ResponseEntity<ToDo> getOne(@RequestParam(value = "id", required = true) String id) {
		if (ToDoDao.getTodoMap(Long.parseLong(id)) != null) {
			return new ResponseEntity<ToDo>(ToDoDao.getTodoMap(Long.parseLong(id)), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/todo", method = RequestMethod.POST)
	public ResponseEntity<ToDo> postOne(@Valid String id, @Valid String title, @Valid String description,
			@Valid String dueDate) {
		try {
			ToDo todo = new ToDo(title, description, new Date(Long.parseLong(dueDate)));
			ToDoDao.setTodoMap(++counter, todo);
			return new ResponseEntity<ToDo>(ToDoDao.getTodoMap(counter), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ToDo>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/todo", method = RequestMethod.PATCH)
	public ResponseEntity<ToDo> patchOne(@RequestParam String id, @RequestBody @Valid ToDo todo) {
		ToDo getTodo = ToDoDao.getTodoMap(Long.parseLong(id));
		if (getTodo != null) {
			if (todo.getTitle() != null) {
				getTodo.setTitle(todo.getTitle());
			}

			if (todo.getDescription() != null) {
				getTodo.setDescription(todo.getDescription());
			}

			if (todo.getDueDate() != null) {
				getTodo.setDueDate(todo.getDueDate());
			}

			ToDoDao.setTodoMap(Long.parseLong(id), getTodo);
			return new ResponseEntity<ToDo>(getTodo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
}