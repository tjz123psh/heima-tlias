package org.god.ibatis.utils.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperStatement {
    /**
     * 用来存放mapper中的Map的value属性
     */

    private String sql;
    private String ResultType;


}
