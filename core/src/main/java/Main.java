import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static List<String> billBoard = new ArrayList<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            while (true) {
                Socket client = serverSocket.accept();
                //Starta tråd
//                Thread thread = new Thread(() -> handleConnection(client));
//                thread.start();
                executorService.submit(() -> handleConnection(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handleConnection(Socket client) {
        try {
            var inputFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            readRequest(inputFromClient);

            var outputToClient = new PrintWriter(client.getOutputStream());
            sendResponse(outputToClient);

            inputFromClient.close();
            outputToClient.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(PrintWriter outputToClient) {
        synchronized (billBoard) {
            for (String s : billBoard) {
                outputToClient.println(s);
            }
        }

        outputToClient.println("\r\n");
        outputToClient.flush();
    }

    private static void readRequest(BufferedReader inputFromClient) throws IOException {
        List<String> tempList = new ArrayList<>();

        while (true) {
            var line = inputFromClient.readLine();
            if (line.isEmpty() || line == null)
                break;
            System.out.println(line);
            tempList.add(line);
        }

        synchronized (billBoard) {
            billBoard.addAll(tempList);
        }
    }

}
