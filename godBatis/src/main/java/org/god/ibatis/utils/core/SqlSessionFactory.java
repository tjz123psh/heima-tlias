package org.god.ibatis.utils.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.god.ibatis.utils.implMethod.Transaction;

import java.sql.Connection;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlSessionFactory {

    /**
     * 事务管理器
     * 事务管理器是有多个选项的，此时可以面向接口编程
     * 灵活切换类型
     */
    private Transaction transaction;

    /**
     * 数据源
     * 这里的数据源可以从事务管理器中获取（因为其中定义了connection方法）
     */

    /**
     * mapper
     */
    private Map<String,MapperStatement> mapperStatement;


    /**
     *开启数据库的连接，同时创建会话对象，在会话对象中执行SQL语句
     * @return
     */
    public Session openSession(){
        transaction.openConnection();
        Session session = new Session(this);
        return session;
    }
    
}
