package ping_server_java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    @SuppressWarnings({"InfiniteLoopStatement", "ResultOfMethodCallIgnored"})
    public static void main(String[] args) throws IOException {
        ServerSocket ssocket = new ServerSocket(Integer.parseInt(args[0]));
        try {
            while (true) {
                Socket socket = ssocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte sendline[] = "JavaServer".getBytes();
                byte recvline[] = new byte[128];
                // receive
                in.read(recvline);
                // send
                out.write(sendline);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                ssocket.close();
        }
    }
}
