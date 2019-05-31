package ru.arm.gm.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.arm.gm.domain.Detector;

import java.util.List;

@Repository
public class DetectorRepository {
    private final NamedParameterJdbcTemplate template;

    public DetectorRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Detector> getAll() {
        return template.query(
          "SELECT * FROM detector",
          new BeanPropertyRowMapper<>(Detector.class)
        );
    }
}
