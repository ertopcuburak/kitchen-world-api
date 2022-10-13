package com.bertopcu.KitchenWorld.util;

public enum NotificationType {
    NEW_FAV("NEW_FAV"),
    SYSTEM_ANNOUNCEMENT("SYSTEM_ANNOUNCEMENT"),
    RECIPE_DELETED("RECIPE_DELETED");

    private final String text;

    NotificationType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
