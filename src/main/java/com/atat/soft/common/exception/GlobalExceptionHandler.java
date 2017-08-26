package com.atat.soft.common.exception;


import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.common.bean.ResultCode;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DELL on 2017/6/6.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(value = {MethodArgumentNotValidException.class,DataAccessException.class})
    @ResponseBody
    public JsonResult MethodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) throws Exception {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(ResultCode.DB_WRONG.getCode());
        jsonResult.setErrorMsg(ResultMessage.METHODARGUMENTNOTVALI+e.getMessage());
        return jsonResult;
    }*/

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public JsonResult DataAccessExceptionErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(ResultCode.DB_ERROR.getCode());
        e.printStackTrace();
        if (e instanceof ParamNullException){
            jsonResult.setCode(ResultCode.PARAM_EMPTY_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.PARAM_EMPTY_ERROR.getInfo());
        } else if(e instanceof DataAccessException){
            jsonResult.setErrorMsg(ResultCode.DB_ERROR.getInfo()+e.getMessage());
        }else if(e instanceof  MethodArgumentNotValidException){
            jsonResult.setErrorMsg(ResultCode.METHODARGUMENTNOTVALI.getInfo()+e.getMessage());
    }
        return jsonResult;
    }


}
