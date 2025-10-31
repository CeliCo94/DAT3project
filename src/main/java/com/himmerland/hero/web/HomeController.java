package com.himmerland.hero.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Forward til index.html under src/main/resources/static/
        return "forward:/index.html";
    }

    @GetMapping("/info")
    @org.springframework.web.bind.annotation.ResponseBody
    public String info() {
        // Forward til info.html under src/main/resources/static/
        return "Information";
    }

    // (Valgfri) behold test som tekst-endpoint ved at bruge @ResponseBody
    @GetMapping("/test")
    @org.springframework.web.bind.annotation.ResponseBody
    public String test() {
        return "This is a test endpoint.";
    }
}
