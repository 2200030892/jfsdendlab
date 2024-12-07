package com.klef.jfsd.exam.UserManagementSystem;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ClientDemo {
    public static void main(String[] args) {
        // Create a Hibernate session factory
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = null;

        Scanner sc = new Scanner(System.in);

        try {
            // Prompt the user to choose an operation
            System.out.println("Choose an operation: ");
            System.out.println("1. Insert User");
            System.out.println("2. Delete User");
            int choice = sc.nextInt();

            if (choice == 1) {
                // Start transaction
                transaction = session.beginTransaction();

                // Taking input from the console to insert a new user
                System.out.println("Enter User ID: ");
                int id = sc.nextInt();  // Reading user ID

                sc.nextLine();  // Consume newline left-over

                System.out.println("Enter Username: ");
                String username = sc.nextLine();

                System.out.println("Enter Password: ");
                String password = sc.nextLine();

                System.out.println("Enter Email: ");
                String email = sc.nextLine();

                // Create a new user object
                User user = new User();
                user.setId(id);  // Set the ID from console input
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                // Save the user object (insert into database)
                session.save(user);
                transaction.commit();
                System.out.println("User inserted successfully!");

            } else if (choice == 2) {
                // Start transaction
                transaction = session.beginTransaction();

                // Taking input from the console to delete a user
                System.out.println("Enter User ID to delete: ");
                int id = sc.nextInt();  // Reading user ID for deletion

                // Load the user object from the database using the ID
                User userToDelete = session.get(User.class, id);

                if (userToDelete != null) {
                    // If user is found, delete the user
                    session.delete(userToDelete);
                    transaction.commit();
                    System.out.println("User deleted successfully!");
                } else {
                    System.out.println("User with ID " + id + " not found.");
                }
            } else {
                System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
