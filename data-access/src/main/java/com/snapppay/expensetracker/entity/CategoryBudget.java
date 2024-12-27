package com.snapppay.expensetracker.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CategoryBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nonnull
    @ManyToOne
    @JoinColumn (name = "category_id", nullable = false)
    private Category category;

    @Nonnull
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Nonnull
    private BigDecimal budget;

    public CategoryBudget(Category category, User user, BigDecimal budget) {
        this.category = category;
        this.user = user;
        this.budget = budget;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof CategoryBudget categoryBudget) {
            return categoryBudget.getId().equals(this.getId());
        }

        return false;
    }
}
