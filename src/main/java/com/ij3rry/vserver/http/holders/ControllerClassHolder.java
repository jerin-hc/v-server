package com.ij3rry.vserver.http.holders;

import com.ij3rry.vserver.handlers.ConnectionHandler;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ControllerClassHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerClassHolder.class);

    // method : endpoint : class
    @Getter
    private static Map<String,Map<String,Object>> clazz = null;

    public static void loadAllControllerClass(Map<String,Object> configs){
        clazz = new HashMap<>();
        Map<String, Object> routes = (Map<String, Object>) configs.get("routes");

        for (Map.Entry<String, Object> methodEntry : routes.entrySet()) {
            String method = methodEntry.getKey();
            List<Map<String, Object>> endpoints = (List<Map<String, Object>>) methodEntry.getValue();

            Map<String, Object> endpointClassMap = new HashMap<>();
            for (Map<String, Object> endpoint : endpoints) {
                if (!endpoint.get("type").equals("controller")) {
                    continue;
                }
                try {
                    Class<?> clazz = Class.forName((String) endpoint.get("path"));
                    Object obj = clazz.getDeclaredConstructor().newInstance();
                    endpointClassMap.put((String) endpoint.get("endpoint"), obj);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException | IllegalAccessException e) {
                    LOGGER.warn("Unable to load controller class : ", e);
                }
            }
            if (!endpointClassMap.isEmpty()) {
                clazz.put(method, endpointClassMap);
            }
        }
        LOGGER.info("Controller classed are loaded {}", clazz);

    }
}
