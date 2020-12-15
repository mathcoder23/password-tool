package org.pettyfox.tool.password.rest;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.pettyfox.tool.password.service.Password2MailService;
import org.pettyfox.tool.password.service.PasswordRsaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Petty Fox
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@ApiOperation(value = "api接口", tags = "a")
@Slf4j
public class PasswordRest {

    @Resource
    private Password2MailService password2MailService;
    @Resource
    private PasswordRsaService passwordRsaService;

    @Value("${RSA_PRIVATE:}")
    private String rsaPrivate;
    @Value("${RSA_PUBLIC:}")
    private String rsaPublic;

    @PostMapping("passwordRsaEncode")
    @ApiOperation(value = "明文密码RSA加密")
    public Map<String, String> passwordRsaEncode(
            @ApiParam @RequestParam("password") String password,
            @RequestParam(value = "publicKey", required = false) String publicKey) {
        if (StrUtil.isBlank(publicKey)) {
            publicKey = rsaPublic;
        }
        Map<String, String> map = new HashMap<>(2);
        String result = passwordRsaService.encrypt(password,publicKey);
        map.put("result", result);
        map.put("md5", SecureUtil.md5(result));
        return map;
    }

    @PostMapping("passwordRsaDecode")
    @ApiOperation(value = "密文密码RSA解密")
    public Map<String, String> passwordRsaDecode(
            @RequestParam("password") String password,
            @RequestParam(value = "privateKey", required = false) String privateKey) {
        if(StrUtil.isBlank(privateKey)){
            privateKey = rsaPrivate;
        }
        RSA rsa = new RSA(privateKey, null);
        String pwd = passwordRsaService.decrypt(password,privateKey);
        Map<String, String> map = new HashMap<>(1);
        map.put("result", "结果已经发送邮件");
        password2MailService.sendPassword(pwd, SecureUtil.md5(password));
        return map;
    }

    @PostMapping("buildRsaKey")
    @ApiOperation(value = "生成RSA密钥对")
    public Map<String, String> buildRsaKey(@RequestParam(name = "size", defaultValue = "4096") Integer size) {
        KeyPair pair = SecureUtil.generateKeyPair("RSA", size);
        Map<String, String> map = new HashMap<>(2);
        map.put("privateKey", Base64.encode(pair.getPrivate().getEncoded()));
        map.put("publicKey", Base64.encode(pair.getPublic().getEncoded()));
        return map;
    }
}
