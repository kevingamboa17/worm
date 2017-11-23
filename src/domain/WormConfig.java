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
        wormConfig = this;
    }

    public static WormConfig newInstance() {
        if (wormConfig == null) {
            try {
                throw new NotInitializatedWormConfigException();
            } catch (NotInitializatedWormConfigException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return wormConfig;
    };

    public PoolConnections getPoolConnections() {
        return poolConnections;
    }

    public String getDbName() {
        return dbName;
    }
}

class NotInitializatedWormConfigException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NotInitializatedWormConfigException() {
        super("The WormConfig isn't initializated with the database name");
    }
}
