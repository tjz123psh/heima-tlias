package org.god.ibatis.utils.implMethod;

import java.sql.Connection;

/**
 * 面向接口编程，实现接口中的方法
 */
public interface Transaction {

    void commit();


    void rollback();

    void close();

    void openConnection();


    Connection getConnection();

}
