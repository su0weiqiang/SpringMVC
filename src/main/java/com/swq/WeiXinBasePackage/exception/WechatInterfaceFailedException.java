package com.swq.WeiXinBasePackage.exception;
/**
 * 
 * @description: 接口调用凭据access_token超时抛出的异常

 */
public class WechatInterfaceFailedException extends Exception{
	private static final long serialVersionUID = 968513399206621088L;
	public WechatInterfaceFailedException(){
		super();
	}
	public WechatInterfaceFailedException(String message){
		super(message);
	}
}