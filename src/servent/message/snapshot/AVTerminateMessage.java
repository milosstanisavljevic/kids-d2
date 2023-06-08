package servent.message.snapshot;

import app.ServentInfo;
import servent.message.BasicMessage;
import servent.message.MessageType;
import java.util.Map;

public class AVTerminateMessage extends BasicMessage {

    private static final long serialVersionUID = 2618200693534225853L;

    public AVTerminateMessage(ServentInfo sender, ServentInfo receiver, ServentInfo neighbor, Map<Integer, Integer> senderVectorClock) {
        super(MessageType.AV_TERMINATE, sender, receiver, neighbor, senderVectorClock);
    }

}
