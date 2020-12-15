package org.pettyfox.tool.password.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.pettyfox.tool.password.mail.IMailService;
import org.pettyfox.tool.password.po.PasswordStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 密码会按照某种策略分包发送到多个邮件
 *
 * @author Petty Fox
 * @version 1.0
 */
@Component
@Slf4j
public class Password2MailService {
    private static final String MAIL_RECEIVE_SPLIT = ",";
    @Resource
    private IMailService mailService;

    @Value("${MAIL_RECEIVE_LIST:}")
    private String mailReceive;

    public void sendPassword(String password, String encryptPasswordHash) {
        log.info("mail to {}", mailReceive);
        if (StrUtil.isBlank(mailReceive)) {
            log.error("send mail failed.not config MAIL_RECEIVE_LIST");
            return;
        }
        if(StrUtil.isBlank(password)){
            return;
        }
        List<String> mailList = new ArrayList<>();
        if (mailReceive.contains(MAIL_RECEIVE_SPLIT)) {
            mailList = Arrays.asList(mailReceive.split(MAIL_RECEIVE_SPLIT));
        }else{
            mailList.add(mailReceive);
        }
        PasswordStore store = new PasswordStore(password, encryptPasswordHash, mailList.size());
        for(int i = 0;i<mailList.size();i++){
            mailService.sendSimpleMail(mailList.get(i), "密码被解密", "密码被解密", "被解密密文签名：" + SecureUtil.md5(password)
                    + "\n明文内容:" + store.toContent(i));
        }

    }
}
