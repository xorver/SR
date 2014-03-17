package zad3_chat_java;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lichon on 3/16/14.
 */
public class MessageSendingLoop implements Runnable {

    private MulticastSocket socket;
    private String login;
    private InetAddress address;
    private int port;

    public MessageSendingLoop(MulticastSocket socket,String login, InetAddress address, int port) {
        this.socket=socket;
        this.login=login;
        this.address=address;
        this.port=port;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true) try {
            String line = System.console().readLine();
            byte[] encodedMessage = new Message(login, line).encode();
            socket.send(new DatagramPacket(encodedMessage, encodedMessage.length, address, port));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
