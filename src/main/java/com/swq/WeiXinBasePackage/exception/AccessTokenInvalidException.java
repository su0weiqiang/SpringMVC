package com.swq.WeiXinBasePackage.exception;
/**
 * 
 * @description: 接口调用凭据access_token失效时抛出的异常

 */
public class AccessTokenInvalidException extends WechatInterfaceFailedException {
	private static final long serialVersionUID = 968513399206621088L;
	public AccessTokenInvalidException(){
		super();
	}
	public AccessTokenInvalidException(String message){
		super(message);
	}
}