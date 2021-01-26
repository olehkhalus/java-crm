package com.inventorsoft.crm.javacrm.domain.repository;

import com.inventorsoft.crm.javacrm.domain.model.entity.QLead;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeadQueryDslRepository {

    JPAQueryFactory query;

    public Map<LocalDate, Long> getDateAndCountMap(LocalDate localDate) {
        return query.select(QLead.lead.creatingDate)
                .from(QLead.lead)
                .where(QLead.lead.creatingDate.between(localDate, LocalDate.now()))
                .groupBy(QLead.lead.creatingDate)
                .transform(GroupBy.groupBy(QLead.lead.creatingDate).as(QLead.lead.creatingDate.count()));
    }
}