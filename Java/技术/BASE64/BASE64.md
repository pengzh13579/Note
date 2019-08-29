[TOC]

### 1 简述
​        BASE64主要用途不是加密，而是把一些二进制数转成普通字符，方便在网络上传输。 由于一些二进制字符在传输协议中属于控制字符，不能直接传送，所以需要转换一下才可以。由于某些系统中只能使用ASCII字符，Base64就是用来将非ASCII字符的数据转换成ASCII字符的一种方法，Base64特别适合在http，mime协议下快速传输数据。比如网络中图片的传输。
​        BASE64，并非安全领域下的加密解密算法。虽然经常遇到所谓的base64的加密解密。但base64只能算是一个编码算法，对数据内容进行编码来适合传输。虽然base64编码过后原文也变成不能看到的字符格式，但是方式初级又简单。

### 2 原理
​        BASE64编码方法，要求把每三个8bit的字节转换为四个6bit的字节，其中，转换之后的这四个字节中每6个有效bit为是有效数据，空余的那2个bit用0补上成为一个字节。因此BASE64所造成数据冗余不是很严重，BASE64是当今比较流行的编码方法，因为它编起来速度快而且简单。

### 3 例子
有三个字节的原始数据：aaaaaabb　bbbbccccc　ccdddddd（这里每个字母表示一个bit位）
那么编码之后会变成：　00aaaaaa　00bbbbbb　00cccccc　00dddddd

所以可以看出base64编码简单，虽然编码后不是明文，看不出原文，但是解码也很简单

### 4 代码
```
/**
 * BASE64解密
 * @throws Exception
 */
public static byte[] decryptBASE64(String str) throws Exception {
	BASE64Decoder decoder = new BASE64Decoder();
	byte[] bytes = decoder.decodeBuffer(key);
	return new String(bytes, "UTF-8");
}

/**
 * BASE64加密
 * @throws Exception
 */
public static String encryptBASE64(String str) throws Exception {
	BASE64Encoder encoder = new BASE64Encoder();
	return encoder.encodeBuffer(key.getBytes("UTF-8"));
}
```