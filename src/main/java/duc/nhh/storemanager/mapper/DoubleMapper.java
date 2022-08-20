package duc.nhh.storemanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleMapper implements RowMapper<Double>{
    @Override
    public Double mapRow(ResultSet resultSet) {
        try {
            return resultSet.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
