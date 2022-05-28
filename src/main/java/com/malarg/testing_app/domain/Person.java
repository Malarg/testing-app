package com.malarg.testing_app.domain;

public class Person {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person(" + firstName + "," + lastName + ")";
    }

    //Плохое решение, но json в CSV не хотелось вставлять
    public static Person tryParse(String value) {
        if (value.startsWith("Person(") && value.endsWith(")") && value.contains(",")) {
            var firstAndLastName = value.replace("Person(", "").replace(")", "").split(",");
            return new Person(firstAndLastName[0], firstAndLastName[1]);
        }
        return null;
    }
}
