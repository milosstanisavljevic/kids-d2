package servent.handler.snapshot;

import app.AppConfig;
import app.snapshot_bitcake.SnapshotCollector;
import servent.handler.MessageHandler;
import servent.message.Message;
import servent.message.MessageType;

public class AVDoneHandler implements MessageHandler {

    private final Message clientMessage;
    private final SnapshotCollector snapshotCollector;

    public AVDoneHandler(Message clientMessage, SnapshotCollector snapshotCollector) {
        this.clientMessage = clientMessage;
        this.snapshotCollector = snapshotCollector;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.AV_DONE) {
            snapshotCollector.addDoneValues(clientMessage.getReceiverInfo().getId());
        } else {
            AppConfig.timestampedErrorPrint("Tell amount handler got: " + clientMessage);
        }
    }

}
