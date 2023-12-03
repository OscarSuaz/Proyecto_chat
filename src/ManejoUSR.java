
import java.io.*;
import java.util.*;
import java.net.Socket;
import java.util.ArrayList;

class ManejoUSR implements Runnable {

    private Servidor server;
    private Client user;

    public ManejoUSR(Servidor server, Client user) {
        this.server = server;
        this.user = user;
        this.server.broadcastAllUsers();
    }

    public void run() {
        String message;

        // Muestra nuevos mensajes a todos
        Scanner sc = new Scanner(this.user.getInputStream());
        while (sc.hasNextLine()) {
        message = sc.nextLine();
        server.broadcastMessages(message, user);
        }
        // Al matar el hilo
        server.removeUser(user);
        this.server.broadcastAllUsers();
        sc.close();
    }
}