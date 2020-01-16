package all.RegisterAndForgetpassword;

import all.util.RedisUtil;
import all.util.SendCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Update;
import redis.clients.jedis.Jedis;


import java.util.HashMap;
import java.util.Map;

/**
 * @author forget
 */
public class ForgetPassword {
    public String forget(String data){

        Map<String ,String> res=new HashMap<String, String>();

        JSONObject map=JSON.parseObject(data);

        Jedis jedis = RedisUtil.getJedis();

        String phonenumber=map.getString("phonenumber");
        String code0=map.getString("code");
        String password = map.getString("password");

        if (jedis.hexists("phonenumber" ,phonenumber)) {
            if (jedis.exists("cc" + phonenumber)) {
                // return "验证码已发送";
                if(jedis.get("cc"+phonenumber)==code0) {
                    Register register  = new Register();
                    register.userupdate(phonenumber,password);
                }
                else{
                   // return验证码错误;
                    res.put("msg","验证码错误");
                }
            } else {

                jedis.setex("cc" + phonenumber, 60,SendCodeUtil.sendCode(phonenumber));

            }

        } else {
            //return "账号不存在，请确认账号是否输入正确";
            res.put("msg","账号不存在，请确认账号是否输入正确");
        }
        return JSON.toJSONString(res);
    }
}
