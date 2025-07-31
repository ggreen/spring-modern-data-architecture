

package spring.modern.data.jdbc.sql.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST API to execute SQL queries
 * @author gregory green
 */
@RestController
@RequestMapping("sql")
@RequiredArgsConstructor
public class ExecuteSqlController {

    private final JdbcTemplate jdbcTemplate;

    @PostMapping
    public List<Map<String, Object>> execute(@RequestBody String sql) {

        var input = sql.trim().toLowerCase();
        if(!input.startsWith("select"))
        {
            int update = jdbcTemplate.update(sql);
            return List.of(Map.of("update",update));
        }

        return jdbcTemplate.queryForList(sql);
    }
}
