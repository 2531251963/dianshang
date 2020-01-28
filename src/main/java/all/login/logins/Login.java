package all.login.logins;


import all.login.entity.User;
import all.login.Service.LoginService;
import all.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

public class Login {
    private Jedis jedis;

    public Login(){
        jedis = RedisUtil.getJedis();
        jedis.auth("123456");
    }


    public String loginM(String data){
        JSONObject json = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = JSON.toJavaObject(jsonObject,User.class );

        //前端登录界面传进json
        //1、在redis中的hashmap查询k是否存在，且判断redis密码是否正确
        String str1=lookForInHashmap(user);
        switch (str1) {
            //3、该用户没有注册过，跳转到注册界面。
            case "Register":
                json.put("msg", "Not_Register");
                return json.toString();
            //4、用户名密码正确
            case "Login_OK":
                //把正确账号密码保存token
                String tokenstr = Token.saveTokenToRedis(user);
                json.put("token", tokenstr);
                json.put("phoneNumber", user.getPhoneNumber());
                json.put("password", user.getPassword());
                json.put("msg", "Success_Login_With_PhonenumberAndPassword");
                return json.toString();
            //5、redis中密码错误，重新登录
            case "Login":
                json.put("msg", "Fail_Login_With_WrongPassword");
                return json.toString();

            //6、用户注册过，但是redis中不存在账号密码，查询数据库
            case "LookForSql":
                String str2 = lookForInMysql(user);
                switch (str2) {
                    //7、数据库查询账号密码正确
                    case "Login_OK":
                        //把正确账号密码保存token
                        String tokenstr1 = Token.saveTokenToRedis(user);
                        //把正确账号密码保存redis
                        saveRecordToRedis(user);
                        json.put("token", tokenstr1);
                        json.put("phoneNumber", user.getPhoneNumber());
                        json.put("password", user.getPassword());
                        json.put("msg", "Success_Login_With_PhonenumberAndPassword");
                        return json.toString();
                    //8、数据库查询密码错误
                    case "Login":
                        json.put("msg", "Fail_Login_With_WrongPassword");
                        return json.toString();
                    //9、数据库没有查询到信息，显示注册
                    case "Register":
                        json.put("msg", "Not_Register");
                        return json.toString();
                    default:
                        json.put("msg", "Login_Again");
                        return json.toString();
                }
            default:
                json.put("msg", "Login_Again");
                return json.toString();
        }
    }

    private void saveRecordToRedis(User user) {
        jedis.set("p"+user.getPhoneNumber(),user.getPassword());
    }

    //1、在redis中的hashmap查询k是否存在，且判断redis密码是否正确
    public String lookForInHashmap(User user){
        //2、该用户有注册信息
        if(jedis.hexists("phonenumber",user.getPhoneNumber())){
            //查询redis是否存在账号密码
            if(jedis.exists("p"+user.getPhoneNumber())){
                //4、redis存在账号密码，且密码正确,登录成功
                if(jedis.get("p"+user.getPhoneNumber()).equals(user.getPassword())){
                    return "Login_OK";
                }else {
                    //5、查询redis密码错误，登录失败，提示密码错误，重新登录
                    return "Login";
                }
            }else {
                //6、用户注册过，但是redis中不存在账号密码，查询数据库
                return "LookForSql";
            }
        }else {
            //3、该用户没有注册过，跳转到注册界面。
            return "Register";
        }
    }

    //6、查询数据库，且判断数据库密码是否正确
    public String lookForInMysql(User user){
        LoginService loginService = new LoginService();
        User userMysql=loginService.selectUserByPhoneNumber(user.getPhoneNumber());
        //数据库中存在注册信息
        if(userMysql!=null){
            if(userMysql.getPassword().equals(user.getPassword())){
                //7、数据库用户名密码正确
                return "Login_OK";
            }else {
                //8、数据库查询密码错误，需要重新输入
                return "Login";
            }
        }else {
            //9、数据库中不存在注册信息，需要注册
            //System.out.println("数据库中没有找到phonenumber，请先注册");
            return "Register";
        }
    }
}
