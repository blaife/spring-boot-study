package com.blaife.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoEncryptJasyptApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String plaintext = "123456";
        String ciphertext = stringEncryptor.encrypt(plaintext);
        System.out.println("==================");
        System.out.println(plaintext + " 加密后为：" + ciphertext);
    }

    @Test
    public void decryptPwd() {
        String ciphertext = "Q73uzbRtVtasPTO1eoMECB5Ntq/ygoHs1PkQebZ+YmBNPcauH9zvMASV22izKwAQ";
        String plaintext = stringEncryptor.decrypt(ciphertext);
        System.out.println("==================");
        System.out.println(ciphertext + " 解密后为：" + plaintext);
    }

}
