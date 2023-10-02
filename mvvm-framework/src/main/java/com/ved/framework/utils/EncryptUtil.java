package com.ved.framework.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public final class EncryptUtil {

    private EncryptUtil() {
    }

    /**
     * 为生成唯一表示码提供处理
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        //返回实现指定转换的Cipher对象 "算法/模式/填充"
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //创建一个DESKeySpec对象，使用8个字节的key作为DES密钥的内容
        DESKeySpec desKeySpec = new DESKeySpec(encryptKey.getBytes("UTF-8"));
        //返回转换指定算法的秘密密钥的SecretKeyFactory对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        //根据提供的密钥生成SecretKey对象
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        //使用iv中的字节作为iv来构造一个iv ParameterSpec对象。复制该缓冲区的内容来防止后续修改
        IvParameterSpec iv = new IvParameterSpec(encryptKey.getBytes());
        //用密钥和一组算法参数初始化此 Cipher；Cipher：加密、解密、密钥包装或密钥解包，具体取决于 opmode 的值。
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        //加密同时解码成字符串返回
        return new String(Base64Util.encode(cipher.doFinal(encryptString.getBytes("UTF-8"))));
    }

    /**
     * 为生成唯一表示码提供处理
     */
    public static String decryptDES(String decodeString, String decodeKey) throws Exception {
        //使用指定密钥构造IV
        IvParameterSpec iv = new IvParameterSpec(decodeKey.getBytes());
        //根据给定的字节数组和指定算法构造一个密钥。
        SecretKeySpec skeySpec = new SecretKeySpec(decodeKey.getBytes(), "DES");
        //返回实现指定转换的 Cipher 对象
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //解密初始化
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        //解码返回
        byte[] byteMi = Base64Util.decode(decodeString.toCharArray());
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    /**
     * MD5单向加密，不能解密，一般用于验证密码是否正确
     */
    public static class Md5Util {
        // 加密算法MD5
        private static final String MD5 = "MD5";

        /**
         * MD5加密，单向加密，不能解密的
         */
        public static byte[] encryptByMD5(String data) {
            // String result = null;
            byte[] resultBytes = null;

            try {
                // 获取一个信息加密的加密对象，传递一个加密算法名称的参数
                MessageDigest md = MessageDigest.getInstance(MD5);

                byte[] bytes = data.getBytes("utf-8");

                // 编码
                resultBytes = md.digest(bytes);

                // result = new String(resultBytes);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultBytes;
        }


        /**
         * 获取字符串的 MD5
         */
        public static String encodeStringByMD5(String str) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(str.getBytes("UTF-8"));
                byte messageDigest[] = md5.digest();
                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    hexString.append(String.format(Locale.getDefault(), "%02X", b));
                }
                return hexString.toString().toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 获取文件的 MD5
         */
        public static String encodeFileByMD5(File file) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                FileInputStream inputStream = new FileInputStream(file);
                DigestInputStream digestInputStream = new DigestInputStream(inputStream,
                        messageDigest);
                //必须把文件读取完毕才能拿到md5
                byte[] buffer = new byte[4096];
                while (digestInputStream.read(buffer) > -1) {
                }
                MessageDigest digest = digestInputStream.getMessageDigest();
                digestInputStream.close();
                byte[] md5 = digest.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : md5) {
                    sb.append(String.format(Locale.getDefault(), "%02X", b));
                }
                return sb.toString().toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 一般情况下，先使用MD5加密，再使用BASE64编码传输
         */
        public static String encryptByMD5AndBASE64(String data) {

            byte[] bytes = encryptByMD5(data);

            String buff = null;
            try {
                buff = new String(bytes, "utf-8");

            } catch (Exception e) {
                e.printStackTrace();
            }

            String result = Base64Util.encryptBASE64(buff);

            return result;
        }

    }

    /**
     * BASE64编码解码
     */
    public static class Base64Util {

        /**
         * 为生成唯一表示码提供处理
         */
        private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
        private static byte[] codes = new byte[256];

        public static char[] encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }
            return out;
        }

        public static byte[] decode(char[] data) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=')
                --len;
            if (data.length > 1 && data[data.length - 2] == '=')
                --len;
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length)
                throw new Error("miscalculated data length!");
            return out;
        }

        static {
            for (int i = 0; i < 256; i++)
                codes[i] = -1;
            for (int i = 'A'; i <= 'Z'; i++)
                codes[i] = (byte) (i - 'A');
            for (int i = 'a'; i <= 'z'; i++)
                codes[i] = (byte) (26 + i - 'a');
            for (int i = '0'; i <= '9'; i++)
                codes[i] = (byte) (52 + i - '0');
            codes['+'] = 62;
            codes['/'] = 63;
        }

        /**
         * 编码
         */
        public static String encryptBASE64(String data) {
            byte[] bytes = null;
            try {
                bytes = data.getBytes("utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(bytes, Base64.DEFAULT);

        }

        /**
         * 编码
         */
        public static String encryptBASE64(byte[] bytes) {
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }

        public static String encryptBASE64NoWrap(byte[] bytes) {
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        }

        /**
         * 译码
         */
        public static String decryptBASE64ToStr(String encodeStr) {
            String result = null;
            try {
                byte[] decodeBytes = Base64.decode(encodeStr, Base64.DEFAULT);
                result = new String(decodeBytes, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 译码
         */
        public static byte[] decryptBASE64ToBytes(String encodeStr) {
            return Base64.decode(encodeStr, Base64.DEFAULT);
        }

    }

    /**
     * AES加密解密
     */
    public static class AESUtil {
        private static IvParameterSpec enc_iv;
        private static SecretKeySpec enc_key;
        private static Cipher cipherEnc;

        //密钥有限制，貌似不能随便改
        private static String AESKEY = "0123456789abcdef";
        private static String IvKey = "fedcba9876543210";

        static final char[] HEX_CHAR_TABLE = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };


        static {//初始化代码块
            try {
                cipherEnc = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (NoSuchPaddingException ex) {
                ex.printStackTrace();
            }
            enc_key = new SecretKeySpec(AESKEY.getBytes(), "AES");
            enc_iv = new IvParameterSpec(IvKey.getBytes());
        }


        /**
         * 加密
         *
         * @param str 要加密的字符串
         * @return 加密后的字符串
         */
        public static String encrypt(String str) {
            byte[] ret = null;

            try {
                enc_key = getKey(AESKEY);
                cipherEnc.init(Cipher.ENCRYPT_MODE, enc_key, enc_iv);
                String padRight = padRight(str, ((int) Math.ceil(str.length() / 16.0)) * 16);
                byte[] origData = padRight.getBytes("UTF-8");
                ret = cipherEnc.doFinal(origData);

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            return byteArray2HexString(ret);
        }

        /**
         * 解密
         *
         * @param str 要解密的字符串
         * @return
         */
        public static String decrypt(String str) {
            byte[] ret = null;

            try {
                cipherEnc.init(Cipher.DECRYPT_MODE, enc_key, enc_iv);
                ret = cipherEnc.doFinal(hexString2ByteArray(str));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            try {
                return new String(ret, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }


        public static String byteArray2HexString(byte[] b) {
            if (b == null) {
                return null;
            }
            final StringBuilder hex = new StringBuilder(2 * b.length);
            for (final byte by : b) {
                hex.append(HEX_CHAR_TABLE[(by & 0xF0) >> 4]).append(HEX_CHAR_TABLE[(by & 0x0F)]);
            }
            return hex.toString();
        }

        public static byte[] hexString2ByteArray(String s) {
            if (s == null) {
                return null;
            }
            byte high, low;
            int len = s.length() / 2;
            byte[] b = new byte[len];
            for (int i = 0, k = 0; i < len; i++, k += 2) {
                high = (byte) (Character.digit(s.charAt(k), 16) & 0x0F);
                low = (byte) (Character.digit(s.charAt(k + 1), 16) & 0x0F);
                b[i] = (byte) ((high << 4) | low);
            }

            return b;
        }

        /**
         * 将16进制转换为二进制
         */
        public static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                return null;
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < hexStr.length() / 2; i++) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }

        /**
         * 将二进制转换成16进制
         */
        public static String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                String hex = Integer.toHexString(buf[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        }

        public static String padLeft(String s, int n) {
            return String.format(Locale.getDefault(), "%1$#" + n + "s", s);
        }

        public static String padRight(String s, int n) {

            return String.format(Locale.getDefault(), "%1$-" + n + "s", s);
        }

        /**
         * 获取适配密钥
         */
        private static SecretKeySpec getKey(String strKey) throws Exception {
            byte[] arrBTmp = strKey.getBytes();
            byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

            for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
                arrB[i] = arrBTmp[i];
            }

            SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

            return skeySpec;
        }

        /**
         * 加密
         *
         * @param content 需要加密的内容
         * @return 解密后的字节数组
         */
        public static byte[] encryptString2Bytes(String content) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128, new SecureRandom(AESKEY.getBytes()));
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");// 创建密码器
                byte[] byteContent = content.getBytes("utf-8");
                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(byteContent);
                return result; // 加密
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * DES加密解密
     */
    public static class DesUtil {
        // 加密算法
        private static final String ALGORITHM = "DES";

        /**
         * DES加密
         *
         * @param base64Key : 经BASE64二次加密的密钥
         * @return
         */
        public static byte[] encryptByDES(String base64Key, byte[] dataBytes)
                throws Exception {

            // BASE64解密
            String encodeKey = Base64Util.decryptBASE64ToStr(base64Key);

            // 获得key
            Key key = getKey(encodeKey);

            // 获得加密解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // 初始化，使用密钥进行加密
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(dataBytes);

        }

        /**
         * DES解密
         *
         * @param base64Key        ： 经过BASE64编码且SecretKey加密的密钥
         * @param encryptDataBytes ：经过加密的数据
         * @return ：译码之后的字节数组
         * @throws Exception
         */
        public static byte[] decryptByDES(String base64Key,
                                          byte[] encryptDataBytes) throws Exception {

            // 获得key
            Key k = getKey(Base64Util.decryptBASE64ToStr(base64Key));

            // 获得加密解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, k);

            return cipher.doFinal(encryptDataBytes);

        }


        /**
         * 获取解码后密钥
         *
         * @param encodeKey ： 经SecretKey加密的密钥字符串
         * @return Key ：解码后的密钥
         */
        private static Key getKey(String encodeKey) {

            SecretKey key = null;

            try {
                // 密钥参数
                DESKeySpec dks = new DESKeySpec(encodeKey.getBytes());

                SecretKeyFactory factory = SecretKeyFactory
                        .getInstance(ALGORITHM);

                // 生成密钥
                key = factory.generateSecret(dks);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return key;

        }

        /**
         * 生成密钥
         *
         * @param seed ： 种子
         * @return ：经过SecretKey一次编码与BASE64二次编码的密钥字符串
         */
        public static String initKey(String seed) {

            // 获得密钥生成器所需要的随机参数
            SecureRandom random = null;

            if (null != seed) {
                random = new SecureRandom(seed.getBytes());
            } else {
                random = new SecureRandom();
            }

            // 获取密钥生成器
            KeyGenerator generator = null;
            try {
                generator = KeyGenerator.getInstance(ALGORITHM);

                // 初始化，设置随机码
                generator.init(random);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            // 生成密钥，一个8位字节的
            SecretKey key = generator.generateKey();

            // 获得经过SecretKey编码后对应的字节数组
            byte[] encodeKey = key.getEncoded();

            // 对密钥字节数组进行BASE64编码传输
            String base64Key = Base64Util.encryptBASE64(encodeKey);

            return base64Key;

        }

    }

    /**
     * RSA非对称加密解密
     *
     * @author Administrator
     */
    public static class RsaUtil {

        public static final String KEY_ALGORITHM = "RSA";
        public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

        private static final String PUBLIC_KEY = "RSAPublicKey";
        private static final String PRIVATE_KEY = "RSAPrivateKey";

        private static final int KEY_LENGTH = 1024;


        /**
         * 使用私钥对数据进行加密
         *
         * @param data
         * @param base64Key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPrivateKey(byte[] data, String base64Key)
                throws Exception {
            // 解码密钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(base64Key);

            // 获得私钥
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

            PrivateKey privateKey = factory.generatePrivate(spec);

            // 使用私钥对数据加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] bytes = cipher.doFinal(data);

            return bytes;

        }

        /**
         * 使用公钥对数据进行加密
         *
         * @param data
         * @param base64Key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPublicKey(byte[] data, String base64Key)
                throws Exception {
            // 解码密钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(base64Key);

            // 获得公钥
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

            PublicKey publicKey = factory.generatePublic(spec);

            // 使用公钥对数据加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] bytes = cipher.doFinal(data);

            return bytes;
        }

        /**
         * 使用公钥解密数据
         *
         * @param data
         * @param base64Key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPublicKey(byte[] data, String base64Key)
                throws Exception {
            // 解码密钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(base64Key);
            // 获得公钥
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

            PublicKey publicKey = factory.generatePublic(spec);

            // 使用公钥对数据
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] bytes = cipher.doFinal(data);

            return bytes;

        }

        /**
         * 使用私钥解密数据
         *
         * @param data
         * @param base64Key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPrivateKey(byte[] data, String base64Key)
                throws Exception {
            // 解码密钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(base64Key);
            // 获得私钥
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

            PrivateKey privateKey = factory.generatePrivate(spec);

            // 使用私钥对数据加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] bytes = cipher.doFinal(data);

            return bytes;

        }

        /**
         * 初始化密钥对，将生成的密钥对存放到Map集合
         *
         * @return
         * @throws Exception
         */
        public static Map<String, Object> initKey() throws Exception {

            KeyPairGenerator generator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);

            // 指定密钥长度
            generator.initialize(KEY_LENGTH);

            // 生产密钥对
            KeyPair keyPair = generator.generateKeyPair();

            // 分别获得公钥与私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            // 将密钥存放到map集合并返回该map对象
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PRIVATE_KEY, privateKey);
            keyMap.put(PUBLIC_KEY, publicKey);

            return keyMap;

        }

        /**
         * 获得BASE64编码的私钥
         *
         * @param keyMap ：存放元素私钥的集合
         * @return BASE64编码的私钥字符串
         */
        public static String getBase64PrivateKey(Map<String, Object> keyMap) {
            // 获取私钥
            Key privateKey = (Key) keyMap.get(PRIVATE_KEY);

            // 对私钥进行BASE64编码
            String base64PrivateKey = Base64Util.encryptBASE64(privateKey
                    .getEncoded());

            // 返回编码后的私钥
            return base64PrivateKey;
        }

        /**
         * 获得BASE64编码的公钥
         *
         * @param keyMap ：存放元素私钥的集合
         * @return BASE64编码的公钥字符串
         */
        public static String getBase64PublicKey(Map<String, Object> keyMap) {
            // 获取私钥
            Key publicKey = (Key) keyMap.get(PUBLIC_KEY);

            // 对私钥进行BASE64编码
            String base64PrivateKey = Base64Util.encryptBASE64(publicKey
                    .getEncoded());

            // 返回编码后的私钥
            return base64PrivateKey;
        }


        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       加密数据
         * @param privateKey 私钥
         * @return
         * @throws Exception
         */
        public static String sign(byte[] data, String privateKey)
                throws Exception {
            // 解密由base64编码的私钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(privateKey);

            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);

            return Base64Util.encryptBASE64(signature.sign());
        }

        /**
         * 校验数字签名
         *
         * @param data      加密数据
         * @param publicKey 公钥
         * @param sign      数字签名
         * @return 校验成功返回true 失败返回false
         * @throws Exception
         */
        public static boolean verify(byte[] data, String publicKey, String sign)
                throws Exception {

            // 解密由base64编码的公钥
            byte[] keyBytes = Base64Util.decryptBASE64ToBytes(publicKey);

            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            // 获取签名对象
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 设置公钥，初始化验证
            signature.initVerify(pubKey);
            // 通过签名更新数据
            signature.update(data);

            // 验证签名是否正常
            return signature.verify(Base64Util.decryptBASE64ToBytes(sign));
        }

    }


    /**
     * 3DES辅助类
     *
     * @author chenbo
     * @version $Id: ThreeDes.java,v 0.1 2016年1月29日 上午10:21:50 chenbo$Exp
     */
    public static class ThreeDes {

        /**
         * DES,DESede,Blowfish 定义加密算法,可用
         */
        private static final String Algorithm = "DESede";

        /**
         * 填充方式
         */
        private static final String deAlgorithm = "DESede/ECB/NoPadding";

        /**
         * encryptMode 加密
         */
        public static byte[] encryptMode(byte[] keybyte, byte[] src) throws Exception {
            src = buildBodyBytes(src);
            // 填充Key
            keybyte = build3DesKey(keybyte);
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, 0, 24, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(deAlgorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);// 在单一方面的加密或解密
        }

        /**
         * decryptMode 解密
         */
        public static byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
            byte[] busDt = null;
            // 填充Key
            keybyte = build3DesKey(keybyte);
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, 0, 24, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(deAlgorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            busDt = c1.doFinal(src);
            return busDt;
        }

        public static byte[] build3DesKey(byte[] temp) {
            byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
            System.arraycopy(temp, 0, key, 0, temp.length);
            // 补充的8字节就是16字节密钥的前8位
            for (int i = 0; i < 8; i++) {
                key[16 + i] = temp[i];
            }
            return key;
        }

        public static byte[] buildBodyBytes(byte[] body) {
            int len = body.length % 8 == 0 ? 0 : 8 - (body.length % 8);
            if (len > 0) {
                byte[] addByte = new byte[len];
                for (int i = 0; i < addByte.length; i++) {
                    addByte[i] = 0x00;
                }
                body = ByteUtil.contactArray(body, addByte);
            }
            return body;
        }
    }
}