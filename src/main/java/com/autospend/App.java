package com.autospend;

import com.autospend.repository.ExpenseRepository;
import com.autospend.service.FileParserService;
import com.autospend.util.DatabaseManager;

import java.util.List;

public class App {

    public static void main(String[] args)
            throws Exception {

        // Determine input and archive directories. Priority: command-line args -> env vars -> defaults
        String inputDir = (args != null && args.length >= 1 && args[0] != null && !args[0].isBlank())
                ? args[0]
                : System.getenv().getOrDefault("INPUT_DIR", "input");

        String archiveDir = (args != null && args.length >= 2 && args[1] != null && !args[1].isBlank())
                ? args[1]
                : System.getenv().getOrDefault("ARCHIVE_DIR", "archive");

        DatabaseManager manager=
                new DatabaseManager(
                        "jdbc:h2:./autospenddb"
                );

        manager.initializeDatabase();

        ExpenseRepository repository=
                new ExpenseRepository(
                        manager.getConnection()
                );

        FileParserService service=
                new FileParserService(
                        repository
                );

        service.processFolder(
                inputDir,
                archiveDir
        );

        System.out.println("""
                
===========================
AUTO SPEND DASHBOARD
===========================
Processing Complete
Expenses persisted
Files archived
===========================

""");

        // Print all persisted expenses as a quick query
        try {
            List<com.autospend.model.Expense> expenses = repository.getAllExpenses();
            System.out.println("Persisted expenses:");
            expenses.forEach(e -> System.out.println(
                    e.getId() + " | " + e.getPurchaseDate() + " | " + e.getMerchant() + " | " + e.getAmount() + " | " + e.getCategory()
            ));
        } catch (Exception ex) {
            System.err.println("Failed to query expenses: " + ex.getMessage());
        }

    }

}