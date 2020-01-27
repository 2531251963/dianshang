package all.login.logins;

import all.login.Entity.User;
import all.util.RedisUtil;
import all.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import netscape.security.UserTarget;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class TokenProvider {
    final private Jedis jedis ;

    public TokenProvider(){
        jedis = RedisUtil.getJedis();
        jedis.auth("123456");

    }

    public String getToken(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = JSON.toJavaObject(jsonObject,User.class );

        //1、判断redis是否存在token
        if(jedis.exists("token_"+user.getPhoneNumber())){
            //redis存在token，直接登录成功，并且更新token时间为三天
            jedis.set("token_"+user.getPhoneNumber(), TokenUtil.getToken(user.getPhoneNumber()), "NX", "EX", 259200);
            return "Login_OK";
        }else {
            //不存在token，表示登录过期，则重新输入用户名密码
            return "Login";
        }
    }

    public static void saveTokenToRedis(User user){
        Jedis jedis1 = RedisUtil.getJedis();
        jedis1.auth("123456");
        jedis1.set("token_"+user.getPhoneNumber(), TokenUtil.getToken(user.getPhoneNumber()), "NX", "EX", 259200);
    }

}
