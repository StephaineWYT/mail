package wen.mail.service;

public interface MailService {

    public void sendSimpleMail(String to, String subject, String content);

    public void sendHtmlMail(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    public void sendInlineResourcesMail(String to, String subject, String content, String srcPath, String srcId);
}
