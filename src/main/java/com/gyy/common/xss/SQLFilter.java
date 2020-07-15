package com.gyy.common.xss;

import com.gyy.common.exception.BusinessException;
import com.gyy.common.exception.code.BaseResponseCode;
import org.apache.commons.lang.StringUtils;

/**
 * SQL 过滤
 * @author GYY
 * @version 1.0
 * @date 2020/7/15 22:08
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.contains(keyword)){
                throw new BusinessException(BaseResponseCode.ILLEGAL_PARAMETER);
            }
        }

        return str;
    }
}
