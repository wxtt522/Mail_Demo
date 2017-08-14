package com.test;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Created by Administrator on 2017/8/1.
 */
public class RealMial {

    //发件人的邮箱和密码/授权码
    public static String myEmailAccount = "17606516052@163.com";
    public static String myEmailPassword = "wl960920";

    //发件人邮箱的smtp服务器地址，网易为 smtp.163.com
    public static String myEmailSMTPHost = "smtp.163.com";

    //收件人邮箱
    public static String receiveMailAccount = "fr9even@163.com";

    public static void main(String[] args) throws Exception {
        //1.创建参数配置，用于连接邮件服务器的参数配置
        Properties props = new Properties();    //参数配置
        props.setProperty("mail.transport.protocol", "smtp");//使用的协议
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");

        //2.根据配置创建会话对象，用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true); //设置为debug模式

        //3.创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);

        //4.根据session获取邮件传输对象
        Transport transport = session.getTransport();

        //5.使用邮件账号和密码连接服务器
        transport.connect(myEmailAccount, myEmailPassword);

        //6.发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        //关闭连接
        transport.close();


    }
        /*
        创建一封只包含文本的简单邮件
         */

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        //创建一份邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail,"某宝网","UTF-8"));
        message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress(receiveMail, "wxtt", "UTF-8")});
        message.setSubject("打个屁的这，骗你呢","UTF-8");
        message.setContent("date()可能并没有什么软用","text/html;charset=UTF-8");
//        message.setSentDate(new Date());
//        message.setSentDate(new Date("Tue Aug 01 10:43:23 CST 2017"));
        message.saveChanges();
        return message;
    }
}
