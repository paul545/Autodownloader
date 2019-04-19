package org.jdownloader.extensions.autodownloader.model;

import org.appwork.storage.Storable;
import org.jdownloader.controlling.UniqueAlltimeID;

public class ProfileEntryStorable implements Storable {
    private boolean enabled         = true;
    private String  name            = "defaulty";
    // ID for action
    private String  actionID        = null;
    private String  actionConfig    = null;
    private long    id              = new UniqueAlltimeID().getID();
    // Download Parameters
    private int     minNumDownloads = 1;
    private int     maxNumDownloads = 1;
    private int     minNumChunks    = 1;
    private int     maxNumChunks    = 1;
    private int     minBandwidth    = 1;                            // kilobytes per second kbps
    private int     maxBandwidth    = 100;                          // kbps

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getActionId() {
        return actionID;
    }

    public void setActionId(String actionID) {
        this.actionID = actionID;
    }

    public String getActionConfig() {
        return actionConfig;
    }

    public void setActionConfig(String actionConfig) {
        this.actionConfig = actionConfig;
    }

    public int getMinNumDownloads() {
        return minNumDownloads;
    }

    public void setMinNumDownloads(int minNumDownloads) {
        this.minNumDownloads = minNumDownloads;
    }

    public int getMaxNumDownloads() {
        return maxNumDownloads;
    }

    public void setMaxNumDownloads(int maxNumDownloads) {
        this.maxNumDownloads = maxNumDownloads;
    }

    public int getMinNumChunks() {
        return minNumChunks;
    }

    public void setMinNumChunks(int minNumChunks) {
        this.minNumChunks = minNumChunks;
    }

    public int getMaxNumChunks() {
        return maxNumChunks;
    }

    public void setMaxNumChunks(int maxNumChunks) {
        this.maxNumChunks = maxNumChunks;
    }

    public ProfileEntryStorable(/* Storable */) {
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getMinBandwidth() {
        return this.minBandwidth;
    }

    public int getMaxBandwidth() {
        return this.maxBandwidth;
    }

    public void setMaxBandwidth(int kbps) {
        this.maxBandwidth = kbps;
    }

    public void setMinBandwidth(int kbps) {
        this.minBandwidth = kbps;
    }
}
