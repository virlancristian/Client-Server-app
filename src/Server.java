import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        createLauncher();

        ServerSocket serverSocket = new ServerSocket(6431);

        System.out.println("Waiting for client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        BufferedReader reader1 = new BufferedReader(reader);

        String s = reader1.readLine();

        System.out.println(s);

        while(!s.equals("stop")) {
            System.out.println(s);
            s = reader1.readLine();
        }

        System.out.println("connection closed");
    }

    public static void createLauncher() {
        File launcher = new File("Server.bat");

        if(!launcher.exists()) {
            try {
                launcher.createNewFile();

                Writer writer = new FileWriter(launcher);
                String path = Server.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);

                writer.write("@echo off\n");
                writer.write("java -jar \"" + path + "\"\n");
                writer.write("pause\n");
                writer.write("exit");
                writer.close();

                Runtime.getRuntime().exec("cmd /c Server.bat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
