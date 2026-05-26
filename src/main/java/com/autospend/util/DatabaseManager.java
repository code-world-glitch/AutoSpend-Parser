package com.autospend.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseManager {

    private final String url;

    public DatabaseManager(String url){
        this.url=url;
    }

    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(url,"sa","");
    }

    public void initializeDatabase(){

        try(Connection conn=getConnection();
            Statement st=conn.createStatement()){

            String sql=
                    new BufferedReader(
                            new InputStreamReader(
                                    getClass()
                                    .getClassLoader()
                                    .getResourceAsStream("db-init.sql")
                            ))
                            .lines()
                            .collect(Collectors.joining());

            st.execute(sql);

        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}