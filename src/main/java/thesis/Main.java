package thesis;

import thesis.entity.EntityManager;
import thesis.entity.EntityManagerImpl;
import thesis.model.Student;
import thesis.model.User;
import thesis.model.UserComponent;
import thesis.model.UserDecorator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/thesis");

        EntityManager entityManager = new EntityManagerImpl(connection);

        // Create and persist a new user
        User newUser = new UserComponent("Hajik");
        System.out.println(newUser);
        entityManager.create(newUser);
        System.out.println("ID: " + newUser.getId());
        System.out.println("username: " + newUser.getUsername());

        System.out.println("Get all users");
        List<UserComponent> users = entityManager.findAll(UserComponent.class);
        for (UserComponent user : users) {
            System.out.println(user);
        }
    }
}