package com.donghyeokseo.flow.database;

public class DatabaseTableHelper {
    public final String TokenTable =
            "CREATE TABLE Token ( " +
                    "idx INTEGER PRIMARY KEY, " +
                    "token TEXT )";

    public final String MealTable =
            "CREATE TABLE Meal ( " +
                    "idx INTEGER PRIMARY KEY, " +
                    "year INTEGER, " +
                    "month INTEGER, " +
                    "day INTEGER, " +
                    "breakfast TEXT, " +
                    "lunch TEXT, " +
                    "dinner TEXT )";
}
