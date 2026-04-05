package org.god.ibatis.utils.core;

import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


@AllArgsConstructor
public class Session {
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 会话期间，执行SQL代码
     * 这里执行查询语句
     */
    public Object selectOne(String sqlId, Object param) throws Exception {
        Object obj = null;
        //获取连接对象
        Connection conn = sqlSessionFactory.getTransaction().getConnection();
        //获取sql语句
        String sql = sqlSessionFactory.getMapperStatement().get(sqlId).getSql();
        //获取ResultType
        String resultType = sqlSessionFactory.getMapperStatement().get(sqlId).getResultType();
        //预编译sql
        String replacedSql = sql.replaceAll("#\\{[\\w$]*\\}", "?");
        PreparedStatement preparedStatement = conn.prepareStatement(replacedSql);
        //给sql赋值
        preparedStatement.setString(1, param.toString());
        //返回结果集
        ResultSet rs = preparedStatement.executeQuery();
        //对结果集进行处理.
        if (rs.next()) {
            //获取全类名,并返回resultType的class
            Class<?> resultTypeClass = Class.forName(resultType);
            //获取构造方法: Object obj = new User();
            obj = resultTypeClass.newInstance();
            //给他初始化值,现在获取字段
            ResultSetMetaData metaData = rs.getMetaData();
            //获取字段数量
            int columnCount = metaData.getColumnCount();
            //现在开始for循环初始化
            for (int i = 1; i <= columnCount; i++) {
                //获取字段名
                String columnName = metaData.getColumnName(i);
                //获取set方法名字
                String setMethodName = "set" + columnName.toUpperCase().charAt(0) + columnName.substring(1);
                //通过反射机制获取方法
                Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName, resultTypeClass.getDeclaredField(columnName).getType());
                setMethod.invoke(obj,rs.getString(columnName));
            }
        }
        return obj;
    }

    //插入语句
    public int insert(String sqlId, Object pojo) throws Exception {
        //获取连接对象
        Connection conn = sqlSessionFactory.getTransaction().getConnection();
        //获取sql语句
        String sql = sqlSessionFactory.getMapperStatement().get(sqlId).getSql();
        //替换sql语句中的占位符为?
        String replacedSql = sql.replaceAll("#\\{[\\w$]*\\}", "?");
        //预编译sql
        PreparedStatement preparedStatement = conn.prepareStatement(replacedSql);
        //现在要将？赋值
        int fromIndex = 0;
        int index = 1;
        while (true) {
            int jingHaoIndex = sql.indexOf("#", fromIndex);
            //当执行完到没有#就退出
            if (jingHaoIndex == -1) {
                break;
            }
            int kuoHaoIndex = sql.indexOf("}", jingHaoIndex);
            String propertyName = sql.substring(jingHaoIndex + 2, kuoHaoIndex);
            //让下一个循环从当前}后面继续找
            fromIndex = kuoHaoIndex + 1;
            //接下来要获取propertyName的类型，通过反射机制获取的这个pojo中的值
            //构造get的方法
            String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
            Method declaredMethod = pojo.getClass().getDeclaredMethod(getMethodName);
            Object propertyValue = declaredMethod.invoke(pojo);
            preparedStatement.setString(index, propertyValue.toString());
            index++;
        }

        int count = preparedStatement.executeUpdate();
        return count;

    }


    /**
     * 所有的操作基本都是在session上执行，那些方法最后要在这里调用
     */
    public void commit() {
        sqlSessionFactory.getTransaction().commit();
    }

    public void rollback() {
        sqlSessionFactory.getTransaction().rollback();
    }

    public void close() {
        sqlSessionFactory.getTransaction().commit();
    }
}
