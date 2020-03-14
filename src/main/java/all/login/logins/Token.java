package all.login.logins;

import all.login.entity.TokenE;
import all.login.entity.User;
import all.util.RedisUtil;
import all.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

public class Token {
    final private Jedis jedis ;
    private String token;
    private String userid="userid";

    public Token(){
        jedis = RedisUtil.getJedis();
        //jedis.auth("123456");
        token="token";

    }

    public String getToken(String data) {
        JSONObject json = new JSONObject();
        TokenE tokenE=null;
        //data包含token
        try{
            JSONObject map = JSON.parseObject(data);
            String tokenn=map.getString("token");
            tokenE.setToken(tokenn);
        }catch (JSONException e){
            json.put("msg","Attack!");
            return json.toString();
        }

        //token数据有效
        if(!tokenE.getUserid().equals("userid")&&!tokenE.getToken().equals("token")){
            //1、判断redis是否存在token
            if(jedis.exists("token_"+tokenE.getUserid())){
                //比较redis的token与传入token的值是否相同
                if(jedis.get("token_"+tokenE.getUserid()).equals(tokenE.getToken())){
                    //redis存在token，比对成功后，免密成功，并且更新token时间为三天
                    boolean keyExist = jedis.exists("token_"+tokenE.getUserid());
                    if (keyExist) {
                        jedis.del("token_"+tokenE.getUserid());
                    }
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
                json.put("msg","Fail_Login");
                json.put("userid",userid);
                return json.toString();
            }
        }
        else {
            json.put("msg","Fail_Login");
            return json.toString();
        }
    }

    public static String saveTokenToRedis(Jedis jedis,String userid){
        //jedis1.auth("123456");
        boolean keyExist = jedis.exists("token_"+userid);
        if (keyExist) {
            jedis.del("token_"+userid);
        }
        String tokenstr=TokenUtil.getToken(userid);
        jedis.set("token_"+userid, tokenstr, "NX", "EX", 259200);
        return tokenstr;
    }
}
