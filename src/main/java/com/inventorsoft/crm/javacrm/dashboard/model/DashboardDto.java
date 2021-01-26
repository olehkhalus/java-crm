package com.inventorsoft.crm.javacrm.dashboard.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class DashboardDto {

    String date;
    long leadsQuantity;

}
