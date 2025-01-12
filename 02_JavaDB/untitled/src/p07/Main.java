package p07;

import p03.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        printMinions();

        connection.close();
    }

    private static void printMinions() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT name FROM minions");
        ArrayDeque<String> deque = new ArrayDeque<>();
        while (resultSet.next()) {
            deque.addLast(resultSet.getString("name"));
        }

        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
            if (!deque.isEmpty()) {
                System.out.println(deque.removeLast());
            }
        }

    }
}
