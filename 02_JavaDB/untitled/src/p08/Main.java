package p08;

import p03.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        increaseMinionAge();

        connection.close();
    }

    private static void increaseMinionAge() throws IOException, SQLException {
        int[] ids = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < Arrays.stream(ids).count(); i++) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE minions SET name = LOWER(name), age = age + 1 " +
                        "WHERE id = ?");
            preparedStatement.setInt(1, ids[i]);
            preparedStatement.executeUpdate();
        }

        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT name, age FROM minions");
        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"),
                    resultSet.getInt("age"));
        }

    }
}
