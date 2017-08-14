package justTest;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * 创建一封可以发送文本、图片、文件及其他复杂附件的邮件
 */
public class JavaMailDemo {

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

    public static MimeMessage createMimeMessage(Session session, String sendMail,String reseiveMail) throws UnsupportedEncodingException, MessagingException {
        //1.创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //2.From 发件人
        message.setFrom(new InternetAddress(sendMail,"我的测试邮件—发件人","UTF-8"));
        //3.TO 收件人,用add方法不需要接受list\\ 果然 还是要list
        message.addRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress(reseiveMail, "我的测试邮件", "UTF-8")});
        //4.subject邮件主题
        message.setSubject("TEST邮件主题【文本+图片】","UTF-8");
        /**
         * 先使邮件内容的创建
         */
        //5.创建图片节点
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("C://users/Administrator/Desktop/风一样的码农.png"));
        image.setDataHandler(dh);   //图片数据添加到节点
        image.setContentID("image_fairy_tail"); //为节点设置一个编号
        //6.创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        //这里添加图片的方式是将整个图片包含到邮件内容中，实际上也可以以http连接的形式
        text.setContent("这是一张图片<br/><img src='https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3530256458,892403971&fm=58 '/>","text/html;charset=UTF-8");

        //7.文本+图片 设置文本和图片节点的关系 将文本和图片混合成一个节点
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related"); //关联关系

        //8.将文本+图片的混合节点封装成一个普通节点
        //最终添加到邮件content是由多个bodypart主城的multipart，所以我们需要的是bodypart
        //上面的mm_text_image 并非是bodypart，所有要把mm_text_image封装成一个bodypart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        //9.创建附件节点
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("C://Users/Administrator/Desktop/WEB.doc"));
        attachment.setDataHandler(dh2);
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        //10.设置 (文本+图片)和附件的关系 合成一个大的混合节点
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment); //多个附件，创建多个多次添加
        mm.setSubType("mixed");

        //11.设置整个邮件的关系，将最终的混合几点作为邮件的内容添加到邮件
        message.setContent(mm);

        //保存设置，不设置时间了
        message.saveChanges();

        return message;
    }

}
