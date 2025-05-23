package app.snapshot_bitcake;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import app.AppConfig;
import app.CausalBroadcastShared;
import servent.message.Message;
import servent.message.util.MessageUtil;

import static app.snapshot_bitcake.SnapshotType.AB;

/**
 * Main snapshot collector class. Has support for Naive, Chandy-Lamport
 * and Lai-Yang snapshot algorithms.
 * 
 * @author bmilojkovic
 *
 */
public class SnapshotCollectorWorker implements SnapshotCollector {

	private volatile boolean working = true;
	
	private AtomicBoolean collecting = new AtomicBoolean(false);
	
	private List<Integer> collectedDoneValues = new CopyOnWriteArrayList<>();
	private Map<String,Integer> collectedNaiveValues = new ConcurrentHashMap<>();
	
	private SnapshotType snapshotType;
	private Map<Integer,ABBitcakeResult> collectedABValues = new ConcurrentHashMap<>();
	private BitcakeManager bitcakeManager;

	public SnapshotCollectorWorker(SnapshotType snapshotType) {
		this.snapshotType = snapshotType;
		
		switch(snapshotType) {

			case AB:
				bitcakeManager = new ABBitcakeManager();

			case AV:
				bitcakeManager = new AVBitcakeManager();

			case NONE:
				AppConfig.timestampedErrorPrint("Making snapshot collector without specifying type. Exiting...");
				System.exit(0);
		}
	}
	
	@Override
	public BitcakeManager getBitcakeManager() {
		return bitcakeManager;
	}
	
	@Override
	public void run() {
		while(working) {
			
			/*
			 * Not collecting yet - just sleep until we start actual work, or finish
			 */
			while (collecting.get() == false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (working == false) {
					return;
				}
			}
			
			/*
			 * Collecting is done in three stages:
			 * 1. Send messages asking for values
			 * 2. Wait for all the responses
			 * 3. Print result
			 */
			Map<Integer,Integer> vectorClock;
			Message askMsg;
			//1 send asks
			switch (snapshotType) {
				case AB:
					vectorClock = new ConcurrentHashMap<>(CausalBroadcastShared.getVectorClock());
					//askMsg = new Ab
					break;// ((ABBitcakeManager)bitcakeManager);
				case NONE:
				//Shouldn't be able to come here. See constructor. 
					break;
			}
			
			//2 wait for responses or finish
			boolean waiting = true;
			while (waiting) {
				switch (snapshotType) {

				case NONE:
					//Shouldn't be able to come here. See constructor. 
					break;
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if (working == false) {
					return;
				}
			}
			
			//print
			int sum;
			switch (snapshotType) {
			case NAIVE:
				sum = 0;
				for (Entry<String, Integer> itemAmount : collectedNaiveValues.entrySet()) {
					sum += itemAmount.getValue();
					AppConfig.timestampedStandardPrint(
							"Info for " + itemAmount.getKey() + " = " + itemAmount.getValue() + " bitcake");
				}
				
				AppConfig.timestampedStandardPrint("System bitcake count: " + sum);
				
				collectedNaiveValues.clear(); //reset for next invocation
				break;





			case NONE:
				//Shouldn't be able to come here. See constructor. 
				break;
			}
			collecting.set(false);
		}

	}
	
	@Override
	public void addNaiveSnapshotInfo(String snapshotSubject, int amount) {
		collectedNaiveValues.put(snapshotSubject, amount);
	}
	
	@Override
	public void startCollecting() {
		boolean oldValue = this.collecting.getAndSet(true);
		
		if (oldValue == true) {
			AppConfig.timestampedErrorPrint("Tried to start collecting before finished with previous.");
		}
	}

	@Override
	public void addDoneValues(int id) {
		collectedDoneValues.add(id);
	}

	@Override
	public void clearCollectedDoneValues() {

	}

	@Override
	public Map<String, ABBitcakeResult> getCollectedAbValues() {
		return null;
	}

	@Override
	public void stop() {
		working = false;
	}

}
