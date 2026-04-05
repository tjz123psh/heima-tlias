package org.god.ibatis.utils.implMethod.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


public class UnPoolDataSource implements javax.sql.DataSource {
    /**
     * 注册驱动和使得连接数据库
     *
     * @return
     * @throws SQLException
     */

    private String userName;
    private String password;
    private String url;

    public UnPoolDataSource(String driver, String url, String userName, String password) {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
