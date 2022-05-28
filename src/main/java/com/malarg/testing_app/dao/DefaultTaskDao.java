package com.malarg.testing_app.dao;

import com.malarg.testing_app.domain.Answer;
import com.malarg.testing_app.domain.Person;
import com.malarg.testing_app.domain.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class DefaultTaskDao implements TasksDao {

    private final CSVFormat csvFormat;
    private final String filePath;

    public CSVFormat getCsvFormat() {
        return csvFormat;
    }

    public String getFilePath() {
        return filePath;
    }

    public DefaultTaskDao(String filePath) {
        csvFormat = org.apache.commons.csv.CSVFormat.DEFAULT.builder().setDelimiter("|").build();
        this.filePath = filePath;
    }

    @Override
    public List<Task> getAll() {
        var lines = readCsvAsString();
        var csvArray = parseCSV(lines);

        var result = new ArrayList<Task>(csvArray.size());
        for (var line : csvArray) {
            var question = line.get(0);
            var answers = line.stream().skip(1).collect(Collectors.toList());
            result.add(firstNonNull(new Callable[]{
                    () -> tryParsePeople(question, answers),
                    () -> tryParseInt(question, answers),
                    () -> tryParseDate(question, answers),
                    () -> parseString(question, answers)
            }));
        }
        return result;
    }

    private String readCsvAsString() {
        var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader;
        if (inputStream == null) {
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        var linesArray = reader.lines().toArray();
        var stringArray = Arrays.copyOf(linesArray, linesArray.length, String[].class);
        return String.join("\n", stringArray);
    }

    private List<CSVRecord> parseCSV(String lines) {
        try {
            var parser = CSVParser.parse(lines, csvFormat);
            return parser.stream().skip(1).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private Task<Person> tryParsePeople(String question, List<String> answers) {
        var people = answers.stream().map(Person::tryParse).map(Answer::new).collect(Collectors.toList());
        if (people.stream().anyMatch(a -> a.getValue() == null)) {
            return null;
        }
        return new Task<>(question, people, people.get(0));
    }

    private Task<Integer> tryParseInt(String question, List<String> answers) {
        var integers = answers.stream().map(this::stringToInt).collect(Collectors.toList());
        if (integers.stream().anyMatch(Objects::isNull)) {
            return null;
        }
        var integersList = integers.stream().map(Answer::new).collect(Collectors.toList());
        return new Task<>(question, integersList, integersList.get(0));
    }

    private Integer stringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Task<Date> tryParseDate(String question, List<String> answers) {
        var dates = answers.stream().map(this::stringToDate).map(Answer::new).collect(Collectors.toList());
        if (dates.stream().anyMatch(a -> a.getValue() == null)) {
            return null;
        }
        return new Task<>(question, dates, dates.get(0));
    }

    private Date stringToDate(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            return parser.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    private Task<String> parseString(String question, List<String> answers) {
        var answersList = answers.stream().map(Answer::new).collect(Collectors.toList());
        return new Task<>(question, answersList, answersList.get(0));
    }

    private <T> T firstNonNull(Callable<?>[] values) {
        for (var value : values) {
            try {
                var result = value.call();
                if (result != null) {
                    return (T) result;
                }
            } catch (Exception e) {}
        }
        return null;
    }
}
