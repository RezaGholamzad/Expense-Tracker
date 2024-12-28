package com.snapppay.expensetracker.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Nonnull
    private BigDecimal amount;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String title;

    private ZonedDateTime creationDate;

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

        if (obj instanceof Bill bill) {
            return bill.getId().equals(this.getId());
        }

        return false;
    }

}
