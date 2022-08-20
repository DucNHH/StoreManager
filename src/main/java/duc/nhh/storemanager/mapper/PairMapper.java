package duc.nhh.storemanager.mapper;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PairMapper implements RowMapper<Pair<Integer, Integer>> {
    @Override
    public Pair<Integer, Integer> mapRow(ResultSet resultSet) {
        try {
            Integer key = resultSet.getInt(1);
            Integer value = resultSet.getInt(2);
            return new Pair<Integer, Integer>(key, value);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
