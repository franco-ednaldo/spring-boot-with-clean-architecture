package com.fullcycle.admin.catalogo.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void testCreateMain() {
        Assertions.assertNotNull(new Main());
    }
}
