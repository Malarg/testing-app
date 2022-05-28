package com.malarg.testing_app;

import com.malarg.testing_app.dao.DefaultTaskDao;
import com.malarg.testing_app.service.TaskService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TaskService service = context.getBean(TaskService.class);
        var tasks = service.getAllTasks();
        for (var task : tasks) {
            System.out.println(service.convertToString(task));
            System.out.println();
        }
    }
}