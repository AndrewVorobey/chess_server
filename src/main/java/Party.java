import logic.Field.Field;
import logic.Field.Figures.Figure;

import java.io.*;
import java.util.HashMap;

/**
 * Created by andrew on 01.01.15.
 */
public class Party extends Thread {
    Field field = new Field();

    Player wightPlayer;
    Player blackPlayer;
    final String name;


    public Party(String name, boolean afterCrash) {
        this.name = name;
        if (afterCrash) {
            readAll();// if failed, then the field will have default pos.
            writeAll();
        } else {
            writeAll();
        }
    }

    public boolean connectPlayer(Player player, boolean isWight) {
        if (isWight) {
            if (wightPlayer == null) {
                wightPlayer = player;
                return true;
            }
        } else {
            if (blackPlayer == null) {
                blackPlayer = player;
                return true;
            }
        }
        return false;
    }


    //returns isWhit
    public boolean connectSecondPlayer(Player player) {
        if (!(wightPlayer == null ^ blackPlayer == null)) {
            throw new RuntimeException("подключены уже оба игрока");
        }

        if (this.blackPlayer != null) {
            this.blackPlayer = player;
        } else {
            this.wightPlayer = player;
        }
        this.start();
        return player == wightPlayer;
    }

    private boolean tryToGo(String fromRow, String fromCol, String toRow, String toCol, Player player) {
        boolean gone = field.tryToGO(Integer.parseInt(fromRow),
                Integer.parseInt(fromCol),
                Integer.parseInt(toRow),
                Integer.parseInt(toCol),
                player == wightPlayer);
        try {
            if (gone) {
                Player other = (player == wightPlayer) ? blackPlayer : wightPlayer;
                other.sendMsg("GO|fr:" + fromRow + ".fc:" + fromCol + ".tr:" + toRow + ".tc:" + toCol);
                player.sendMsg("ok");
                writePos(Integer.parseInt(fromRow), Integer.parseInt(fromCol));
                writePos(Integer.parseInt(toRow), Integer.parseInt(toCol));
            } else {
                player.sendMsg("err");
            }
            return gone;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("can't connect to client");
        }
    }


    public void run() {
        sendFieldTo(wightPlayer);
        sendFieldTo(blackPlayer);

        while (true) {
            Go(wightPlayer);
            Go(blackPlayer);
        }
    }

    void Go(Player player) {
        try {
            processMsg(player.blockingReadMsg(), player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMsg(String msg, Player player) throws IOException {
        String[] splited = msg.split("|");
        String command = splited[0];
        splited = splited[1].split(".");
        HashMap<String, String> fields = new HashMap<String, String>();
        for (String s : splited) {
            String[] f = s.split(":");
            fields.put(f[0], f[1]);
        }
        processMsg(command, fields, player);
    }

    private void processMsg(String command, HashMap<String, String> descriptions, Player player) throws IOException {
        if (command == "GO") {
            tryToGo(descriptions.get("fr"), descriptions.get("fc"), descriptions.get("tr"), descriptions.get("tc"), player);
        }
    }

    private void sendFieldTo(Player player) {
        try {
            for (int row = 0; row < field.size; row++) {
                for (int col = 0; col < field.size; col++) {
                    player.sendMsg(String.format("row:%dcol:%dfigure%s", row, col, field.at(row, col).getFigure().toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //File:
    static String dir = "./Fields/";
    static final int len = 10;
    char[] buffer = new char[len];

    void writePos(int row, int col) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dir + name))) {
            writer.write(field.at(row, col).getFigure().toString(), len * (row * field.size + col), len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeAll() {
        for (int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                writePos(i, j);
            }
        }
    }

    void readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dir + name))) {
            for (int row = 0; row < field.size; row++) {
                for (int col = 0; col < field.size; col++) {
                    reader.read(buffer, len * (row * field.size + col), len);
                    field.at(row, col).setFigure(Figure.make(new String(buffer)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
