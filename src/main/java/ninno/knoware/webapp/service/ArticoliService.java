package ninno.knoware.webapp.service;

import java.util.List;
import ninno.knoware.webapp.domain.Articoli;

public interface ArticoliService {
    List<Articoli> SelArticoliByFilter(String Filtro);

    List<Articoli> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo);

    void InsArticolo(Articoli articolo);

    void DelArticolo(String CodArt);
}
