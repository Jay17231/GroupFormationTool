package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Controller
public class ApplicationController {

    @GetMapping
    public String greetings() {
        return "index";
    }
}
