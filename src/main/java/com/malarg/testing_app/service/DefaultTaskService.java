package com.malarg.testing_app.service;

import com.malarg.testing_app.dao.TasksDao;
import com.malarg.testing_app.domain.Task;

import java.util.Collections;
import java.util.List;

public class DefaultTaskService implements TaskService {

    TasksDao dao;

    DefaultTaskService(TasksDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Task> getAllTasks() {
        return dao.getAll();
    }

    @Override
    public String convertToString(Task task) {
        var result = task.getQuestion();
        if (task.getAnswers().size() == 1) {
            return result;
        }
        Collections.shuffle(task.getAnswers());
        result += '\n';
        for (var i = 0; i < task.getAnswers().size(); i++) {
            result += i + 1 + ". " + task.getAnswers().get(i).toString();
            if (i <task.getAnswers().size() - 1) {
                result += '\n';
            }
        }
        return result;
    }
}
