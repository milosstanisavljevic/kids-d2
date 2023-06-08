package servent.handler;

import app.AppConfig;
import app.CausalBroadcastShared;
import app.ServentInfo;
import app.snapshot_bitcake.SnapshotCollector;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.util.MessageUtil;

import java.util.Objects;
import java.util.Set;


/**
 * Handles the CAUSAL_BROADCAST message. Fairly simple, as we assume that we are
 * in a clique. We add the message to a pending queue, and let the check on the queue
 * take care of the rest.
 * @author bmilojkovic
 *
 */
public class CausalBroadcastHandler implements MessageHandler {

    private final Message clientMessage;
    private final Set<Message> receivedBroadcasts;
    private final SnapshotCollector snapshotCollector;



    public CausalBroadcastHandler(Message clientMessage, SnapshotCollector snapshotCollector, Set<Message> receivedBroadcasts) {
        this.clientMessage = clientMessage;
        this.snapshotCollector = snapshotCollector;
        this.receivedBroadcasts = receivedBroadcasts;

    }

    @Override
    public void run() {
        ServentInfo senderInfo = clientMessage.getOriginalSenderInfo();

        if (senderInfo.getId() == AppConfig.myServentInfo.getId()) {
            //We are the sender :o someone bounced this back to us. /ignore
            AppConfig.timestampedStandardPrint("Got own message back. No rebroadcast.");
        } else {
                boolean didPut = receivedBroadcasts.add(clientMessage);

                if (didPut) {
                    CausalBroadcastShared.addPendingMessage(clientMessage);
                    CausalBroadcastShared.checkPendingMessages();

                    if (!AppConfig.IS_CLIQUE) {
                        //New message for us. Rebroadcast it.
                        AppConfig.timestampedStandardPrint("Rebroadcasting... " + receivedBroadcasts.size());

                        for (Integer neighbor : AppConfig.myServentInfo.getNeighbors()) {
                            //Same message, different receiver, and add us to the route table.
                            MessageUtil.sendMessage(clientMessage.changeReceiver(neighbor).makeMeASender());
                        }
                    }
                } else {
                    //We already got this from somewhere else. /ignore
                    AppConfig.timestampedStandardPrint("Already had this. No rebroadcast.");
                }

        }
    }
}
