package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.exception.CategoryNotFoundException;
import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public final Category getCategory(CategoryType categoryType) {
        var category = categoryRepository.findByName(categoryType.toString());

        if (category.isPresent()) {
            return category.get();
        }

        throw new CategoryNotFoundException("Category not found");
    }

}
