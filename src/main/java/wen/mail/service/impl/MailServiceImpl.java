package wen.mail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import wen.mail.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("邮件已发送");
        } catch (Exception e) {
            logger.error("异常！", e);
        }
    }

    /**
     * 发送带 HTML 模板的邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            //true 表示创建一个 multipart message
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html 邮件发送成功！");
        } catch (MessagingException e) {
            logger.error("HTML 邮件异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File((filePath)));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("带附件的邮件已发送");
        } catch (MessagingException e) {
            logger.error("发送附件异常！", e);
        }
    }

    /**
     * 发送带静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param srcPath
     * @param srcId
     */
    @Override
    public void sendInlineResourcesMail(String to, String subject, String content, String srcPath, String srcId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(srcPath));
            helper.addInline(srcId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");

        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}
