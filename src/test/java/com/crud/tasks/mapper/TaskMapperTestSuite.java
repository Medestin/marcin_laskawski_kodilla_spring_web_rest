package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskMapperTestSuite {
    private TaskMapper taskMapper = new TaskMapper();

    @Test(expected = NullPointerException.class)
    public void testMapToTaskWithNull(){
        TaskDto taskDto = null;

        taskMapper.mapToTask(taskDto);
    }

    @Test
    public void testMapToTask(){
        TaskDto taskDto = new TaskDto(1L, "test", "content");
        Task task = taskMapper.mapToTask(taskDto);

        assertEquals("test", task.getTitle());
    }

    @Test
    public void testMapToTaskDto(){
        Task task = new Task(1L, "test", "content");
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        assertEquals("test", taskDto.getTitle());
    }

    @Test
    public void testMapToTaskDtoList(){
        Task task = new Task(1L, "test", "content");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        assertEquals(1, taskDtos.size());
        assertEquals("test", taskDtos.get(0).getTitle());
    }

    @Test
    public void testMapToTaskDtoListWithEmptyList(){
        List<Task> tasks = new ArrayList<>();
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        assertEquals(0, taskDtos.size());
    }

    @Test(expected = NullPointerException.class)
    public void testMapToTaskDtoListWithNull(){
        List<Task> tasks = null;
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
    }
}