package com.fullcycle.admin.catalogo.infrastructure.category.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    Page<CategoryEntity> findAll(Specification<CategoryEntity> whereClause, Pageable pageable);
}
