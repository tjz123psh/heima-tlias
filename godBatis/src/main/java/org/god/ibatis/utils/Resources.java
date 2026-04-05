package org.god.ibatis.utils;

import java.io.InputStream;

public class Resources {

    /**
     * @工具类
     * 私有化构造方法
     * @静态方法调取
     */
    private Resources(){
    }

    /**
     *加载类路径
     * @param resource
     * @return
     */
    public static InputStream getResourceAsStream(String resource){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);

    }
}
