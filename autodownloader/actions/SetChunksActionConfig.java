package org.jdownloader.extensions.autodownloader.actions;

public class SetChunksActionConfig implements IProfileActionConfig {
    public SetChunksActionConfig() {
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    private int chunks = 0;
}
