package com.swq.WeiXinBasePackage.exception;
/**
 * 
 * @description: 接口调用凭据access_token超时抛出的异常

 */
public class AccessTokenTimeoutException extends WechatInterfaceFailedException {
	private static final long serialVersionUID = 968513399206621088L;
	public AccessTokenTimeoutException(){
		super();
	}
	public AccessTokenTimeoutException(String message){
		super(message);
	}
}