/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.common.exception;

/**
 * 参数为空异常
 *
 * @author ligw
 * @version $Id ParamNullException.java, v 0.1 2017-06-09 16:47 ligw Exp $$
 */
public class ParamNullException extends Exception {

    public ParamNullException(){
        super();
    }

    public ParamNullException(String msg){
        super(msg);
    }
}
