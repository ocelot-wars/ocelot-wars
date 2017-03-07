package com.github.ocelotwars.engine;

import java.util.Objects;

public class Player {

    private String name;
    private String host;
    private int port;

    public Player(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int hashCode() {
        return 13 
            + ((host == null) ? 0 : host.hashCode() * 17) 
            + port * 31
            + ((name == null) ? 0 :name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Player that = (Player) obj;
        return Objects.equals(this.name, that.name)
            && Objects.equals(this.host, that.host)
            && this.port == that.port;
    }

}
