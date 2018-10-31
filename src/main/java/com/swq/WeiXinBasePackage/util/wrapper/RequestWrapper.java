package com.swq.WeiXinBasePackage.util.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private StringBuffer buffer;
	private AtomicInteger mark;
	private ServletInputStream is;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		buffer = new StringBuffer();
		mark = new AtomicInteger();
		is = new ServletInputStream() {
			@Override
			public int read() throws IOException {
				if(mark.intValue() < buffer.length()) {
					return buffer.charAt(mark.getAndIncrement());
				}
				return -1;
			}

		};
	}

	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return is;
	}
	/**
	 * 
	* @Title: write 
	* @Description: 将内容写入request
	* @param @param data    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void write(String data){
		buffer.append(data);
	}
	
	
	
}