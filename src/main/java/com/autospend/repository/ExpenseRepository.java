package com.autospend.repository;

import com.autospend.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    private final Connection connection;

    public ExpenseRepository(Connection connection){
        this.connection=connection;
    }
    public void save(Expense e){

        String sql="""
                INSERT INTO expenses
                (
                purchase_date,
                merchant,
                amount,
                category,
                processed_at
                )
                VALUES(?,?,?,?,?)
                """;

        try(PreparedStatement ps=
                    connection.prepareStatement(sql)){

            ps.setDate(1,
                    Date.valueOf(e.getPurchaseDate()));

            ps.setString(2,e.getMerchant());

            ps.setBigDecimal(3,e.getAmount());

            ps.setString(4,e.getCategory());

            ps.setTimestamp(
                    5,
                    Timestamp.valueOf(
                            e.getProcessedAt()
                    )
            );

            ps.executeUpdate();

        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public List<Expense> getAllExpenses(){

        List<Expense> expenses=
                new ArrayList<>();

        try(
                PreparedStatement ps=
                connection.prepareStatement(
                        "SELECT * FROM expenses");

                ResultSet rs=
                ps.executeQuery()){

            while(rs.next()){

                Expense e=new Expense();

                e.setId(rs.getLong("id"));

                e.setPurchaseDate(
                        rs.getDate(
                                "purchase_date")
                                .toLocalDate());

                e.setMerchant(
                        rs.getString("merchant"));

                e.setAmount(
                        rs.getBigDecimal("amount"));

                e.setCategory(
                        rs.getString("category"));

                expenses.add(e);

            }

        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

        return expenses;
    }
}