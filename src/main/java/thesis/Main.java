package thesis;

import thesis.entity.EntityManager;
import thesis.entity.EntityManagerImpl;
import thesis.model.StudentComponent;
import thesis.model.UserComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/thesis");

        EntityManager entityManager = new EntityManagerImpl(connection);

        // Create and persist a new user
        UserComponent newUser = new UserComponent("Hajik");
        entityManager.create(newUser);

        StudentComponent newStudent = new StudentComponent(new UserComponent("Hajikk"), "elementary");
        entityManager.create(newStudent);

        System.out.println("Get all users");
        List<UserComponent> users = entityManager.findAll(UserComponent.class);
        for (UserComponent user : users) {
            System.out.println(user);
        }

        System.out.println("Get all students");
        List<StudentComponent> students = entityManager.findAll(StudentComponent.class);
        for (StudentComponent student : students) {
            System.out.println(student);
        }
    }
}