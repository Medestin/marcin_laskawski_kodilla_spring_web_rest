package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
    TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyList () throws Exception {
        List<TaskDto> taskDtos = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(taskDtos);

        mockMvc.perform(get("/v1/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTaskList () throws Exception {
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "test", "test content"));
        taskDtos.add(new TaskDto(2L, "test2", "test content2"));

        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(taskDtos);

        mockMvc.perform(get("/v1/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void shouldFetchTask () throws Exception {
        Task task = new Task(1L, "test", "test content");
        TaskDto taskDto = new TaskDto(1L, "test", "test content");

        when(dbService.getTask(anyLong())).thenReturn(java.util.Optional.ofNullable(task));
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any())).thenReturn(taskDto);

        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")));
    }

    @Test
    public void shouldCreateTask () throws Exception {
        TaskDto taskDto = new TaskDto(1L, "test", "test content");
        Task task = new Task(1L, "test", "test content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldUpdateTask () throws Exception {
        TaskDto updatedTaskDto = new TaskDto(1L, "update", "update content");
        Task updatedTask = new Task(1L, "update", "update content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(updatedTask);
        when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(updatedTask);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(updatedTaskDto);

        mockMvc.perform(put("/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", is("update")));
    }
}