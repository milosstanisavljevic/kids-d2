package servent.messeges;

import app.ServentInfo;

import java.util.Map;

/**
 * Has all the fancy stuff from {@link BasicMessage}, with an
 * added vector clock.
 *
 * Think about the repercussions of invoking <code>changeReceiver</code> or
 * <code>makeMeASender</code> on this without overriding it.
 * @author bmilojkovic
 *
 */
public class CausalBroadcastMessage extends BasicMessage{
    private static final long serialVersionUID = 7952273798396080816L;
    private Map<Integer, Integer> senderVectorClock;

    public CausalBroadcastMessage(ServentInfo senderInfo, ServentInfo receiverInfo, String messageText,
                                  Map<Integer, Integer> senderVectorClock) {
        super(MessageType.CAUSAL_BROADCAST, senderInfo, receiverInfo, messageText);

        this.senderVectorClock = senderVectorClock;
    }

    public Map<Integer, Integer> getSenderVectorClock() {
        return senderVectorClock;
    }
}
