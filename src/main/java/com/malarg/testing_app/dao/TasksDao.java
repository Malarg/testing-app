package com.malarg.testing_app.dao;

import com.malarg.testing_app.domain.Task;
import java.util.List;

public interface TasksService {
    List<Task> getAll();
}
