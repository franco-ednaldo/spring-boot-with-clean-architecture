package com.fullcycle.admin.catalogo.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryInterface extends JpaRepository<CategoryEntity, String> {

}
