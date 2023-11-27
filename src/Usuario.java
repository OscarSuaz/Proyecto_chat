import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Usuario {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private String id;


    public Usuario(Socket socket, String id){
        try {
            this.socket=socket;
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.id = id;
        } catch (Exception e) {
            cierraTodo(socket,br,bw);
        }
    }

    public void mandar(){
        try {
            bw.write(id);
            bw.newLine();
            bw.flush();

            Scanner sc = new Scanner(System.in);

            while (socket.isConnected()) {
                bw.write(id+": "+sc.nextLine());
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            cierraTodo(socket,br,bw);
        }
    }

    public void recibir(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String contenidoChat;

                while(socket.isConnected()){
                    try {
                        contenidoChat = br.readLine();
                        System.out.println(contenidoChat);
                    } catch (Exception e) {
                        cierraTodo(socket,br,bw);
                    }
                }
            }
        }).start();
    }

    public void cierraTodo(Socket socket, BufferedReader br, BufferedWriter bw){
        try {
            socket.close();
            br.close();
            bw.close();
        } catch (Exception e) {
            
        }
    }
}
