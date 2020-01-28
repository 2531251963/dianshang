package all.RegisteForget;


import all.util.SendCodeUtil;
import all.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;


import java.util.HashMap;
import java.util.Map;


/**
 * @author zhao wen bo
 */
public class ServletRegister {


    public String sendcode(String phonenumber) {

        Map<String, String> map = new HashMap<String, String>();
        Jedis jedis = RedisUtil.getJedis();

        if (jedis.hexists("phonenumber", phonenumber)) {
            //return "账号已存在";
            map.put("msg", "账号已存在");
        } else {

            if (jedis.exists("c" + phonenumber)) {
                // return "验证码已发送";
                map.put("msg", "验证码已发送");
            } else {
                jedis.setex("c" + phonenumber, 60, SendCodeUtil.sendCode(phonenumber));
            }
        }
        return JSON.toJSONString(map);

    }

    /**
     * @param data {
     *             "phonenumber":"17822002550",
     *             "code":"12345",
     *             "password":"123456"
     *             }
     */
    public String register(String data) {
        Map<String, String> res = new HashMap<String, String>();
        JSONObject map = JSON.parseObject(data);

        String phonenumber = map.getString("phonenumber");
        String code0 = map.getString("code");
        String password = map.getString("password");


        Jedis jedis = RedisUtil.getJedis();
        if (jedis.hexists("phonenumber", phonenumber)) {
            //return "账号已存在";
            res.put("msg", "账号已存在");
        } else {
            //先验证验证码是否发送
            if (jedis.exists("c" + phonenumber)) {
                if (jedis.get("c" + phonenumber).equals(code0)) {
                    Register register = new Register();
                    register.userregister(phonenumber, password);
                    res.put("msg","注册成功");
                } else {
                    //return "验证码错误";
                    res.put("msg", "验证码错误");
                }
            }
            else{
                res.put("msg", "请发送验证码");
            }
        }
        return JSON.toJSONString(res);


    }


}