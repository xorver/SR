package zad2_server_java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Server {

    @SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
    private static int BUFLEN = 10000;

    @SuppressWarnings({"InfiniteLoopStatement", "ResultOfMethodCallIgnored"})
    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(Integer.parseInt(args[0]));
        String filePath = args[1];
        try {
            while (true) {
                Socket socket = ssocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] received = new byte[BUFLEN];
                File file = new File(filePath);
                file.createNewFile();
                FileOutputStream fileStream = new FileOutputStream(filePath);

                int read=0;
                while(read!=-1) {
                    // receive
                    read = in.read(received);
                    if(read>0)
                        fileStream.write(received,0,read);
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ssocket.close();
        }
    }
}
