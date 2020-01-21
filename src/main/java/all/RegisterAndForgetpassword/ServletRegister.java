package all.RegisterAndForgetpassword;


import all.util.SendCodeUtil;
import all.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;


import java.util.HashMap;
import java.util.Map;


public class ServletRegister {
    public String sendcode(String phonenumber){

        Map<String ,String> map=new HashMap<String, String>();
        Jedis jedis =RedisUtil.getJedis();

        if(jedis.hexists("phonenumber",phonenumber)){
            //return "账号已存在";
            map.put("msg","账号已存在");
        }else {

            if (jedis.exists("c"+phonenumber)){
                // return "验证码已发送";
                map.put("msg","验证码已发送");
            }else {
                jedis.setex("c"+phonenumber,60,SendCodeUtil.sendCode(phonenumber));
            }
        }
        return JSON.toJSONString(map);

    }

    /**
     *
     * @param data
     *
     * {
     *     "phonenumber":"11111111",
     *     "code":"123234",
     *     "password":"123456"
     * }
     */
    public  String register(String data) {
        Map<String,String> res=new HashMap<String, String>();
        JSONObject map=JSON.parseObject(data);

        String phonenumber=map.getString("phonenumber");
        String code0=map.getString("code");
        String password = map.getString("password");


        Jedis jedis =RedisUtil.getJedis();
        if(jedis.hexists("phonenumber",phonenumber)){
            //return "账号已存在";
            res.put("msg","账号已存在");
        }
        else{

            if(jedis.get("c"+phonenumber)==code0){


                Register register = new Register();
                register .userregister(phonenumber,password);
                //保存已经注册的手机号
                jedis.hset("phonenumber",phonenumber,"");
                //保存已经注册的用户密码
                jedis.set("p"+phonenumber,password);
                res.put("msg","成功注册");
            }
            else{
                //return "验证码错误";
                res.put("msg","验证码错误");
            }

        }
        return JSON.toJSONString(res);
    }

}

