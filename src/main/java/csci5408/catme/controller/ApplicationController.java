package csci5408.catme.controller;

import csci5408.catme.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Controller
public class ApplicationController {

    private final AuthenticationService authenticationService;

    public ApplicationController(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String greetings() {
        if(authenticationService.isAuthenticated()) {
            if(authenticationService.getLoggedInUser().getAdmin()) {
                // @TODO: Goto ADMIN PAGE
            }
            return "redirect:courses";
        }
        return "redirect:login";

    }
    
}
