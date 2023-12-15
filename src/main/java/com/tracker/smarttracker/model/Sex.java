package com.tracker.smarttracker.model;

public enum Sex {
    MALE("male sex"),
    FEMALE("female sex");

    private final String description;

    Sex(String description) {
        this.description = description;
    }
}
