package com.efederation.Enums;

public enum NodeType {
    EMERGENCY("Emergency"),
    CONVERSATION("Conversation"),
    STORY("Story");

    private final String displayName;

    NodeType(String displayName) {this.displayName = displayName;}

    @Override
    public String toString() {return this.displayName;}
}
