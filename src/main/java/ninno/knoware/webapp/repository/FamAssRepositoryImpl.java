package ninno.knoware.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ninno.knoware.webapp.domain.FamAssort;

@Repository
public class FamAssRepositoryImpl implements FamAssRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FamAssort> SelFamAssort() {

        String Sql = "SELECT A.* FROM FAMASSORT A ORDER BY ID;";

        List<FamAssort> famAssort = jdbcTemplate.query(Sql, new FamAssMapper());

        return famAssort;
    }

}
