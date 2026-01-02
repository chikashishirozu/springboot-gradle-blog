// DebugController.java
package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DebugController {
    
    @Autowired
    @Qualifier("requestMappingHandlerMapping")    
    private RequestMappingHandlerMapping handlerMapping;
    
    @GetMapping("/debug/mappings")
    public List<String> getMappings() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
            .map(entry -> entry.getKey() + " -> " + entry.getValue())
            .collect(Collectors.toList());
    }
}
