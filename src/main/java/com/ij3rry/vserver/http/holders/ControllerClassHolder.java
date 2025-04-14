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
        Map<String,Object> routes = (Map<String, Object>) configs.get("routes");
        try {

            Iterator<Map.Entry<String, Object>> methodsEntrySet = routes.entrySet().iterator();
            while (methodsEntrySet.hasNext()){
                Map.Entry<String, Object> methodEntry = methodsEntrySet.next();
                String method = methodEntry.getKey();
                List<Map<String,Object>> endpoints =(List<Map<String,Object>>) methodEntry.getValue();

                Map<String,Object> endpointClassMap = new HashMap<>();
                for( Map<String,Object> endpoint : endpoints ){
                    if(!endpoint.get("type").equals("controller")){
                        continue;
                    }
                    Class<?> clazz = Class.forName((String) endpoint.get("path"));
                    Object obj = clazz.getDeclaredConstructor().newInstance();
                    endpointClassMap.put((String) endpoint.get("endpoint"), obj );
                }
                if(!endpointClassMap.isEmpty()) {
                    clazz.put(method, endpointClassMap);
                }
            }
            LOGGER.info("Controller classed are loaded {}",clazz);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Class not found");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
