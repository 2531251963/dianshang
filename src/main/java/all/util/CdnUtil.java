package all.util;

import com.aliyun.oss.OSSClient;

import java.io.File;

/**
 * @ClassName CdnUtil
 * @Description TODO
 * @Author Liyihe
 * @Date 19-4-23 下午6:20
 * @Version 1.0
 */
public class CdnUtil {
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAIBHBFbhKxDBTU";
    private static final  String accessKeySecret = "gibydtl1h2tUHxAr6FFq45cZMyf3ob";
    private static final String bucketName="liyihe";
    private static final String fileFolderName="dianshang/";
    /**
     * 上传
     */
    public static void upload(String objectName, File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileFolderName+objectName, file);
        ossClient.shutdown();
    }
    /**
     * 删除
     */
    public static void deleteFile(String objectName){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileFolderName+objectName);
        ossClient.shutdown();
    }

}
