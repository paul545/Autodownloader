package org.jdownloader.extensions.autodownloader.model;

import org.jdownloader.extensions.autodownloader.actions.AbstractProfileAction;
import org.jdownloader.extensions.autodownloader.actions.IProfileActionConfig;
import org.jdownloader.extensions.autodownloader.helpers.ActionHelper;

public class ProfileEntry {
    private final ProfileEntryStorable                        storableEntry;
    private final AbstractProfileAction<IProfileActionConfig> action;

    public ProfileEntry(ProfileEntryStorable storableEntry) throws Exception {
        this.storableEntry = storableEntry;
        this.action = ActionHelper.newActionInstance(storableEntry);
    }

    public ProfileEntryStorable getStorable() {
        return this.storableEntry;
    }

    public void setEnabled(boolean enabled) {
        storableEntry.setEnabled(enabled);
    }

    public String getName() {
        return storableEntry.getName();
    }

    public void setName(String name) {
        storableEntry.setName(name);
    }

    public int getMaxNumDownloads() {
        return storableEntry.getMaxNumDownloads();
    }

    public int getMaxNumChunks() {
        return storableEntry.getMaxNumChunks();
    }

    public int getMinNumDownloads() {
        return storableEntry.getMinNumDownloads();
    }

    public int getMinNumChunks() {
        return storableEntry.getMinNumChunks();
    }

    public AbstractProfileAction getAction() {
        return this.action;
    }

    public long getID() {
        return storableEntry.getID();
    }

    public boolean isEnabled() {
        return storableEntry.isEnabled();
    }

    public int getMinBandwidth() {
        return storableEntry.getMinBandwidth();
    }

    public void setMinBandwidth(int speed) {
        storableEntry.setMinBandwidth(speed);
    }

    public int getMaxBandwidth() {
        return storableEntry.getMaxBandwidth();
    }

    public void setMaxBandwidth(int speed) {
        storableEntry.setMaxBandwidth(speed);
    }
}
