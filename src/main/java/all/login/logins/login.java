package all.login.logins;


import all.login.Entity.User;
import all.login.Service.LoginService;
import all.login.Service.SerializeObjectTool;
import all.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import java.util.Map;

public class login {
    private Jedis jedis;

    public login(){
        jedis = RedisUtil.getJedis();
        jedis.auth("123456");
    }
    public String login(String data){

        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = JSON.toJavaObject(jsonObject,User.class );

        //前端登录界面传进json
        //1、在redis中的hashmap查询k是否存在
        String str1=lookForInHashmap(user);
        if(str1.equals("Login_OK")){
            return str1;
        }

        //2、在redis中查询缓存是否存在
        String str2=lookForInKV(user);
        if(str2.equals("Login_OK")){
            return str2;
        }

        //3、查询数据库
        String str3=lookForInMysql(user);
        if(str3.equals("Login_OK")){
            return str3;
        }else if(str3.equals("Login")){
            //三步查询没有找到，数据库存在号码信息，但是密码出错，显示登录页面，重新登录；
            return "Login";
        }else {
            //三步查询没有找到，并且数据库不存在号码信息，显示注册页面
            return "Register";
        }

    }


    //1、在redis中的hashmap查询k是否存在
    public String lookForInHashmap(User user){
        byte[] byt = jedis.get("hashmap".getBytes());
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
                return "Login_False";
            }
        }catch (NullPointerException e){
            System.out.println("缓存中没有hashmap 空指针");
        }
        return "Login_False";
    }

    //2、在redis中查询缓存是否存在
    public String lookForInKV(User user){
        byte[] byt1 = jedis.get(("p"+user.getPhoneNumber()).getBytes());
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
        return "Login_False";
    }

    //3、查询数据库
    public String lookForInMysql(User user){
        LoginService loginService = new LoginService();
        User userMysql=loginService.selectUserByPhoneNumber(user.getPhoneNumber());
        if(userMysql!=null){
            if(userMysql.getPassword().equals(user.getPassword())){
                return "Login_OK";
            }else {
                return "Login";
            }
        }else {
            System.out.println("数据库中没有找到phonenumber，请先注册");
            return "Register";
        }
    }
}
