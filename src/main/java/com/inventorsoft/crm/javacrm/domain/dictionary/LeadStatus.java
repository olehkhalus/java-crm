package com.inventorsoft.crm.javacrm.domain.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LeadStatus {

    NEW ("New"),
    IN_PROGRESS ("In Progress"),
    LOST_OPPORTUNITY ("Lost Opportunity"),
    CONTACT_LATER ("Contact Later"),
    ACTIVE_CLIENT ("Active Client"),
    PAST_CLIENT ("Past Client"),
    NOT_INTERESTED ("Not Interested");

    @Getter
    private final String label;

}
