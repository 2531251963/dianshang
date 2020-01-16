package all.login.logins;

import all.login.Entity.User;

public class test {
    public static void main(String[] args) {
//
//        Jedis redis = new Jedis("127.0.0.1", 6379);
//        redis.auth("123456");

//        // 存对象
//        User p1 = new User(); // peson类记得实现序列化接口 Serializable
//        p1.setPhoneNumber("152846666");
//        p1.setPassword("555555");
//        redis.set("p"+p1.getPhoneNumber(),p1.getPassword());
//
//        // 存对象
//        User p2 = new User(); // peson类记得实现序列化接口 Serializable
//        p2.setPhoneNumber("152846254");
//        p2.setPassword("58452");
//        redis.set("p"+p2.getPhoneNumber(),p2.getPassword());

//        // 存对象
//        User p3 = new User(); // peson类记得实现序列化接口 Serializable
//        p3.setPhoneNumber("152846334");
//        p3.setPassword("123456");
//        redis.set("p"+p3.getPhoneNumber(),p3.getPassword());
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(p1.getPhoneNumber(), p1.getPassword());
//        map.put(p2.getPhoneNumber(), p2.getPassword());
//        map.put(p3.getPhoneNumber(), p3.getPassword());
//        byte[] personByte = SerializeObjectTool.serialize(map);
//        redis.set("hashmap".getBytes(), personByte);



        User user=new User();
        user.setPhoneNumber("12521252323");
        user.setPassword("123123123");
        login logi=new login();

        String str=logi.login(user.getPhoneNumber());
        System.out.println(str);
        //登录成功 保存token值
        if(str.equals("Login_OK")){



        }



        TokenProvider tokenProvider=new TokenProvider();
        String str1=tokenProvider.createToken(user.getPhoneNumber());
        System.out.println(str1);
        System.out.println(str1.length());


    }

//    {
//
//          //状态位，ok表示成功
//
//            "status":"0",
//
//          //申请的有效token值
//
//            "token":"4a28d8516d42f4821e6d5782d1a79a7a",
//
//          //token的有效时间,单位为秒，这里设定7天有效期（604800秒）
//
//            "expires_in":"604800",
//
//          //token过期情况下，用来刷新access_token值,设置30天的有效期
//
//            "refresh_token":"8ab486662d50e6d51a5a5dd6a25c9a4b"
//
//    }
}
