package zad3_chat_java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lichon on 3/16/14.
 */
public class MessageListeningLoop implements Runnable {

    private MulticastSocket socket;
    private String login;

    public MessageListeningLoop(MulticastSocket socket,String login) {
        this.socket=socket;
        this.login=login;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            try {
                DatagramPacket received=new DatagramPacket(ByteBuffer.allocate(Message.MessageSizeProperties.FULL_MESSAGE_SIZE).array(),Message.MessageSizeProperties.FULL_MESSAGE_SIZE);
                Message message;
                socket.receive(received);
                try {
                    message = new Message(received.getData());
                    if(!message.getLogin().equals(login))
                        System.out.println(message.toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
