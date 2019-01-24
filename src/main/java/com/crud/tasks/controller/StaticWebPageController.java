package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class StaticWebPageController {

    @RequestMapping("/")
    public String index(Map<String, Object> model){
        model.put("variable", "My Thymeleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        return "index";
    }

    @GetMapping("/ac")
        public String index(Map<String, Object> model,
                            @RequestParam(name = "n1") int one, @RequestParam(name = "n2") int two){
        model.put("variable", "My Thymeleaf variable");
        model.put("one", one);
        model.put("two", two);
        return "index";
    }

}
