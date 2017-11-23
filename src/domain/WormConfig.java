package domain;

import persistence.contracts.PoolConnections;

public class WormConfig {
    private static WormConfig wormConfig;
    private PoolConnections poolConnections;

    public WormConfig(PoolConnections poolConnections){
        this.poolConnections = poolConnections;
        wormConfig = this;
    };

    private WormConfig(){

    }

    public static WormConfig newInstance() {
        if (wormConfig == null) {
            return new WormConfig();
        } else {
            return wormConfig;
        }
    };

    public PoolConnections getPoolConnections() {
        return poolConnections;
    }
}
