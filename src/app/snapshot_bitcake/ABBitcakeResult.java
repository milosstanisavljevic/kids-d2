package app.snapshot_bitcake;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ABBitcakeResult implements Serializable {
    private static final long serialVersionUID = 8939516333227254439L;
    private int serventId;
    private int recordedAmount;
    private Map<Integer,Integer> sentHistory;
    private Map<Integer,Integer> recordHistory;

    public ABBitcakeResult(int serventId, int recordedAmount,Map<Integer,Integer>sentHistory,Map<Integer,Integer> recordHistory){
        this.serventId = serventId;
        this.recordedAmount = recordedAmount;
        this.sentHistory =  new ConcurrentHashMap<>(sentHistory);
        this.recordHistory = new ConcurrentHashMap<>(recordHistory);
    }

    public int getServentId() {
        return serventId;
    }

    public int getRecordedAmount() {
        return recordedAmount;
    }

    public Map<Integer, Integer> getSentHistory() {
        return sentHistory;
    }

    public Map<Integer, Integer> getRecordHistory() {
        return recordHistory;
    }
}
