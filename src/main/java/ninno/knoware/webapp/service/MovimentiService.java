package ninno.knoware.webapp.service;

import ninno.knoware.webapp.domain.Movimenti;
import java.util.List;

public interface MovimentiService {

    Movimenti SelMovimentiByCodArt(String CodArt);

    List<Movimenti> SelMovimentiByFilter(String Filtro);

    List<Movimenti> SelMovimentiByFilter(String Filtro, String OrderBy, String Tipo, String FilterMag);
}
