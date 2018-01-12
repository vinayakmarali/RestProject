package com.todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.model.ToDo;
import com.todo.model.ToDoDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoListApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetAll() throws Exception {
		Date d1 = new Date();
		ToDo todo1 = new ToDo("Meeting1", "Some meeting description1", d1);
		ToDoDao.setTodoMap(1, todo1);
		Date d2 = new Date();
		ToDo todo2 = new ToDo("Meeting2", "Some meeting description2", d2);
		ToDoDao.setTodoMap(2, todo2);
		mockMvc.perform(get("/todo/all")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.[0].title").value("Meeting1"))
				.andExpect(jsonPath("$.[0].description").value("Some meeting description1"))
				.andExpect(jsonPath("$.[0].dueDate").value(d1)).andExpect(jsonPath("$.[1].title").value("Meeting2"))
				.andExpect(jsonPath("$.[1].description").value("Some meeting description2"))
				.andExpect(jsonPath("$.[1].dueDate").value(d2));
	}

	@Test
	public void testGetOne1() throws Exception {
		Date d1 = new Date();
		ToDo todo1 = new ToDo("Meeting1", "Some meeting description1", d1);
		ToDoDao.setTodoMap(1, todo1);
		mockMvc.perform(get("/todo?id=1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.title").value("Meeting1"))
				.andExpect(jsonPath("$.description").value("Some meeting description1"))
				.andExpect(jsonPath("$.dueDate").value(d1));
	}
	
	@Test
	public void testGetOne2() throws Exception {
		mockMvc.perform(get("/todo?id=5")).andExpect(status().isNotFound());
	}

	@Test
	public void testPostOne1() throws Exception {
		mockMvc.perform(post("/todo").contentType(MediaType.APPLICATION_JSON_VALUE).param("id", "3")
				.param("title", "Meeting3").param("description", "Some meeting description3")
				.param("dueDate", "1515570511019").sessionAttr("todo", new ToDo())).andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.title").value("Meeting3"))
				.andExpect(jsonPath("$.description").value("Some meeting description3"))
				.andExpect(jsonPath("$.dueDate").value("1515570511019"));
	}
	
	@Test
	public void testPostOne2() throws Exception {
		mockMvc.perform(post("/todo").contentType(MediaType.APPLICATION_JSON_VALUE).param("id", "4")
				.param("titl", "Meeting2").param("description", "Some meeting description2")
				.param("dueDate", "abcd").sessionAttr("todo", new ToDo())).andExpect(status().isBadRequest());
	}

	@Test
	public void testPatchOne1() throws Exception {
		mockMvc.perform(patch("/todo").contentType(MediaType.APPLICATION_JSON_VALUE).param("id", "1")
				.content("{\"description\":\"Some meeting description3\",\"dueDate\":1515649069819}")
				.sessionAttr("todo", new ToDo())).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.description").value("Some meeting description3"))
				.andExpect(jsonPath("$.dueDate").value("1515649069819"));
	}
	
	@Test
	public void testPatchOne2() throws Exception {
		mockMvc.perform(patch("/todo").contentType(MediaType.APPLICATION_JSON_VALUE).param("id", "5")
				.content("{\"description\":\"Some meeting description3\",\"dueDate\":1515649069819}")
				.sessionAttr("todo", new ToDo())).andExpect(status().isNotFound());
	}
}
