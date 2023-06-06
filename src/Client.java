import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        createLauncher();
        Scanner sc = new Scanner(System.in);

        Socket socket = new Socket("localhost", 6431);

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        URL myip = new URL("http://checkip.amazonaws.com/");
        BufferedReader reader = new BufferedReader(new InputStreamReader(myip.openStream()));

        writer.write("IP: ");
        writer.write(reader.readLine());
        writer.write("connected to the client\n");
        writer.flush();

        System.out.println("say something to the server!");
        String input;

        do {
            input = sc.next();
            writer.write(input + "\n");
            writer.flush();
        } while(!input.equals("stop"));
    }

    public static void createLauncher() {
        File launcher = new File("Client.bat");

        if(!launcher.exists()) {
            try {
                launcher.createNewFile();

                Writer writer = new FileWriter(launcher);
                String path = Client.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);

                writer.write("@echo off\n");
                writer.write("java -jar \"" + path + "\"\n");
                writer.write("pause\n");
                writer.write("exit");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
