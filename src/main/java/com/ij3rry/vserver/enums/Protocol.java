package com.ij3rry.vserver.enums;

import com.ij3rry.vserver.exceptions.InvalidProtocolException;

public enum Protocol {
    HTTP_1_1("HTTP/1.1"),
    ;

    private final String name;

    Protocol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Protocol fromString(String name) {
        for (Protocol p : Protocol.values()) {
            if (p.name.equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new InvalidProtocolException("Unsupported protocol : " + name);
    }
}
