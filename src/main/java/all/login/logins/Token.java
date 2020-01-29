package all.login.logins;

import all.login.entity.TokenE;
import all.login.entity.User;
import all.util.RedisUtil;
import all.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

public class Token {
    final private Jedis jedis ;
    private String token;

    public Token(){
        jedis = RedisUtil.getJedis();
        //jedis.auth("123456");
        token="token";

    }

    public String getToken(String data) {
        //data包含token
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONObject json = new JSONObject();
        TokenE tokenE = JSON.toJavaObject(jsonObject,TokenE.class );

        //1、判断redis是否存在token
        if(jedis.exists("token_"+tokenE.getUserid())){
            //比较redis的token与传入token的值是否相同
            if(jedis.get("token_"+tokenE.getUserid()).equals(tokenE.getToken())){
                //redis存在token，比对成功后，免密成功，并且更新token时间为三天
                token=TokenUtil.getToken(tokenE.getUserid());
                jedis.set("token_"+tokenE.getUserid(), token, "NX", "EX", 259200);
                json.put("userid",tokenE.getUserid());
                json.put("token",token);
                json.put("msg","Success_Login");
                return json.toString();
            }else {
                //redis存在token，比对失败后，免密失败，返回重新进入登录界面
                json.put("userid",tokenE.getUserid());
                json.put("token",tokenE.getToken());
                json.put("msg","Fail_Login");
                return json.toString();
            }


        }else {
            //不存在token，表示登录过期，则重新输入用户名密码
            json.put("token",tokenE.getToken());
            json.put("msg","Fail_Login_Without_PhonenumberAndPassword");
            return json.toString();
        }
    }

    public static String saveTokenToRedis(Jedis jedis,User user){
        //jedis1.auth("123456");
        String tokenstr=TokenUtil.getToken(user.getPhoneNumber());
        jedis.set("token_"+user.getPhoneNumber(), tokenstr, "NX", "EX", 259200);
        return tokenstr;
    }


}
