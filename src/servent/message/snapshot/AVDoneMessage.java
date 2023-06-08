package servent.message.snapshot;

import app.ServentInfo;
import servent.message.BasicMessage;
import servent.message.MessageType;

import java.util.Map;

public class AVDoneMessage extends BasicMessage {

    private static final long serialVersionUID = 1531454750270616384L;
    public int initiatorId;

    public AVDoneMessage(ServentInfo sender, ServentInfo receiver, ServentInfo neighbor, Map<Integer, Integer> senderVectorClock, int initiatorId) {
        super(MessageType.AV_DONE, sender, receiver, neighbor, senderVectorClock);
        this.initiatorId = initiatorId;

    }


    public int getInitiatorId() {
        return initiatorId;
    }

}
