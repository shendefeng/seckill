package top.yolopluto.seckill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.entity.User;
import top.yolopluto.seckill.mapper.UserMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/25 19:40
 * @description: 生成用户工具类
 * @Modified By:
 */
public class UserUtil {
    @Resource
    private UserMapper userMapper;
    public void WriteUserCookie() throws IOException {
        List<User> users= userMapper.list();
//        for (User user : users) {
//            System.out.println(user.getNickname());
//        }
        //登录，生成UserTicket
        String urlString = "http://localhost:8888/login/doLogin";
        File file = new File("C:\\seckillInfo\\userConfig.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User tUser = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String params = "mobile=" + tUser.getId() + "&password="+ MD5Utils.inputPassToFromPass("123456");
            outputStream.write(params.getBytes());
            outputStream.flush();
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                byteArrayOutputStream.write(buff, 0, len);
            }
            inputStream.close();
            byteArrayOutputStream.close();
            String respone = new String(byteArrayOutputStream.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RequBean respBean = mapper.readValue(respone, RequBean.class);
            String userTicket = (String) respBean.getObject();
            System.out.println("create userTicket:" + tUser.getId());
            String row = tUser.getId() + "," + userTicket;
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(row.getBytes());
            randomAccessFile.write("\r\n".getBytes());
            System.out.println("write to file :" + tUser.getId());
        }
        randomAccessFile.close();
        System.out.println();
    }


}
