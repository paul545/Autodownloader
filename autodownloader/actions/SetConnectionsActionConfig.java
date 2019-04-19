package org.jdownloader.extensions.autodownloader.actions;

public class SetConnectionsActionConfig implements IProfileActionConfig {
    public SetConnectionsActionConfig() {
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    private int connections = 0;
}
