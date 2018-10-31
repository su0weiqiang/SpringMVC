package com.swq.WeiXinBasePackage.exception;
/**
 * 
 * @description: 当用户发来的信息，没有定义响应方式时抛出的异常

 */
public class NoMatchedMsgException extends Exception{
	private static final long serialVersionUID = -4196729256924044881L;
	
	public NoMatchedMsgException(){
		super();
	}
	
	public NoMatchedMsgException(String message){
		super(message);
	}
}