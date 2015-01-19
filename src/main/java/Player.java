import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;

/**
 * Created by andrew on 19.01.15.
 */
public class Player {
    public String name;
    private int id;
    private static int counter = 0;
    public SelectionKey client;
    ByteBuffer Buffer = ByteBuffer.allocate(8192);
    CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();


    public Player(SelectionKey client, String name) {
        this.client = client;
        this.name = name;
    }

    public void sendMsg(String msg) throws IOException {
        ((SocketChannel) client.channel()).write(encoder.encode(CharBuffer.wrap(msg)));
    }

    public String blockingReadMsg() {
        try {
            while (!(((client.readyOps() & SelectionKey.OP_READ) ==
                    SelectionKey.OP_READ)))
                Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "err:sleep";
        }

        try {
            SocketChannel sc = (SocketChannel) client.channel();
            sc.read(Buffer);
            return new String(Buffer.array(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return "err:readingError";
        }
    }
}
