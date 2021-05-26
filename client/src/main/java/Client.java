import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Write something: ");
        String text = sc.nextLine();

        try {
            Socket socket = new Socket("localhost",5050);

            var output = new PrintWriter(socket.getOutputStream());
            output.println(text+"\r\n\r\n");
            output.flush();

            var inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true){
                var line =  inputFromServer.readLine();
                if(line.isEmpty() || line == null)
                    break;
                System.out.println(line);
            }
            output.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
