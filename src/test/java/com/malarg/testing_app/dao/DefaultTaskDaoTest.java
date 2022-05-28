package com.malarg.testing_app.dao;

import com.malarg.testing_app.domain.Answer;
import com.malarg.testing_app.domain.Person;
import com.malarg.testing_app.domain.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс DefaultTaskDaoTest")
class DefaultTaskDaoTest {

    @DisplayName("инициализируется корректно")
    @Test
    void shouldInitializeCorrectly() {
        var dao = new DefaultTaskDao("42");

        assertThat(dao.getFilePath()).isEqualTo("42");
    }

    @DisplayName("генерирует список вопросов")
    @Test
    void shouldParseQuestionList() {
        var dao = new DefaultTaskDao("tasks.csv");
        var tasks = dao.getAll();

        assertThat(tasks)
                .hasSize(5)
                .hasOnlyElementsOfType(Task.class)
                .anyMatch(s -> s.getQuestion().equals("Who lives in a pineapple under the sea?") &&
                        s.getAnswers().stream().allMatch(p -> ((Answer) p).getValue() instanceof Person)
                ).anyMatch(s -> s.getQuestion().equals("What is the best time of the year?") &&
                        s.getAnswers().stream().allMatch(p -> ((Answer) p).getValue() instanceof String)
                ).anyMatch(s -> s.getQuestion().equals("What is the first word in Wine abbreviature?") &&
                        s.getAnswers().stream().allMatch(p -> ((Answer) p).getValue() instanceof String)
                ).anyMatch(s -> s.getQuestion().equals("2^7?") &&
                        s.getAnswers().stream().allMatch(p -> ((Answer) p).getValue() instanceof Integer)
                ).anyMatch(s -> s.getQuestion().equals("When was last Star Wars date? Type in dd.mm.yyyy format.") &&
                        s.getAnswers().stream().allMatch(p -> ((Answer) p).getValue() instanceof Date)
                );
    }
}