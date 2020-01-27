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


    public String loginM(String data){

        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = JSON.toJavaObject(jsonObject,User.class );

        //前端登录界面传进json
        //1、在redis中的hashmap查询k是否存在，且判断redis密码是否正确
        String str1=lookForInHashmap(user);
        switch (str1){
            //用户名密码正确
            case "Login_OK" :
                //把正确账号密码保存token
                TokenProvider.saveTokenToRedis(user);
                return "Login_OK";
            //密码错误
            case "Login":return "Login";
            //3、redis没有找到记录，需要查询数据库
            case "Login_False":
                String str2=lookForInMysql(user);
                switch (str2){
                    //数据库查询账号密码正确
                    case "Login_OK":
                        //把正确账号密码保存token
                        TokenProvider.saveTokenToRedis(user);
                        return "Login_OK";
                    //数据库查询密码错误
                    case "Login":return "Login";
                    //数据库没有查询到信息，显示注册
                    case "Register":return "Register";
                    default:return "Login";
                }
            default:return "Login";
        }

    }

    //1、在redis中的hashmap查询k是否存在，且判断redis密码是否正确
    public String lookForInHashmap(User user){
        if(jedis.hexists("phonenumber",user.getPhoneNumber())){
            //2、查询redis密码是否正确
            if(jedis.hget("phonenumber",user.getPhoneNumber()).equals(user.getPassword())){
                //redis中用户名密码正确，登录成功
                return "Login_OK";
            }else {
                //redis中密码不正确,则重新登录
                return "Login";
            }
        }else {
            //hashmap中没有找到用户名记录，下一步查询数据库。
            return "Login_False";
        }
    }


    //3、查询数据库，且判断数据库密码是否正确
    public String lookForInMysql(User user){
        LoginService loginService = new LoginService();
        User userMysql=loginService.selectUserByPhoneNumber(user.getPhoneNumber());
        if(userMysql!=null){
            if(userMysql.getPassword().equals(user.getPassword())){
                //数据库用户名密码正确
                return "Login_OK";
            }else {
                //数据库查询密码错误
                return "Login";
            }
        }else {
            //System.out.println("数据库中没有找到phonenumber，请先注册");
            //没有用户信息，需要注册
            return "Register";
        }
    }
}
