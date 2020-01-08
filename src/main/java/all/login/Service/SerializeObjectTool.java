package all.login.Service;

import java.io.*;
import java.util.Map;

public class SerializeObjectTool {

    //序列化
    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            //System.out.println("获取失败");
            e.printStackTrace();
        }
        return null;
    }

    // 反序列化
    public static Object unserizlize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            //System.out.println("hashshap缓存获取失败");
            e.printStackTrace();
        }

        return null;
    }

    // 反序列化
    public static Map<String, String> unserizlizeMap(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Map<String, String> obj = (Map<String, String>)oii.readObject();
            return obj;
        } catch (Exception e) {
            //System.out.println("user.phonenumber缓存获取失败");
            e.printStackTrace();
        }
        return null;
    }



}
