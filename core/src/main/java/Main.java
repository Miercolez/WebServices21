import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        //localhost 127.0.0.1
        // Martins ip: 178.174.162.51

        try(ServerSocket socket = new ServerSocket(80)){

            while (true) {
                Socket client = socket.accept();
                System.out.println(client.getInetAddress());
                var inputFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

                inputFromClient.lines().forEach(System.out::println);
                inputFromClient.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
