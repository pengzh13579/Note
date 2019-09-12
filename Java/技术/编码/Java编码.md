[TOC]

### 1 常见的编码表
 - ASCII：美国标准信息交换表
 - ISO8859-1:拉丁码表，欧洲码表
 - GB2312:中国的中文编码表
 - GBK:中国的中文编码表升级
 - GB18030:GBK的取代版本
 - BIG5：通用于香港、台湾地区的繁体字编码方案
 - UTF-8：最多用3个子节表示一个字符
 - Unicode：国际标准码，融合了多种文字，所有的文字都用两个子节来表示，Java语言使用的就是该码表

### 2 常见使用
#### 2.1 流文件
具有转换编码功能的有：OutputStreamWriter 和 InputStreamReader 
构造器如下：
// 创建指定字符集的 InputStreamReader
InputStreamReader(InputStream in, String CharsetName)
// 创建使用指定字符集的 OutputStreamWriter
OutputStreamWriter(OutputStream out, String CharsetName)
#### 2.2 字符串
// 重新对获取的字符串进行编码
Byte[] bytes = str.getBytes(String encodeCharName);
// 重新对bytes进行编码，创建新的字符串对象
str = new String(Byte[] bytes, String decodeCharsetName);
// 结合使用
str = new String(str.getBytes(String encodeName), String decodeCharsetName);
#### 2.3 请求参数
java中编码：URLEncoder.encode(strUri, “UTF-8”); 
java中解码：URLDecoder.decode(strUri, “UTF-8”);
### 3 代码
```
String str = "%E7%B2%BE%E7%AE%97%E9%AA%8C%E8%AF%81%E4%BB%A3%E7%A0%81%E6%96%B9%E4%BE%BF%E5%AD%A6%E4%B9%A0.doc";
String urlStr = URLDecoder.decode(str, "UTF-8");
// 精算验证代码方便学习.doc
System.out.println(urlStr);
String tmpStr = new String(urlStr.getBytes("UTF-8"), "ISO8859-1");
// ??????é??è????????????????????.doc
System.out.println(tmpStr);
tmpStr = new String(tmpStr.getBytes("ISO8859-1"), "UTF-8");
// 精算验证代码方便学习.doc
System.out.println(tmpStr);
```