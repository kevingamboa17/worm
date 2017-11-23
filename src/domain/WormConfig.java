package domain;

import persistence.contracts.PoolConnections;

public class WormConfig {
    private static WormConfig wormConfig;
    private PoolConnections poolConnections;
    private final String dbName;
    private final String host;
    private final String port;
    private final String user;
    private final String password;

    public WormConfig(String dbName, String host, String port, String user, String password, PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
        this.dbName = dbName;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        wormConfig = this;
    }

    public WormConfig(String dbName, String host, String port, String user, String password) {
        this.dbName = dbName;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
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

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
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
