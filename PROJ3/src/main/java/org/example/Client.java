package org.example;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        try (
                Socket clientSocket = new Socket(HOST, PORT);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ) {
            String message = (String) in.readObject();
            System.out.println(message);

            System.out.print("Ilość widadomosci: ");
            int n = scanner.nextInt();

            out.writeObject(n);

            message = (String) in.readObject();
            System.out.println(message);

            for (int i = 0; i < n; i++) {
                System.out.print("Treść wiadomości: ");
                String content = scanner.next();

                Message message1 = new Message(i, content);
                out.writeObject(message1);
            }

            message = (String) in.readObject();
            System.out.println(message);
        }
    }
}
