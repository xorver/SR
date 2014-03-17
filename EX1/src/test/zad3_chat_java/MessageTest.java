package test.zad3_chat_java;

import junit.framework.TestCase;
import org.junit.Test;
import zad3_chat_java.Message;

/**
 * Created by lichon on 3/17/14.
 */
public class MessageTest extends TestCase {

    @Test
    public void testEncode() throws Exception {
        Message underTest;
        byte[] encoded;

        underTest = new Message("login","message");
        encoded = underTest.encode();
        Message decodedMessage = new Message(encoded);
        assertEquals(underTest.toString(),decodedMessage.toString());
    }
}
