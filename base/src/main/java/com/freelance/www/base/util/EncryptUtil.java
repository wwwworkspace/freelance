package com.freelance.www.base.util;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class EncryptUtil {
    private final byte[] DESIV = new byte[] { 18, 52, 86, 120, -112, -85, -51, -17 };

    private AlgorithmParameterSpec iv = null;

    private Key key = null;

    private String charset = "utf-8";

    public EncryptUtil(String deSkey) throws Exception {
        if (StringUtils.isNotBlank(this.charset))
            this.charset = this.charset;
        DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));
        this.iv = new IvParameterSpec(this.DESIV);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        this.key = keyFactory.generateSecret(keySpec);
    }

    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        enCipher.init(1, this.key, this.iv);
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }

    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(2, this.key, this.iv);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        return new String(pasByte, "UTF-8");
    }
}
