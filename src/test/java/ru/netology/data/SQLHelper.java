package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String user = "app";
    private static final String password = "pass";

    private static final QueryRunner runner = new QueryRunner();
    private final Connection conn = getConnection();

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(System.getProperty("db.url"), user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearTables() {
        var runner = new QueryRunner();
        var clearCreditRequestTableQuery = "DELETE FROM credit_request_entity;";
        var clearOrderTableQuery = "DELETE FROM order_entity;";
        var clearPaymentTableQuery = "DELETE FROM payment_entity;";
        try (var conn = getConnection()) {
            runner.update(conn, clearCreditRequestTableQuery);
            runner.update(conn, clearOrderTableQuery);
            runner.update(conn, clearPaymentTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public String getPaymentStatus() {
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditRequestStatus() {
        var status = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return runner.query(conn, status, new ScalarHandler<>());
    }
}