package datart.server.config;

import datart.security.exception.AuthException;
import datart.server.base.dto.ResponseData;
import datart.server.config.datasource.DynamicDataSource;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


@RestControllerAdvice
public class RestCtrlExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    //括号内可填写具体的异常，这样这个方法就只会捕捉这个异常

    /**
     * 除数不能为0
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseData<String> numberException(ArithmeticException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseData<String> runtimeException(RuntimeException e, HttpServletRequest request) {

        StringBuffer requestURL = request.getRequestURL();
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
        //返回信息可以自定义，例如自定义response结构来设置code和message
//        return requestURL+"地址出现错误，错误信息为"+e.getMessage();
    }

    /**
     * 未登录异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(AuthException.class)
    public ResponseData<String> authException(AuthException e, HttpServletRequest request){

        StringBuffer requestURL = request.getRequestURL();
        return ResponseData.failure(e.getMessage(),401);
    }

    /**
     * token校验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseData<String> jwtException(ExpiredJwtException e,HttpServletRequest request){

        StringBuffer requestURL = request.getRequestURL();
        return ResponseData.failure(requestURL + " token超时校验异常" ,401);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseData<String> nullPointerException(NullPointerException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public ResponseData<String> classCastException(ClassCastException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 文件未找到异常
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseData<String> fileNotFoundException(FileNotFoundException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 数字格式异常
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseData<String> numberFormatException(NumberFormatException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 安全异常
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseData<String> securityException(SecurityException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * sql异常
     */
    @ExceptionHandler(SQLException.class)
    public ResponseData<String> sqlException(SQLException e, HttpServletRequest request) {
        DynamicDataSource.clear();
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        log.error(e.getMessage());
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为SQL脚本异常");
    }

    /**
     * 类型不存在异常
     */
    @ExceptionHandler(TypeNotPresentException.class)
    public ResponseData<String> typeNotPresentException(TypeNotPresentException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public ResponseData<String> ioException(IOException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseData<String> noSuchMethodException(NoSuchMethodException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * sql语法错误异常
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseData<String> badSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        DynamicDataSource.clear();
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        log.error(e.getMessage());
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为sql语法错误异常");
    }

    /**
     * 无法注入bean异常
     */
    @ExceptionHandler(NoSuchBeanDefinitionException.class)
    public ResponseData<String> noSuchBeanDefinitionException(NoSuchBeanDefinitionException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * Http消息不可读异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseData<String> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 400错误
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseData<String> typeMismatchException(TypeMismatchException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 500错误
     */
    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseData<String> conversionNotSupportedException(ConversionNotSupportedException e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 栈溢出
     */
    @ExceptionHandler(StackOverflowError.class)
    public ResponseData<String> stackOverflowError(StackOverflowError e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }

    /**
     * 其他错误
     */
    @ExceptionHandler(Exception.class)
    public ResponseData<String> exception(Exception e, HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        return ResponseData.failure(requestURL + "地址出现错误，错误信息为" + e.getMessage());
    }
}
