package ninno.knoware.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping(value="index")
    public String getWelcome(Model model) {

        model.addAttribute("intestazione", "Benvenuti nel sito Alpha Shop");
        model.addAttribute("saluti", "Seleziona i prodotti da acquistare");

        return "index";
    }

    @RequestMapping(value="/")
    public String getWelcome2(Model model) {

        model.addAttribute("intestazione", "Benvenuti nel sito Alpha Shop");
        model.addAttribute("saluti", "Seleziona i prodotti da acquistare");

        return "index";
    }
}
