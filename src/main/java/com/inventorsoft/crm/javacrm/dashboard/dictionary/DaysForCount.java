package com.inventorsoft.crm.javacrm.dashboard.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DaysForCount {
    SEVEN(7),
    THIRTY(30),
    NINETY(90);

    @Getter
    private final int days;

}
