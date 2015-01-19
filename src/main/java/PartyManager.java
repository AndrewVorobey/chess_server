import java.util.HashMap;

/**
 * Created by andrew on 19.01.15.
 */
public class PartyManager {
    static PartyManager instance = new PartyManager();
    HashMap<String, Party> partyes = new HashMap<String, Party>();
    boolean wasCrashed = false;

    public void connectToParty(Player player, String partyName) {
        get(partyName).connectSecondPlayer(player);
    }

    public void createPartyAndConnect(Player player, boolean isWight, String partyName) {
        GetOrMakePatry(partyName).connectPlayer(player, isWight);
    }

    public Party get(String partyName) {
        return partyes.get(partyName);
    }

    public Party GetOrMakePatry(String partyName) {
        makeSureExistPary(partyName);
        return partyes.get(partyName);
    }

    private void makeSureExistPary(String partyName) {
        if (partyes.get(partyName) == null)
            partyes.put(partyName, new Party(partyName, wasCrashed));
    }
}
