package servent.handler.snapshot;

import app.AppConfig;
import app.snapshot_bitcake.ABBitcakeResult;
import app.snapshot_bitcake.SnapshotCollector;
import servent.handler.MessageHandler;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.snapshot.ABTellMessage;

public class ABTellHandler implements MessageHandler {
    private Message clientMessage;
    private SnapshotCollector snapshotCollector;

    public ABTellHandler(Message clientMessage, SnapshotCollector snapshotCollector) {
        this.clientMessage = clientMessage;
        this.snapshotCollector = snapshotCollector;
    }


    @Override
    public void run() {
        try {
            if (clientMessage.getMessageType() == MessageType.AB_TELL) {
                int neighborAmount = Integer.parseInt(clientMessage.getMessageText());
                ABTellMessage tellAmountMessage = (ABTellMessage)clientMessage;

//                ABBitcakeResult abBitcakeResult = new ABBitcakeResult(
//                        clientMessage.getOriginalSenderInfo().getId(),
//                        neighborAmount,
//                        tellAmountMessage.getSentTransactions(),
//                        tellAmountMessage.getReceivedTransactions());
//
//                snapshotCollector.getCollectedAbValues().put("node " + clientMessage.getOriginalSenderInfo().getId(), abBitcakeResult);
            } else {
                AppConfig.timestampedErrorPrint("Tell amount handler got: " + clientMessage);
            }
        } catch (Exception e) {
            AppConfig.timestampedErrorPrint(e.getMessage());
        }
    }

}
