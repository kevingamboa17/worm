package domain;

import persistence.contracts.PoolConnections;

/**
 * The Worm Config class have all the necessary data to request a connection to server,
 * have to possibility to configure a manager of connection (pool of connection).
 */
public class WormConfig {
    /** WormConfig object that will the only one in all the application */
    private static WormConfig wormConfig;

    /** Pool of connection what manages the connections that use the application */
    private PoolConnections poolConnections;

    /** Name of the database that is request to the server */
    private final String dbName;

    /** Name of the host that is request to the server */
    private final String host;

    /** Number of port where is run the server */
    private final String port;

    /** Username that exist in the server */
    private final String user;

    /** Password of the user requested */
    private final String password;

    /**
     * Initializes a newly created {@code WormConfig} object with all the params required
     * @param dbName name of the database that is request to the server
     * @param host name of the host that is request to the server
     * @param port number of port where is run the server
     * @param user username that exist in the server
     * @param password password of the user requested
     * @param poolConnections a pool of connection what manages the connections that use the application
     */
    public WormConfig(String dbName, String host, String port, String user, String password, PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
        this.dbName = dbName;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        wormConfig = this;
    }

    /**
     * Initializes a newly created {@code WormConfig} object with all the params required
     * @param dbName name of the database that is request to the server
     * @param host name of the host that is request to the server
     * @param port number of port where is run the server
     * @param user username that exist in the server
     * @param password password of the user requested
     */
    public WormConfig(String dbName, String host, String port, String user, String password) {
        this.dbName = dbName;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        wormConfig = this;
    }

    /**
     * Method that return the only one instance of {@code WormConfig} that have all the application
     * @return a WormConfig object
     */
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

    /**
     * Method that return the poolConnection configured
     * @return the pool of connections configured
     */
    public PoolConnections getPoolConnections() {
        return poolConnections;
    }

    /**
     * Method that return the name of the database configured
     * @return the name of the database configured
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Method that return the name of the host configured
     * @return the name of the host configured
     */
    public String getHost() {
        return host;
    }

    /**
     * Method that return the number of port configured
     * @return the number of port configured
     */
    public String getPort() {
        return port;
    }

    /**
     * Method that return the username configured
     * @return return the username configured
     */
    public String getUser() {
        return user;
    }

    /**
     * Method that return the password of the user configured
     * @return the password of the user configured
     */
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
