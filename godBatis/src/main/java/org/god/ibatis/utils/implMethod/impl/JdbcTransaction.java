package org.god.ibatis.utils.implMethod.impl;

import org.god.ibatis.utils.implMethod.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

    /**
     * 面向接口编程获取实现类（数据源）
     */
    private DataSource dataSource;

    private Boolean autoCommit;

    //获取初始化
    public JdbcTransaction(DataSource dataSource, Boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    /**
     * 为使得让下面三个方法属于同一个connection对象中，需要为起单独创建一个方法获连接对象；
     */
    private Connection connection;

    /**
     * 外界要获取connection对象取执行sql语句，对外提供get的方法
     */
    @Override
    public Connection getConnection() {
        return connection;
    }


    @Override
    public void openConnection() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
                ;
            }
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
