package com.cafe24.mysite.dto;

public class JSONResult {
    private String result; // "success" or "fail"
    private String message; // reason message if result is fail
    private Object data; // data for transfer if result is success
    
    private JSONResult( String result, String message, Object data) {
	this.result = result;
	this.message = message;
	this.data = data;
    }
    
    public static JSONResult success(Object data) {
	return new JSONResult("success", null, data);
    }
    
    public static JSONResult fail(String message) {
	return new JSONResult("fail", message, null);
    }
    
    public String getResult() {
	return result;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage( String message ) {
	this.message = message;
    }

    public Object getData() {
	return data;
    }

    public void setData( Object data ) {
	this.data = data;
    }
}