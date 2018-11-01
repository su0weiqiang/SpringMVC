package wechat.util;

import com.swq.util.PropertyConfigUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * AccessToken文件以及menu文件处理
 * 
 * @author xuyajie
 * 
 */
public class WeChatFileUtil {
	private static Logger logger = LoggerFactory.getLogger(WeChatFileUtil.class);
	private static File menuFile = null;
	private static File menuFile2 = null;

	/**
	 * 初始化菜单信息存放文件
	 */
	private static void initMenuFile() {
		WeChatFileUtil ii = new WeChatFileUtil();
		menuFile = new File(ii.getfile(), "menu.json");
//		menuFile2 = new File(ii.getfile(), "menu2.json");
	}
	
	public String getfile(){
		return this.getClass().getResource("/").getPath();
	}

	/**
	 * 获取菜单内容
	 * 
	 * @return
	 */
	public static String readMenu() {
		if (menuFile == null) {
			initMenuFile();
		}
		try {
			InputStream inputStream = new FileInputStream(menuFile);
			String menuStr = IOUtils.toString(inputStream);
			// XXX 注意，此处与menu.json配合动态生成替换内容！
			menuStr = menuStr.replace("Property(base_url)", PropertyConfigUtil.getProperty("base-url"));
			return menuStr;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
//	public static String readMenu2() {
//		if (menuFile2 == null) {
//			initMenuFile();
//		}
//		try {
//			InputStream inputStream = new FileInputStream(menuFile2);
//			String menuStr = IOUtils.toString(inputStream);
//			// XXX 注意，此处与menu.json配合动态生成替换内容！
//			menuStr = menuStr.replace("Property(base_url)", PropertyConfigUtil.getProperty("base-url"));
//			return menuStr;
//		} catch (FileNotFoundException e) {
//			logger.error(e.getMessage(), e);
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
}
