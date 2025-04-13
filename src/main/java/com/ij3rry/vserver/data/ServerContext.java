package com.ij3rry.vserver.data;

import com.ij3rry.vserver.enums.Protocol;
import lombok.Data;

import java.util.Map;

@Data
public abstract class ServerContext {
    protected Protocol protocol;
    protected Map<String, Object> serverConfig;
}
