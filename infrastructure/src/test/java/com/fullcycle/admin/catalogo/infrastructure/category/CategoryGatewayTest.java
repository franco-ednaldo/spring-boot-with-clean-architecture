package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.infrastructure.GatewayTest;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@GatewayTest
class CategoryGatewayTest {

    @Autowired
    private CategoryGateway categoryGateway;

    @Autowired
    private CategoryRepository repository;


    @Test
    void testInjectDependencies() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(repository);
    }

}
