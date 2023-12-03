
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Servidor {

    private int port;
    private List<Client> clients;
    private ServerSocket server;

    public Servidor(int port) {
        this.port = port;
        this.clients = new ArrayList<Client>();
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
        protected void finalize() throws IOException {
            this.close();
        }
        };
        System.out.println("El puerto de conexion es 12345");

        while (true) {
        // accepts a new client
        Socket client = server.accept();

        // get nickname of newClient
        String nickname = (new Scanner ( client.getInputStream() )).nextLine();
        nickname = nickname.replace(" ", "_");
        System.out.println("Nuevo Usuario: \"" + nickname + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());

        //nuevo usuario
        Client newUser = new Client(client, nickname);

        // usuario a lista de usuarios conectados
        this.clients.add(newUser);

        //mensaje de login
        newUser.getOutStream().println("<b>Bienvenido</b> " + newUser.toString());

        // Hilo para el usuario
        new Thread(new ManejoUSR(this, newUser)).start();
        }
    }

    // borrar usuario de lista conectados
    public void removeUser(Client user){
        this.clients.remove(user);
    }

    // broadcast de mensajes
    public void broadcastMessages(String msg, Client userSender) {
        for (Client client : this.clients) {
        client.getOutStream().println(
            userSender.toString() + "<span>: " + msg+"</span>");
        }
    }

    // broadcast de usuarios
    public void broadcastAllUsers(){
        for (Client client : this.clients) {
        client.getOutStream().println(this.clients);
        }
    }
}
