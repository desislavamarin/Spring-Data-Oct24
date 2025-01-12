package p02;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        DBConnection dbConnection = new DBConnection("minions_db", "root", "950990");

        getVillainNamesAndCount();

        //DBConnection.getConnection().close();
    }

    private static void getVillainNamesAndCount() throws SQLException {
        Statement statement = DBConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT v.name, COUNT(*) AS count FROM villains v " +
                    "JOIN minions_villains mv ON v.id = mv.villain_id " +
                    "GROUP BY v.name " +
                    "HAVING count > 15 " +
                    "ORDER BY count DESC ");

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int count = resultSet.getInt("count");
            System.out.printf("%s %d%n", name, count);
        }
    }
}
