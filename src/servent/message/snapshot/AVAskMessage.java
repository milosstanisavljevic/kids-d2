package servent.message.snapshot;

import app.ServentInfo;
import servent.message.BasicMessage;
import servent.message.MessageType;

import java.util.Map;

public class AVAskMessage extends BasicMessage {

    private static final long serialVersionUID = 656784329642914372L;

    public AVAskMessage(ServentInfo sender, ServentInfo receiver, ServentInfo neighbor, Map<Integer, Integer> senderVectorClock) {
        super(MessageType.AV_ASK, sender, receiver, neighbor, senderVectorClock);
    }


}
