package duc.nhh.storemanager.dao;

import duc.nhh.storemanager.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelDAO {
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/store_manager","root", "Duc@160520");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void setParameter(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                int index = i + 1;
                if (parameter instanceof Double) {
                    statement.setDouble(index, (Double) parameter);
                } else if (parameter instanceof String) {
                    statement.setString(index, (String) parameter);
                } else if (parameter instanceof Integer) {
                    statement.setInt(index, (Integer) parameter);
                } else if (parameter instanceof Date) {
                    statement.setTimestamp(index, new java.sql.Timestamp(((Date)parameter).getTime()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareCall(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int insert(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) id = resultSet.getInt(1);
            connection.commit();
            return id;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    public void delete(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareCall(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
