package ninno.knoware.webapp.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

        if (OrderBy != null) {
            orderBy = OrderBy.get(0);
            tipo = OrderBy.get(1);
        }

        if (Paging != null) {
            SkipValue = Long.parseLong(Paging.get(0));
            LimitValue = Long.parseLong(Paging.get(1));
        }

        List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter, orderBy, tipo);

        recordset = recordset
                .stream()
                .filter(u -> IdRep.contains(Integer.toString(u.getIdFamAss())))
                .filter(u -> u.getQtaMag() > 0)
                .filter(u -> u.getPrezzo() > 0)
                .collect(Collectors.toList());

        if (recordset != null)
            NumArt = recordset.size();

        recordset = recordset
                .stream()
                .skip(SkipValue)
                .limit(LimitValue)
                .collect(Collectors.toList());

        model.addAttribute("Articoli", recordset);
        model.addAttribute("NumArt", NumArt);
        model.addAttribute("Titolo", "Ricerca Articoli");

        return "articoli";
    }

    // http://localhost:8080/AlphaShop/articoli/cerca/barilla/creati?daData=2010-10-31&aData=2015-10-31
    @RequestMapping(value = "/cerca/{filter}/creati", method = RequestMethod.GET)
    public String GetArticoliByFilterDate(@PathVariable("filter") String Filter,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("daData") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("aData") Date endDate,
            Model model) {

        List<Articoli> recordset = articoliService.SelArticoliByFilter(Filter)
                .stream()
                .filter(u -> u.getDataCreaz().after(startDate))
                .filter(U -> U.getDataCreaz().before(endDate))
                .collect(Collectors.toList());

        if (recordset != null)
            NumArt = recordset.size();

        model.addAttribute("NumArt", NumArt);
        model.addAttribute("Titolo", "Ricerca Articoli");
        model.addAttribute("Titolo2", "Risultati Ricerca " + Filter);
        model.addAttribute("Articoli", recordset);

        return "articoli";
    }

    @RequestMapping(value = "/infoart/{codart}", method = RequestMethod.GET)
    public String GetDettArticolo(@PathVariable("codart") String CodArt, Model model) {

        Articoli articolo = null;
        recordset = articoliService.SelArticoliByFilter(CodArt);

        if (recordset != null)
            articolo = recordset.get(0);

        model.addAttribute("Titolo", "Dettaglio Articolo");
        model.addAttribute("Titolo2", "Dati Articolo" + CodArt);
        model.addAttribute("articolo", articolo);

        return "infoArticolo";
    }

    @GetMapping(value = "/aggiungi")
    public String InsArticoli(Model model) {

        Articoli articolo = new Articoli();
        model.addAttribute("Titolo", "Inserimento Nuovo Articolo");
        model.addAttribute("newArticolo", articolo);
        return "insArticolo";
    }

}
