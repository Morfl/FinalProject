package com.example.FinalProject.Enum;

import lombok.Getter;

@Getter
public enum OperationType {

    WITHDRAWAL("Снятие"),

    REFILL("Пополнение"),

    TRANSFER("Перевод");

    private final String description;

    OperationType(String description) {
        this.description = description;
    }

}
