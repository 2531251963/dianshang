package all.RegisterAndForgetpassword;

import all.util.RedisUtil;
import all.util.SendCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;


import java.util.HashMap;
import java.util.Map;

/**
 * @author forget
 */
public class ForgetPassword {
    public String sendcode(String phonenumber) {

        Map<String, String> map = new HashMap<String, String>();
        Jedis jedis = RedisUtil.getJedis();

        if (jedis.hexists("phonenumber", phonenumber)) {
            //return "账号已存在";英文
            map.put("msg", "账号已存在");
        } else {

            if (jedis.exists("cc" + phonenumber)) {

                map.put("msg", "验证码已发送");
            } else {
                jedis.setex("cc" + phonenumber, 60, SendCodeUtil.sendCode(phonenumber));
            }
        }
        return JSON.toJSONString(map);

    }
    /**
     * @param data {
     *             "phonenumber":"11111111",
     *             "code":"123234",
     *             "password":"123456"
     *             }
     */
    public String forget(String data){

        Map<String ,String> res=new HashMap<String, String>();

        JSONObject map=JSON.parseObject(data);

        Jedis jedis = RedisUtil.getJedis();

        String phonenumber=map.getString("phonenumber");
        String code1=map.getString("code");
        String password = map.getString("password");

        if (jedis.hexists("phonenumber" ,phonenumber)) {
            if (jedis.exists("cc" + phonenumber)) {
                // return "验证码已发送";
                if(jedis.get("cc" + phonenumber).equals(code1)) {
                    Register register  = new Register();
                    register.userupdate(phonenumber,password);

                    res.put("msg","密码修改成功");
                }
                else{
                   // return验证码错误;
                    res.put("msg","验证码错误");
                }
            }
            else {
                res.put("msg","请发送验证码");
            }

        } else {
            //return "账号不存在，请确认账号是否输入正确";
            res.put("msg","账号不存在，请确认账号是否输入正确");
        }
        return JSON.toJSONString(res);
    }
}
