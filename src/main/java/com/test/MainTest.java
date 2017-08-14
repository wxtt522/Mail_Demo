package com.test;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2017/8/1.
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        //1.创建一封邮件
        Properties props = new Properties();    //用于连接邮件服务器的参数配置
        Session session = Session.getDefaultInstance(props);//根据参数配置创建会话对象
        MimeMessage message = new MimeMessage(session);//创建邮件对象
        /*
        也可以根据已有的eml邮件文件创建MineMessage对象
        MimeMessage message =new MimeMessage(session,new FileInputStream("MyEmain.eml"));
         */

        //2.From发件人
        //其中internetAddress的三个参数分别为：邮箱，显示的昵称，昵称字符集编码
        message.setFrom(new InternetAddress("17606516052@163.com", "sin404", "utf8"));


        //3.TO收件人
        message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress("fr9even@163.com", "wxtt", "utf8")});

        //4.Subject:邮件主题
        message.setSubject("Test邮件测试","UTF-8");

        //5.Content:邮件正文
        message.setContent("TEST,这里是邮件的正文，可是我也想不明白说些什么。。","text/html;charset=UTF-8");

        //6.设置显示的收发时间
        message.setSentDate(new Date());

        //7.保存前面的设置
        message.saveChanges();

        //8.将该邮件保存到本地
        OutputStream out = new FileOutputStream("MyEmail.eml");
        message.writeTo(out);
        out.flush();
        out.close();

    }
}
