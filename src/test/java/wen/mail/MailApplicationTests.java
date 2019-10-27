package wen.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wen.mail.service.MailService;

@SpringBootTest
class MailApplicationTests {

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("820352756@qq.com", "The Simple Mail", "Good afternoon, Stephanie!");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content = "<html>\n" + "<body>\n" + " <h6>it is a mail from html mail form</h6>\n" + "</body>\n" + "</html>";
        mailService.sendHtmlMail("820352756@qq.com", "HTML mail", content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filepath = "E:\\DocsFromCDisk\\sci.php";
        mailService.sendAttachmentsMail("820352756@qq.com", "script", "speech", filepath);
    }

    @Test
    public void sendInlineResourceMail() {
        String srcId = "Stephanie";
        String content = "<html><body>图片邮件：<img src=\'id:" + srcId + "\' ></body></html>";
        String imgPath = "E:\\imagesFromCDisk\\Camera Roll\\1.jpg";
        mailService.sendInlineResourcesMail("820352756@qq.com", "image mail", content, imgPath, srcId);
    }

}
