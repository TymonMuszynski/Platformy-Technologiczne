package org.example;
import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer nasłuchuje na porcie " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie z " + clientSocket.getRemoteSocketAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ) {
                out.writeObject("ready");

                int n = (int) in.readObject();

                out.writeObject("ready for messages");

                for (int i = 0; i < n; i++) {
                    Message message = (Message) in.readObject();
                    System.out.println("Otrzymano wiadomość z ( " + clientSocket.getRemoteSocketAddress() + " ): " + message.getNumber() + " - " + message.getContent());
                }

                out.writeObject("finished");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
