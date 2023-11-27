import java.io.*;
import java.net.*;

public class Servidor {
    private ServerSocket socketserv;

    public Servidor(ServerSocket socket){
        this.socketserv=socket;
    }

    public void inicio(){
        try {
            while (!socketserv.isClosed()) {
                Socket socket= socketserv.accept();
                System.out.println("Nuevo usuario conectado");
                ManejoUSR manejador = new ManejoUSR(socket);

                Thread hilo = new Thread(manejador);
                hilo.start();
            }
        } catch (Exception e) {
            System.out.println("Hubo un error inesperado D:");
            e.printStackTrace();
        }
    }

    public void terminar(){
        try {
            if(socketserv != null){
                socketserv.close();
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error");
            e.printStackTrace();
        }
    }
}
