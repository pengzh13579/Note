[TOC]

## 1 远程桌面连接报错
远程桌面连接Window Server时报错信息如下：
出现身份验证错误，要求的函数不正确，这可能是由于CredSSP加密Oracle修正。
![20180510160641974](/assets/807693-20180509173052799-1726761131.png)
解决方法：
运行 gpedit.msc
本地组策略：
计算机配置>管理模板>系统>凭据分配>加密Oracle修正
选择启用并选择易受攻击。
![20180510160641974](/assets/807693-20180509173055378-2066484289.png)
注意：只有专业版可行
## 2 Windows10插入U盘不显示
现象：右下角显示U盘图标，但是资源管理器中读取不到
解决方案：打开设备和打印机->选择u盘图标，右击->删除设备->拔出U盘再次插入即可
![微信截图_20191223100604](/assets/微信截图_20191223100604.png)