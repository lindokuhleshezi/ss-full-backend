package za.ac.ss.helper;

import org.springframework.stereotype.Component;

@Component
public class GenerateReference {

	private static final String REF_PREFIX = "TCP/";
	private static final long twepoch = 1288834974657L;
    private static final long sequenceBits = 10;
    private static final long sequenceMax = 65536;
    private static volatile long lastTimestamp = -1L;
    private static volatile long sequence = 0L;
    
	public synchronized Long generateRandowUnqiue() {
		long timestamp = System.currentTimeMillis();
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        Long id = ((timestamp - twepoch) << sequenceBits) | sequence;
        return id;
	}

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
	public synchronized String generateReferenceNumber(Long id) {
        return REF_PREFIX + id;
	}
}
