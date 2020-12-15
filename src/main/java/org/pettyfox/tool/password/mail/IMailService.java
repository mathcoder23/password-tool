package org.pettyfox.tool.password.mail;

public interface IMailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param name 收件人名称
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String name, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content);



    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);
}