package p09;

import com.sun.tools.jconsole.JConsoleContext;
import p03.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, SQLException {

        increaseAgeProcedure();

        connection.close();
    }

    private static void increaseAgeProcedure() throws SQLException, IOException {
        int minionId = Integer.parseInt(reader.readLine());
        CallableStatement callableStatement = connection.prepareCall(
                "CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.execute();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT name, age FROM minions WHERE id = ?");
        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.printf("%s %d %n", resultSet.getString("name"),
                resultSet.getInt("age"));
    }
}
