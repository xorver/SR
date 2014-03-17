package zad3_chat_java;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by lichon on 3/16/14.
 */
public class Message {

    private String login;
    private String message;
    private String timestamp;

    public Message(String login, String message) {
        if(login.length() > MessageSizeProperties.LOGIN_SIZE)
            throw new IllegalArgumentException("Login exceeds maximum size (6 characters)");
        if(message.length() > MessageSizeProperties.MESSAGE_SIZE)
            throw new IllegalArgumentException("Message exceeds maximum size (20 characters)");
        this.login = login;
        this.message = message;
        this.timestamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    public Message(byte[] encoded) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder timestampBuffer = new StringBuilder();
        StringBuilder loginBuffer = new StringBuilder();
        StringBuilder messageBuffer = new StringBuilder();
        ByteBuffer checksumBuffer = ByteBuffer.allocate(MessageSizeProperties.CHECKSUM_SIZE);
        int k=0;
        for(int i=0;i<8;i++,k++)
            if(encoded[k]!=0)
                timestampBuffer.append((char)encoded[k]);
        for(int i=0;i< MessageSizeProperties.LOGIN_SIZE;i++,k++)
            if(encoded[k]!=0)
                loginBuffer.append((char)encoded[k]);
        for(int i=0;i< MessageSizeProperties.MESSAGE_SIZE;i++,k++)
            if(encoded[k]!=0)
                messageBuffer.append((char)encoded[k]);
        for(;k<encoded.length;k++)
            checksumBuffer.put(encoded[k]);
        this.timestamp=timestampBuffer.toString();
        this.login=loginBuffer.toString();
        this.message=messageBuffer.toString();
        if(!checksumsEqual(checksumBuffer.array(),this.getChecksum()))
            throw new IllegalArgumentException("Checksum of message is invalid");

    }

    private byte[] getChecksum() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MessageDigest.getInstance("MD5").digest(this.toString().getBytes("UTF-8"));
    }

    private static boolean checksumsEqual(byte[] checksum1,byte[] checksum2){
        if(checksum1.length!=checksum2.length)
            return false;
        for(int i=0;i<checksum1.length;i++)
            if(checksum1[i] != checksum2[i])
                return false;
        return true;
    }

    public byte[] encode() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int messageSize = MessageSizeProperties.FULL_MESSAGE_SIZE;
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);

        //encode timestamp
        buffer.put(timestamp.getBytes("US-ASCII"));
        //encode login
        buffer.put(login.getBytes("US-ASCII"));
        for(int i=login.length();i< MessageSizeProperties.LOGIN_SIZE;i++)
            buffer.put((byte)0);
        //encode message
        buffer.put(message.getBytes("US-ASCII"));
        for(int i=message.length();i< MessageSizeProperties.MESSAGE_SIZE;i++)
            buffer.put((byte)0);
        //encode checksum
        buffer.put(getChecksum());
        //prepare result
        return buffer.array();
    }

    @Override
    public String toString() {
        return "["+timestamp+"]["+login+"]: "+message;
    }

    public String getLogin() {
        return login;
    }

    public static class MessageSizeProperties{
        public static int LOGIN_SIZE = 6;
        public static int MESSAGE_SIZE = 20;
        public static int TIMESTAMP_SIZE = 8;
        public static int CHECKSUM_SIZE = 16;
        public static int FULL_MESSAGE_SIZE = LOGIN_SIZE + MESSAGE_SIZE + TIMESTAMP_SIZE + CHECKSUM_SIZE;
    }

}
