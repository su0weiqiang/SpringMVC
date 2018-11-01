package wechat;

import java.io.IOException;

import com.swq.WeiXinBasePackage.util.AccessTokenUtil;
import com.swq.WeiXinBasePackage.util.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wechat.util.WeChatMenuUtil;

/**
 *
 * 采用测试的方法创建菜单
 *
 * @description: 调用微信接口类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class WechatInterface {

    /**
     * @throws IOException
     * @description: 生成菜单
     * @date: 2015-8-12 下午2:03:04 @author： Xu Yajie
     */
    @Test
    public void createMenu() throws Exception {
        String token = AccessTokenUtil.getAccessToken();
        System.out.println(token);
        System.out.println(WeChatMenuUtil.createMenu(token));
    }

//    @Test
//    public void queryMenu() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + token;
//        String result = HttpUtil.httpGet(url);
//        System.out.println("======查询菜单========");
//        System.out.println(result);
//        System.out.println("======查询菜单========");
//    }
//
//    @Test
//    public void deleteMenu() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token;
//        String result = HttpUtil.httpGet(url);
//        System.out.println("======删除全部菜单========");
//        System.out.println(result);
//        System.out.println("======删除全部菜单========");
//    }
//
//    /**
//     * @throws IOException
//     * @description: 获取微信服务器IP
//     * @date: 2015-8-12 下午2:03:35 @author： Xu Yajie
//     */
//    @Test
//    public void getCallBackIP() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + token;
//        String result = HttpUtil.httpGet(url);
//        JSONObject json = JSONObject.parseObject(result);
//        JSONArray arr = json.getJSONArray("ip_list");
//        for (int i = 0; i < arr.size(); i++) {
//            System.out.println(arr.getString(i));
//        }
//    }
//
//    @Test
//    public void getUser() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=opZbDjsIo443a_zyNDH6MNVj0tjQ";
//        String result = HttpUtil.httpGet(url);
//        System.out.println(result);
//    }
//
//    @Test
//    public void creatTag() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=" + token;
//        String content = "{\"tag\" : {\"name\" : \"社区工作人员\"}}";
//        String result = HttpUtil.httpPost(url, content);
//        System.out.println("=====创建标签=======");
//        System.out.println(result);
//        System.out.println("=================");
//    }
//
//    @Test
//    public void queryTag() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + token;
//        String result = HttpUtil.httpPost(url, "");
//        System.out.println("=====查询标签=======");
//        System.out.println(result);
//        System.out.println("=================");
//    }
//
//    @Test
//    public void addOpenIdTag() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=" + token;
//        String content = "{\"openid_list\" : [\"opZbDjsIo443a_zyNDH6MNVj0tjQ\"],\"tagid\" : 100}";
//        String result = HttpUtil.httpPost(url, content);
//        System.out.println("=====标签新增用户=======");
//        System.out.println(result);
//        System.out.println("====================");
//    }
//
//    @Test
//    public void deleteUserMenu() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=" + token;
//        String content = "{\"menuid\":405415541}";
//        String result = HttpUtil.httpPost(url, content);
//        System.out.println("=====删除个性化菜单=======");
//        System.out.println(result);
//        System.out.println("=====================");
//    }
//
//    @Test
//    public void queryUserMenu() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=" + token;
//        String content = "{\"user_id\":\"opZbDjsIo443a_zyNDH6MNVj0tjQ\"}";
//        String result = HttpUtil.httpPost(url, content);
//        System.out.println("=====查询用户的个性化菜单=======");
//        System.out.println(result);
//        System.out.println("=========================");
//    }
//
//    @Test
//    public void creatUserMenu() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        WeChatMenuUtil.createMenu2(token);
//    }
//
//    // {"menuid":419388656}
//    // {"tag":{"id":100,"name":"社区工作人员"}}
//
//    @Test
//    public void ceratQR() throws Exception {
//        String token = AccessTokenUtil.getAccessToken();
//        System.out.println(token);
//        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
//        String content = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 3344}}}";
//        String result = HttpUtil.httpPost(url, content);
//        System.out.println("=====生成永久二维码=======");
//        System.out.println(result);
//        System.out.println("=========================");
//    }
    // 测试
    // {"ticket":"gQHs8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyVHRiX0VqQm1iM2UxMDAwMHcwM1IAAgTSR85YAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/02Ttb_EjBmb3e10000w03R"}
    // 生产
    // {"ticket":"gQFE8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyOHRxeUpYc2k4c1QxMDAwME0wM0YAAgR9SM5YAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/028tqyJXsi8sT10000M03F"}

}