package org.god.ibatis.utils.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.god.ibatis.utils.Resources;
import org.god.ibatis.utils.implMethod.Transaction;
import org.god.ibatis.utils.implMethod.impl.JdbcTransaction;
import org.god.ibatis.utils.implMethod.impl.UnPoolDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactoryBuilder(){}


    /**
     * 调用build的方法，返回 SqlSessionFactory 对象 ，
     * 现在要往里面塞东西
     * @param is
     * @return                   ``
     */
    public SqlSessionFactory build(InputStream is){
        SqlSessionFactory sqlSessionFactory = null;
       //解析xml文件
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultValue = environments.attributeValue("default");
            Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='"+defaultValue+"']");
            //获取标签元素
            Element transactionManagerElt = environment.element("transactionManager");
            Element dataSourceElt = environment.element("dataSource");

            //获取数据源
            DataSource dataSource =getDataSource(dataSourceElt);

            //获取事务管理器
            Transaction transaction = getTransaction(dataSource,transactionManagerElt);

            //现在进行MapperStatement的封装到 mapperStatementMap
            List<Node> mapperXMLPathListNode = document.selectNodes("//mapper");//获取每一个xml文件路径
            List<String> mapperXMLPathList = new ArrayList<>();
            mapperXMLPathListNode.forEach(elementNode -> {
                Element node = (Element) elementNode;
                String value = node.attributeValue("resource");
                mapperXMLPathList.add(value);
            });

            //调取方法处理 mapperXMLPathList
            Map<String, MapperStatement> mapperStatementMap =getMapperStatementMap(mapperXMLPathList);

            //获取mapperStatement放到sqlSessionFactory中
            sqlSessionFactory = new SqlSessionFactory(transaction,mapperStatementMap);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
         return sqlSessionFactory;
    }

    /**
     *
     * @param mapperXMLPathList 获取该路径下的sql封装（读取xml文件）
     * @return
     */
    private Map<String, MapperStatement> getMapperStatementMap(List<String> mapperXMLPathList) {
        Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

         //读取mapper.xml文件
        mapperXMLPathList.forEach(mapperXMLPath -> {
            try {
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(Resources.getResourceAsStream(mapperXMLPath));
                Element mapper = (Element) document.selectSingleNode("mapper");//这里直接写mapper就行，不用加/
                String namespace = mapper.attributeValue("namespace");
                List<Element> elementList = mapper.elements();
                elementList.forEach(element -> {
                    //组装sqlID：namespace+id
                    String id = element.attributeValue("id");
                    //封装
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getTextTrim();
                    MapperStatement mapperStatement = new MapperStatement(sql,resultType);
                    mapperStatementMap.put(namespace+"."+id,mapperStatement);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return mapperStatementMap;
    }

    /**
     * 获取事务管理器的方法
     * @param dataSource
     * @return
     */
    private Transaction getTransaction(DataSource dataSource,Element transactionManagerElt ) {
        Transaction transaction = null;
        String type = transactionManagerElt.attributeValue("type").trim().toUpperCase();
        if(type.equals(Const.TRANSACTION_JDBC)){
            transaction = new JdbcTransaction(dataSource,false);
        }
        return transaction;
    }


    /**
     * 获取数据源的方法
     * @return
     */
    private DataSource getDataSource(Element dataSourceElt) {
        //创建map集合装载property
        Map<String,String>  map = new HashMap<>();

        //从 dataSourceElt 中获取子标签去注册驱动连接数据库
        List<Element> elementList = dataSourceElt.elements("property");
        elementList.forEach(element -> {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            map.put(name,value);
        });

        //获取数据源
        DataSource dataSource = null;
        String type = dataSourceElt.attributeValue("type").trim().toUpperCase();
        if(type.equals(Const.UN_POOLED_DATASOURCE)){
            //创建接口的实现类，并使用构造方法
             dataSource = new UnPoolDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
        }
        /**
         * 还有其他类型的数据源
         */

        //返回数据源
        return dataSource;
    }

}
