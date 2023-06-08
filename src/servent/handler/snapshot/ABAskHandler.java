package servent.handler.snapshot;

import app.AppConfig;
import app.CausalBroadcastShared;
import app.snapshot_bitcake.SnapshotCollector;
import servent.handler.MessageHandler;
import servent.message.Message;
import servent.message.MessageType;

public class ABMarkerHandler implements MessageHandler {

    private Message clMessage;
    private SnapshotCollector snapshotCollector;

    public ABMarkerHandler(Message clientMessage,SnapshotCollector snapshotCollector){
        this.clMessage = clientMessage;
        this.snapshotCollector = snapshotCollector;
    }
    @Override
    public void run() {
        if(clMessage.getMessageType() == MessageType.AB_MARKER){
            if (clMessage.getOriginalSenderInfo().getId() == AppConfig.myServentInfo.getId()){
                AppConfig.timestampedStandardPrint("Got own message back. No rebroadcast.");
            }else{
                //boolean didPut = CausalBroadcastShared.
            }
        }

    }

}
