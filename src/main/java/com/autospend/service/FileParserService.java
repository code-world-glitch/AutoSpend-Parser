package com.autospend.service;

import com.autospend.model.Expense;
import com.autospend.repository.ExpenseRepository;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FileParserService {

    private final ExpenseRepository repository;

    public FileParserService(
            ExpenseRepository repository){

        this.repository=repository;
    }

    public void processFolder(
            String inputDir,
            String archiveDir)
            throws IOException {

        Files.createDirectories(
                Path.of(archiveDir));

        try(Stream<Path> files=
                    Files.list(
                            Path.of(inputDir))){

            files.forEach(file->{

                boolean processed = false;

                try{
                    processFile(file);
                    processed = true;
                }catch(Exception ex){
                    // log and continue - do not stop processing other files
                    System.err.println("Failed to process file: " + file + " -> " + ex.getMessage());
                }

                try{
                    // Move file to archive regardless of success to avoid re-processing
                    Files.move(
                            file,
                            Path.of(
                                    archiveDir,
                                    file.getFileName()
                                            .toString()
                            ),
                            StandardCopyOption
                                    .REPLACE_EXISTING);
                }catch(Exception e){
                    // If moving fails, print error but continue
                    System.err.println("Failed to move file to archive: " + file + " -> " + e.getMessage());
                    if(!processed){
                        // If both processing and moving failed, propagate to indicate problem
                        throw new RuntimeException(e);
                    }
                }

            });

        }

    }

    private void processFile(Path file){
        // Use Apache Commons CSV for robust parsing (handles quoted values and headers)
        try (Reader reader = Files.newBufferedReader(file);
             CSVParser csv = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csv) {

                // Expect headers: purchase_date, merchant, amount, category
                String dateStr = record.get("purchase_date");
                String merchant = record.get("merchant");
                String amountStr = record.get("amount");
                String category = record.get("category");

                Expense expense = new Expense(
                        LocalDate.parse(dateStr),
                        merchant,
                        new BigDecimal(amountStr),
                        category
                );

                repository.save(expense);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}