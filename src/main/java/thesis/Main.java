package thesis;

import thesis.entity.EntityManager;
import thesis.entity.EntityManagerImpl;
import thesis.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/thesis");

        EntityManager entityManager = new EntityManagerImpl(connection);

        // Create and persist a new user
        User newUser = new User("Hajik");
        System.out.println(newUser);
        entityManager.create(newUser);
        System.out.println("ID: " + newUser.getId());
        System.out.println("username: " + newUser.getUsername());

        System.out.println("Get all users");
        List<User> users = entityManager.findAll(User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }
}