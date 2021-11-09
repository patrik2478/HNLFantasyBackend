package hr.fantasy.hnl.Repository;

import hr.fantasy.hnl.Domain.Player;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class PlayerRepositoryImpl implements  PlayerRepository{
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert playerInserter;

    public PlayerRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.playerInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("player").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Player> getAll() {
        return jdbc.query("select first_name, last_name, age, player_position from player",
                this::mapRowToPlayer);
    }

    @Override
    public Optional<Player> findPlayerByName(String firstName) {
        return Optional.ofNullable(jdbc.queryForObject("select first_name, last_name, age, player_position from player where first_name=?",
                this::mapRowToPlayer, firstName));
    }

    private Player mapRowToPlayer(ResultSet rs, int rowNum) throws SQLException {
        Player player = new Player();
        player.setFirstName(rs.getString("first_name"));
        player.setLastName(rs.getString("last_name"));
        player.setAge(rs.getInt("age"));
        player.setPosition(rs.getString("player_position"));

        return player;
    }
}
