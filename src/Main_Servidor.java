import java.io.IOException;
import java.net.*;
import java.util.*;

public class Main_Servidor{
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        ServerSocket socketServidor = new ServerSocket(1234);
        Servidor servidor = new Servidor(socketServidor);
        servidor.inicio();
        while (!sc.nextLine().equals("terminate")) {
            
        }
        servidor.terminar();
        sc.close();

    }
}