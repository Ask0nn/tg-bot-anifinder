package com.ask0n.enums;

import java.util.Arrays;

import static com.ask0n.Constants.PREFIX;

public enum Commands {
    START("start"), HELP("help"), RANDOM("random"), GENRES("genres"), FIND_BY_GENRES("findbygenres");

    private final String command;

    Commands(String command) {
        this.command = PREFIX + command;
    }

    public static Commands fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(Commands.values())
                .filter(v -> v.command.equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + s));
    }
}
