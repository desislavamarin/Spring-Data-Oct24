package p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        getMinionNames();

        connection.close();
    }

    private static void getMinionNames() throws IOException, SQLException {
        int villainId = Integer.parseInt(reader.readLine());

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
            return;
        }

        System.out.printf("Villain: %s%n", resultSet.getString("name"));

        preparedStatement = connection.prepareStatement(
                "SELECT m.name, m.age FROM minions m " +
                        "JOIN minions_villains mv ON m.id = mv.minion_id " +
                        "WHERE mv.villain_id = ?");
        preparedStatement.setInt(1, villainId);
        resultSet = preparedStatement.executeQuery();
        int count = 1;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n", count++,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }

    }
}
