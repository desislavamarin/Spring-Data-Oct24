package bg.softuni;

import bg.softuni.entities.User;
import bg.softuni.orm.config.MyConnector;
import bg.softuni.orm.context.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        MyConnector.createConnection("root", "950990", "mini_orm");

        Connection connection = MyConnector.getConnection();
        EntityManager em = new EntityManager(connection);

        User user1 = new User("user1", "pass", 20, LocalDate.now());
        User user2 = new User("user2", "pass2", 22, LocalDate.now());
        user2.setId(2);

        em.persist(user1);
    }
}