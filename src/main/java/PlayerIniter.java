import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by andrew on 19.01.15.
 */
public class PlayerIniter extends Thread {
    public SelectionKey client;
    ByteBuffer Buffer = ByteBuffer.allocate(8192);

    PlayerIniter(SelectionKey client) {
        this.client = client;
    }

    public void run() {
        try {
            Player player = new Player(client, blockingReadMsg());
            String[] splited = blockingReadMsg().split(":");//name:creat|connect:isWhite
            if (splited[1] == "createNew") {
                PartyManager.instance.createPartyAndConnect(player, splited[2] == "isWhite", splited[0]);
            } else {
                PartyManager.instance.connectToParty(player, splited[0]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String blockingReadMsg() throws InterruptedException, IOException {
        while (!(((client.readyOps() & SelectionKey.OP_READ) ==
                SelectionKey.OP_READ)))
            Thread.currentThread().sleep(10);

        SocketChannel sc = (SocketChannel) client.channel();
        sc.read(Buffer);
        return new String(Buffer.array(), Charset.forName("UTF-8"));
    }

}

