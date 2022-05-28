package com.malarg.testing_app.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Answer<T> {
    private final T value;

    public T getValue() {
        return value;
    }

    public Answer(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer<?> answer = (Answer<?>) o;
        return value.equals(answer.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        if (value instanceof Person) {
            return ((Person) value).getFirstName() + " " + ((Person) value).getLastName();
        }
        if (value instanceof Date) {
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            return parser.format((Date)value);
        }
        return value.toString();
    }
}
