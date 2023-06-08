package app.snapshot_bitcake;

import app.Cancellable;

import java.util.Map;

/**
 * Describes a snapshot collector. Made not-so-flexibly for readability.
 * 
 * @author bmilojkovic
 *
 */
public interface SnapshotCollector extends Runnable, Cancellable {

	BitcakeManager getBitcakeManager();

	void addNaiveSnapshotInfo(String snapshotSubject, int amount);

	void startCollecting();
	void addDoneValues(int id);
	void clearCollectedDoneValues();

	Map<String, ABBitcakeResult> getCollectedAbValues();

}