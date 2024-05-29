package ninno.knoware.webapp.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import ninno.knoware.webapp.domain.Articoli;
import ninno.knoware.webapp.service.ArticoliService;

@Controller
@RequestMapping("/articoli")
public class ArticoliController {

    @Autowired
    private ArticoliService articoliService;

    private int NumArt = 0;
    private List<Articoli> recordset;

    @RequestMapping(value = "/cerca/{filter}", method = RequestMethod.GET)
    public String GetArticoliByFilter(@PathVariable("filter") String Filter, Model model) {
        recordset = articoliService.SelArticoliByFilter(Filter);

        if (recordset != null)
            NumArt = recordset.size();

        model.addAttribute("NumArt", NumArt);
        model.addAttribute("Titolo", "Ricerca Articoli");
        model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
        model.addAttribute("Articoli", recordset);

        return "articoli";
    }

    /*
     * Example: http://localhost:8080/AlphaShop/articoli/cerca?filter=barilla&rep=1
     */
    @RequestMapping(value = "/cerca", method = RequestMethod.GET)
    public String GetArticoliByFilter(@RequestParam("filter") String Filter, @RequestParam("rep") int IdRep,
            Model model) {

        List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter)
                .stream()
                .filter(u -> u.getIdFamAss() == IdRep).collect(Collectors.toList());

        if (recordset != null)
            NumArt = recordset.size();

        model.addAttribute("NumArt", NumArt);
        model.addAttribute("Titolo", "Ricerca Articoli");
        model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
        model.addAttribute("Articoli", recordset);

        return "articoli";
    }

    @RequestMapping(value = "/cerca/{filter}/{parametri}", method = RequestMethod.GET)
    public String GetArticoliByFilterMatrix(@PathVariable("filter") String Filter,
            @MatrixVariable(pathVar = "parametri") Map<String, List<String>> parametri, Model model) {

        int NumArt = 0;
        String orderBy = "codart";
        String tipo = "desc";
        Long SkipValue = (long) 0;
        Long LimitValue = (long) 10;

        List<String> IdRep = parametri.get("reparti");
        List<String> OrderBy = parametri.get("OrderBy");
        List<String> Paging = parametri.get("paging");

        if(orderBy != null){

        }
    }

}
