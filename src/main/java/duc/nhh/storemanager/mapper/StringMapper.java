package duc.nhh.storemanager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringMapper implements RowMapper<String>{
    @Override
    public String mapRow(ResultSet resultSet) {
        try {
            return resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
