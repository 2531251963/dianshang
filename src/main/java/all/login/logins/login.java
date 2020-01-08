package all.login.logins;


import all.login.Entity.User;
import all.login.Service.LoginService;
import all.login.Service.SerializeObjectTool;
import all.util.JdbcUtil;
import all.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.util.Map;

public class login {
    private Jedis redis;

    public login(){
        redis = RedisUtil.getJedis();
        redis.auth("123456");
    }
    public String login(String data){

        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = JSON.toJavaObject(jsonObject,User.class );

        //前端登录界面传进json
        //1、在redis中的hashmap查询k是否存在
        byte[] byt = redis.get("hashmap".getBytes());
        try{
            Map<String, String> result = SerializeObjectTool.unserizlizeMap(byt);
            int count=0;
            if (result instanceof Map) {
                for (Map.Entry<String, String> entry : result.entrySet()) {
                    System.out.println("key= " + entry.getKey());
                    System.out.println("value= " + entry.getValue());
                    if(user.getPhoneNumber().equals(entry.getKey())){
                        count=1;
                        //在缓存hashmap中找到用户名密码==》登录到主页面
                        return "Login_OK";
                    }
                }
            }
            if(count==0){
                System.out.println("hashmap缓存中没有找到user.getphonrnumber");
            }
        }catch (NullPointerException e){
            System.out.println("缓存中没有hashmap 空指针");
        }

        //2、在redis中查询缓存是否存在
        byte[] byt1 = redis.get(("p"+user.getPhoneNumber()).getBytes());
        try {
            Object obj = SerializeObjectTool.unserizlize(byt1);
            String str = (String)obj;
            if(str instanceof String){
                System.out.println("key = "+user.getPhoneNumber());
                System.out.println("value = "+str);
                //在缓存中找到用户名密码==》登录到主页面
                return "Login_OK";
            }
        }catch (NullPointerException e){
            System.out.println("缓存中没有找到user.getphonrnumber");
        }

        //3、查询数据库
        LoginService loginService = new LoginService();
        User user1=loginService.selectUserByP(user.getPhoneNumber());
        if(user1!=null){
            return "Loin_OK";
        }else {
            System.out.println("数据库中没有找到phonenumber，请先注册");
            return "Login_FAILE";
        }
    }
}
