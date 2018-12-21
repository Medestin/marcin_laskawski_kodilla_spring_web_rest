package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DbServiceTestSuite {
    @Autowired
    private DbService dbService;

    @Before
    public void insertTasksToDb(){
        Task task1 = new Task(1L, "title1", "content1");
        Task task2 = new Task(2L, "title2", "content2");
        Task task3 = new Task(3L, "title3", "content3");
        Task task4 = new Task(4L, "title4", "content4");

        dbService.saveTask(task1);
        dbService.saveTask(task2);
        dbService.saveTask(task3);
        dbService.saveTask(task4);
    }

    @Test
    public void testGetAllTasks(){
        List<Task> tasks = dbService.getAllTasks();

        assertEquals(4, tasks.size());
    }

    @Test
    public void testGetTaskById(){
        Task task = dbService.getTask(1L).get();

        assertEquals("title1", task.getTitle());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetTaskByIdWhenNotInDatabase(){
        Task task = dbService.getTask(555L).get();
    }

    @Test
    public void testDeleteTask(){
        Task task = new Task(5L, "test5", "content5");
        dbService.saveTask(task);

        dbService.deleteTask(5L);
        List<Task> tasks = dbService.getAllTasks();

        List<Task> filteredTasks = tasks.stream().filter(t -> t.getId() == 5L).collect(Collectors.toList());
        assertEquals(0, filteredTasks.size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteNonExistingTask(){
        dbService.deleteTask(1234L);
    }
}