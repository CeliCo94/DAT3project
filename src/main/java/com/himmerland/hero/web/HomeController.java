package com.himmerland.hero.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //annotation that gives the class the role as a controller
public class HomeController {

    @GetMapping("/") //web starter dependency that makes it possible to map http requests to specific methods
    public String index() {
        // Forward til index.html under src/main/resources/static/
        return "forward:/index.html";
        
    }    
    
    @GetMapping("/regler") //web starter dependency that makes it possible to map http requests to specific methods
    public String rules() {
        // Forward til regler.html under src/main/resources/static/
        return "forward:/regler.html";
    }

    @GetMapping("/addRule")
    public String addRule() {
        // Forward til addRule.html under src/main/resources/static/
        return "forward:/addRule.html";
    }

    @GetMapping("/emails") //web starter dependency that makes it possible to map http requests to specific methods
    public String emails() {
        // Forward til emails.html under src/main/resources/static/
        return "forward:/emails.html";
    }

    @GetMapping("/afdelinger") //web starter dependency that makes it possible to map http requests to specific methods
    public String afdelinger() {
        // Forward til afdelinger.html under src/main/resources/static/
        return "forward:/afdelinger.html";
    }

    @GetMapping("/lejemaal") //web starter dependency that makes it possible to map http requests to specific methods
    public String lejemaal() {
        // Forward til lejemaal.html under src/main/resources/static/
        return "forward:/lejemaal.html";
    }

}
