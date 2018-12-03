OmegaT Tencent Translate plugin
==========================

This is a plugin to allow [OmegaT](http://omegat.org/) to source machine translations from [Tencent Cloud](https://cloud.tencent.com/product/tmt)

**The functionality provided by this plugin is built into OmegaT 4.1.0 and
later. Please uninstall the plugin if you are using this version or later.**

Usage
=====

Before run, Log in to [Tencent Cloud API Key Console](https://console.cloud.tencent.com/cam/capi), create a secretId & scretKey or use an existing secretId & scretKey.

1. Download the ZIP from
   [releases](https://github.com/yoyicue/omegat-tencent-plugin/releases) and place it
   into one of the **OmegaT plugins folders**. OmegaT plugin should be placed in `C:\Program Files\OmegaT\plugins`(Windows),`~/Library/Preferences/OmegaT/plugins/`(Mac OS X) or `$HOME/.omegat/plugins`(Linux/UNIX),  depending on your operating systems.

2. In OmegaT, **configure your secretId & scretKey** in Machine Translation Preferences.

3. In OmegaT, enable **Options > Preferences > Machine Translate > Tencent Translate**. Translations will
   appear in the Machine Translation pane.


Building
========


1.Download  [tencentcloud-sdk-java](https://github.com/tencentcloud/tencentcloud-sdk-java) or zip file [tencentcloud-sdk-java.zip](https://tencentcloud-sdk-1253896243.file.myqcloud.com/tencentcloud-sdk-java/tencentcloud-sdk-java.zip?_ga=1.242139138.1268576572.1543238001).

2.Copy Tencent cloud sdk source to src/main/java/com folder.

3.Add gson-2.2.4.jar okhttp-2.5.0.jar okio-1.6.0.jar OmegaT.jar to your CLASSPATH.

License
=======

This project is distributed under the [GNU General Public License,
v3](http://www.gnu.org/licenses/gpl-3.0.html).

This project makes use of the following components:
- [tencentcloud-sdk-java](https://github.com/tencentcloud/tencentcloud-sdk-java) 3.0.35 (License Unknown)

