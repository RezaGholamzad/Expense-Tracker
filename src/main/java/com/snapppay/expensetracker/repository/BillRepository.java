package com.snapppay.expensetracker.repository;

import com.snapppay.expensetracker.entity.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Set;

@Repository
public interface BillRepository extends CrudRepository<Bill, Integer> {

    Set<Bill> findAllByUserIdAndCreationDateBetween(Long userId, ZonedDateTime from, ZonedDateTime to);
}
