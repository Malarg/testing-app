package com.malarg.testing_app.service;

import com.malarg.testing_app.domain.Answer;
import com.malarg.testing_app.domain.Task;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface TaskService {
    List<Task> getAllTasks();

    String convertToString(Task task);
}
