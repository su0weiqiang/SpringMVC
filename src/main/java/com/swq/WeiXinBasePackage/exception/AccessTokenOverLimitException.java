package com.swq.WeiXinBasePackage.exception;
/**
 * 
 * @description: 接口调用凭据access_token失效时抛出的异常

 */
public class AccessTokenOverLimitException extends WechatInterfaceFailedException {
	private static final long serialVersionUID = 968513399206621088L;
	public AccessTokenOverLimitException(){
		super();
	}
	public AccessTokenOverLimitException(String message){
		super(message);
	}
}