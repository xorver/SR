package zad1_server_java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Server {

    public static byte[] incShort(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        short s = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort(0);
        buffer.putShort(++s);
        return buffer.array();
    }
    public static byte[] incInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        int s = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
        buffer.putInt(++s);
        return buffer.array();
    }
    public static byte[] incLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        long s = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getLong(0);
        buffer.putLong(++s);
        return buffer.array();
    }

    @SuppressWarnings({"InfiniteLoopStatement", "ResultOfMethodCallIgnored"})
    public static void main(String[] args) throws IOException {
        if(args.length!=1){
            System.out.println("Usage: prog_name <PORT>");
            System.exit(1);
        }

        ServerSocket ssocket = new ServerSocket(Integer.parseInt(args[0]));
        try {
            while (true) {
                Socket socket = ssocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] received = new byte[128];

                int read=0;
                while(read!=-1) {
                    // receive
                    read = in.read(received);
                    System.out.println("bytes: "+read);
                    if(read==1)
                        out.write(received[0]+1);
                    else if(read==2)
                        out.write(incShort(received));
                    else if(read==4)
                        out.write(incInt(received));
                    else if(read==8)
                        out.write(incLong(received));
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
