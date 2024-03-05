package com.itmoli.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

public class AliOSSUtils {
    public static String upload(MultipartFile file)  throws Exception{
        // 上传储存文件的空间路径
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        //身份凭证
        String accessKeyId = "LTAI5tBUN77viufroXNUJcEX";
        String accessKeySecret ="rURm8Cnoo0vHdRvCsiqHOQZIIefJy0";
        // 储存空间名称。
        String bucketName = "xiao-lz";
        //文件输出流，上传文件
        InputStream inputStream = file.getInputStream();
        // 需要上传的文件名称
        //使用uuid来避免文件名称重复
        //1.获取原文件名
        String filename = file.getOriginalFilename();
        //2.获取文件后缀
        String substring = filename.substring(filename.lastIndexOf("."));
        //3.获取uuid
        UUID uuid = UUID.randomUUID();
        //4。拼接文件名
        String objectName = uuid.toString()+substring;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        String url = null;

        try {
            //发送请求上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

            ossClient.putObject(putObjectRequest);

            //生成前端展示的文件url
            //https://xiao-lz.oss-cn-beijing.aliyuncs.com/9d4112c7-0953-404b-b8dd-a4eaad0d772b.jpg
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1]
                         + "/" + objectName;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return url;
    }
}
