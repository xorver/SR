package zad3_chat_java;

import java.io.*;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatClient {

    public static void main(String[] args) throws IOException {
        if(args.length != 3){
            System.out.println("Usage: prog_name <GROUP_IP> <PORT> <LOGIN>");
            System.exit(1);
        }

        InetAddress group = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);
        String login = args[2];
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        new Thread(new MessageListeningLoop(socket,login)).start();
        new Thread(new MessageSendingLoop(socket,login,group,port)).start();
    }
}
