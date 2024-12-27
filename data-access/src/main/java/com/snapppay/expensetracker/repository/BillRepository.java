package com.snapppay.expensetracker.repository;

import com.snapppay.expensetracker.entity.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill, Integer> {
}
