package org.pettyfox.tool.password.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

/**
 * @author Petty Fox
 * @version 1.0
 */
@Component
public class PasswordRsaService {

    @Value("${RSA_COUNT:}")
    private Integer encryptCount = 2;
    public String encrypt(String password,String publicKey){
        RSA rsa = new RSA(null, publicKey);
        for(int i = 0;i<encryptCount;i++){
            byte[] encrypt = rsa.encrypt(StrUtil.bytes(password, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
            password = Base64.encode(encrypt);
        }
        return password;
    }
    public String decrypt(String password,String privateKey){
        RSA rsa = new RSA(privateKey, null);
        for(int i = 0;i<encryptCount;i++){
            byte[] decrypt = rsa.decrypt(Base64.decode(password), KeyType.PrivateKey);
            password = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
//            password = StrUtil.sub(password,0,password.length()-rsaSalt.length());
        }
        return password;
    }

    public static void main(String[] args) {
        PasswordRsaService service = new PasswordRsaService();
        KeyPair pair = SecureUtil.generateKeyPair("RSA", 4096);
        String k = service.encrypt("nihao",Base64.encode(pair.getPublic().getEncoded()));
        System.out.println(k);

        System.out.println(service.decrypt(k,Base64.encode(pair.getPrivate().getEncoded())));
    }
}
