package top.yolopluto.seckill;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yolopluto.seckill.dto.RequBean;
import top.yolopluto.seckill.entity.User;
import top.yolopluto.seckill.mapper.UserMapper;
import top.yolopluto.seckill.utils.MD5Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: yolopluto
 * @Date: created in 2024/2/25 19:47
 * @description: 用户生成测试
 * @Modified By:
 */
@SpringBootTest
public class UserTest {
    @Resource
    private UserMapper userMapper;

    /**
     * 生成用户
     */
    @Test
    public void CreateUser(){
        System.out.println("Create Users");
        List<User> users = new ArrayList<>();
        // 生成用户
        for(int i = 0; i < 100; i++){
            User user = new User();
            user.setId(13000000000L+i);
            user.setLoginCount(1);
            user.setNickname("user"+i);
            user.setCreateTime(new Date());
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Utils.inputPassToDBPass("123456", user.getSalt()));
            users.add(user);
        }
        System.out.println("插入数据库");
        userMapper.insertUsers(users);
    }

    /**
     * 读取用户
     * @throws IOException
     */
    @Test
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
