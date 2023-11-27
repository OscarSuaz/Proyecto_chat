import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ManejoUSR  implements Runnable{
    public static ArrayList<ManejoUSR>manejadores = new ArrayList<>();
    public Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String id;

    public ManejoUSR(Socket socket){
        try {
            this.socket=socket;
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.id = br.readLine();
            manejadores.add(this);
            mensajeTodos(id+" a entrado al servidor");
        } catch (Exception e) {
            cierraTodo(socket,br,bw);
        }
    }

    @Override
    public void run() {
        String mensaje;
        while (socket.isConnected()) {
            try {
                mensaje=br.readLine();
                mensajeTodos(mensaje);
            } catch (Exception e) {
                cierraTodo(socket,br,bw);
            }
        }
    }
    public void mensajeTodos(String contenido){
        for (ManejoUSR usuario : manejadores) {
            try {
                if (!usuario.id.equals(id)) {
                    usuario.bw.write(contenido);
                    usuario.bw.newLine();
                    usuario.bw.flush();
                }
            } catch (Exception e) {
                cierraTodo(socket, br, bw);
            }
        }
    }

    public void salirChat(){
        manejadores.remove(this);
        mensajeTodos(id+" a salido");
    }

    public void cierraTodo(Socket socket, BufferedReader br, BufferedWriter bw){
        salirChat();
        try {
            if (br != null) {
                br.close();
            }
            if (bw != null) {
                bw.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Algo salió mal");
            e.printStackTrace();
        }
    }
}
