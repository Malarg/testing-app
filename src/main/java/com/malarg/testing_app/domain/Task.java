package com.malarg.testing_app.dao;

import java.util.List;

public class Task<T> {
    String question;
    List<Answer<T>> answers;
    Answer<T> correctAnswer;
}
