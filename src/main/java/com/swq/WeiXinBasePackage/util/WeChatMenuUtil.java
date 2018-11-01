package com.swq.WeiXinBasePackage.util;

import java.io.IOException;

/**
 * 自定义菜单帮助类
 * 
 * @author xuyajie
 */
public class WeChatMenuUtil {
	private static final String GET_MENU = "get";
	private static final String CREATE_MENU = "create";
	private static final String DELETE_MENU = "delete";
	private static final String CREATE_O_MENU = "addconditional";

	// 生成菜单操作url
	private static String menuURL(String type, String token) {
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append("https://api.weixin.qq.com/cgi-bin/menu/").append(type);
		urlBuffer.append("?access_token=").append(token);
		return urlBuffer.toString();
	}

	/**
	 * 建立菜单
	 * 
	 * @return
	 * @throws IOException
	 */
	public static boolean createMenu(String token) throws Exception {
		String menu = WeChatFileUtil.readMenu();
		if (menu == null) {
			return false;
		}
		// System.out.println(menu);
		String url = menuURL(CREATE_MENU, token);
		String result = HttpUtil.httpPost(url, menu);
		System.out.println(result);
		return WeChatErrCodeUtil.isOK(result);
	}

	/**
	 * 建立菜单2
	 * 
	 * @return
	 * @throws IOException
	 */
	public static void createMenu2(String token) throws Exception {
		String menu = WeChatFileUtil.readMenu();
		if (menu == null) {
			return;
		}
		// System.out.println(menu);
		String url = menuURL(CREATE_O_MENU, token);
		String result = HttpUtil.httpPost(url, menu);
		System.out.println(result);
	}

	/**
	 * 获取微信返回的菜单操作信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getWeChatMenu(String token) throws Exception {
		String url = menuURL(GET_MENU, token);
		return HttpUtil.httpGet(url);
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteMenu(String token) throws Exception {
		String url = menuURL(DELETE_MENU, token);
		String string = HttpUtil.httpGet(url);
		return WeChatErrCodeUtil.isOK(string);
	}
}
