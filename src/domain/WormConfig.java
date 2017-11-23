package domain;

import persistence.contracts.PoolConnections;

public class WormConfig {
    private static WormConfig wormConfig;
    private PoolConnections poolConnections;
    private final String dbName;

    public WormConfig(String dbName, PoolConnections poolConnections){
        this.poolConnections = poolConnections;
        this.dbName = dbName;
        wormConfig = this;
    };

    public WormConfig(String dbName) {
        this.dbName = dbName;
    }

    public static WormConfig newInstance() {
        return wormConfig;
    };

    public PoolConnections getPoolConnections() {
        return poolConnections;
    }

    public String getDbName() {
        return dbName;
    }
}
