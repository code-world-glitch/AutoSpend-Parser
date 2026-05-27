package com.autospend.service;

import com.autospend.repository.ExpenseRepository;
import com.autospend.util.DatabaseManager;

import org.junit.Test;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileParserServiceTest {

    DatabaseManager manager;

    ExpenseRepository repository;
    FileParserService service;

    @BeforeEach
    void setup() throws Exception{

        manager=
                new DatabaseManager(
                 "jdbc:h2:mem:testdb"
                );

        manager.initializeDatabase();

        repository=
                new ExpenseRepository(
                    manager.getConnection());

        service=
                new FileParserService(
                        repository);

        Files.createDirectories(
                Path.of("testinput"));

        Files.writeString(
                Path.of(
                        "testinput/test.csv"),
"""
purchaseDate,merchant,amount,category
2026-05-22,Target,55.00,Shopping
""");

    }

    @AfterEach
    void teardown()
            throws Exception{

        Files.deleteIfExists(
                Path.of(
                "testinput/test.csv"));

        Files.deleteIfExists(
                Path.of("testinput"));

    }

    @Test
    void shouldReadAndPersistFile()
            throws Exception{

        service.processFolder(
                "testinput",
                "archive");

        assertEquals(
                1,
                repository
                .getAllExpenses()
                .size()
        );

    }

}