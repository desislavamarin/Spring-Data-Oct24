package p05;

import p03.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        updateTownNames();

        connection.close();
    }

    private static void updateTownNames() throws IOException, SQLException {
        String country = reader.readLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE towns SET name = UPPER(name) WHERE country = ?");
        preparedStatement.setString(1, country);
        int count = preparedStatement.executeUpdate();

        if (count == 0) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.", count).append(System.lineSeparator());
            preparedStatement = connection.prepareStatement(
                    "SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> towns = new ArrayList<>();
            while (resultSet.next()) {
                towns.add(resultSet.getString("name"));
            }
            System.out.printf("[%s]", String.join(", ", towns));
        }
    }
}
