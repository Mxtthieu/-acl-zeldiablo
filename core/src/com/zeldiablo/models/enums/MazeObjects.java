package com.zeldiablo.models.enums;

public enum MazeObjects {

    WALL('W');

    private final char element;

    MazeObjects(char c) { this.element = c; }

    public char getElement() { return this.element; }

}
