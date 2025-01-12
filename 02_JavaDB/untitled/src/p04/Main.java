package p04;

import p03.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        //addMinion();

        connection.close();
    }

    private static void addMinion() throws IOException, SQLException {
        String[] minionTokens = reader.readLine().split("\\s+");
        String minionName = minionTokens[1];
        int minionAge = Integer.parseInt(minionTokens[2]);
        String minionTown = minionTokens[3];

        String[] villainTokens = reader.readLine().split("\\s+");
        String villainName = villainTokens[1];

        int townId = townExists(minionTown);
        if (townId == 0) {
            townId = createTown(minionTown);
        }
        
        int minionId = createMinion(minionName, minionAge, townId);

        int villainId = villainExists(villainName);
        if (villainId == 0) {
            villainId = createVillain(villainName);
        }

        assignMinionToVillain(villainId, minionId);
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
    }

    private static void assignMinionToVillain(int villainId, int minionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_villains VALUE (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();

    }

    private static int villainExists(String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }

    private static int createVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO villains(name, evilness_factor) VALUE (?, 'evil')");
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        System.out.printf("Villain %s was added to the database.%n", villainName);

        return resultSet.getInt("id");
    }

    private static int createMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions(name, age, town_id) VALUE (?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(1, minionAge);
        preparedStatement.setInt(1, townId);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int createTown(String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO towns(name) VALUE (?)");
        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        System.out.printf("Town %s was added to the database.%n", townName);
        return resultSet.getInt("id");
    }

    private static int townExists(String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }

        return 0;
    }
}
