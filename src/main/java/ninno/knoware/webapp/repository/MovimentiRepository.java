package ninno.knoware.webapp.repository;

import java.util.List;
import ninno.knoware.webapp.domain.Movimenti;

public interface MovimentiRepository {

    Movimenti SelMovimentiByCodArt(String CodArt);

    List<Movimenti> SelMovimentiByFilter(String Filtro);

    List<Movimenti> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo);
}
