package all.login.logins;

import all.login.Service.SerializeObjectTool;
import all.util.RedisUtil;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class TokenProvider {
    private Jedis redis ;

    public TokenProvider(){
        redis = RedisUtil.getJedis();
        redis.auth("123456");
    }

    public String createToken(String json) {//String tokenInfo, Long expireTime
        //token  45位
        String token = System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "");

        //把token存入redis
        saveTokenToRedis(token,json);
        return token;
    }

    public void saveTokenToRedis(String token,String json){
        redis.set(token, json, "NX", "EX", 259200);
    }

    //根据token从redis中查询用户json
    public String getUserByToken(String token) {
        String json=null;
        try {
            json = redis.get(token);
            System.out.println("token = "+token);
            System.out.println("Login_json = "+json);
        }catch (NullPointerException e){
            //token已过期 或者没有token记录
            System.out.println("缓存中没有找到token，需要重新登录");
            //设置返回重新登录的json


        }
        //更新过期时间
        redis.expire(token, 259200);
        //返回用户信息
        return json;
    }
}
