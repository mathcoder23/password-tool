package org.pettyfox.tool.password.po;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty Fox
 * @version 1.0
 */
@Getter
@Setter
@Slf4j
public class PasswordStore {
    private static final String PWD_SPLIT = "###";
    private String commContent;
    private String privateEncryptHash;
    private String passwordHash;
    private List<String> segmentPassword;
    public PasswordStore(String password,String privateEncryptHash,int segmentCount){
        this.passwordHash = SecureUtil.md5(password);
        String pwd;
        if(password.contains(PWD_SPLIT)){
            this.commContent = password.split(PWD_SPLIT)[0];
            pwd = password.split(PWD_SPLIT)[1];
        }else{
            pwd = password;
            commContent = "";
        }
        if(segmentCount<=0){
            segmentCount = 1;
        }
        List<String> pwdList = new ArrayList<>(segmentCount);
        int pIndex = 0;
        int size = (int) Math.max(pwd.length()/(segmentCount*1.0f),1);
        for(int i = 0;i<segmentCount;i++){
            if(i == segmentCount -1){
                pwdList.add(StrUtil.subSuf(pwd,pIndex));
            }else{
                pwdList.add(StrUtil.sub(pwd,pIndex,pIndex+size));
            }
            pIndex+=size;
        }
        this.segmentPassword = pwdList;
        this.privateEncryptHash = privateEncryptHash;
    }
    public String toContent(int segmentIndex){
        if(segmentPassword.size()>segmentIndex){
            return String.format("加密签名:%s,%s,分段密码(%s):%s",this.privateEncryptHash,commContent,segmentIndex,segmentPassword.get(segmentIndex));
        }
        return commContent;
    }

    public static void main(String[] args) {
        PasswordStore store = new PasswordStore("你好###12345678","",1);
        for (int i = 0;i<store.getSegmentPassword().size();i++){
            log.info(store.toContent(i));
        }

    }
}
