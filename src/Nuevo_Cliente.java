import java.util.*;
import java.io.IOException;
import java.net.*;

public class Nuevo_Cliente {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cual es tu nombre?");
        String id = sc.nextLine();
        Socket socket = new Socket("localhost",1234);
        Usuario usuario = new Usuario(socket,id);
        usuario.recibir();
        usuario.mandar();
    }
}
