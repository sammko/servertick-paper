package net.cavoj.servertick.paper;

import org.bukkit.Server;

import java.nio.ByteBuffer;

public class Metrics {
    private final long[] samples = new long[240];
    private int startIndex;
    private int sampleCount;
    private int writeIndex;

    private long lastSample;

    public void pushSample(long time) {
        this.lastSample = time;
        this.samples[this.writeIndex] = time;
        ++this.writeIndex;
        if (this.writeIndex == samples.length) {
            this.writeIndex = 0;
        }

        if (this.sampleCount < samples.length) {
            this.startIndex = 0;
            ++this.sampleCount;
        } else {
            this.startIndex = (this.writeIndex+1)%samples.length;
        }
    }

    public long getLastSample() {
        return this.lastSample;
    }

    public void update(Server server) {
        long[] tickTimes = server.getTickTimes();
        int i = (server.getCurrentTick()+99) % 100;
        this.pushSample(tickTimes[i]);
    }

    public byte[] serialize() {
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES*3+Long.BYTES*samples.length);
        bb.putInt(this.writeIndex);
        bb.putInt(this.sampleCount);
        bb.putInt(this.startIndex);
        for (long l : this.samples) {
            bb.putLong(l);
        }
        return bb.array();
    }
}
