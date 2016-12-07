# reactNativeBundleBreak
reactNative bundle
React Native的页面都会通过

react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle

打离线包，然后将生成的index.android.bundle放入assets文件件，app启动的时候离线加载，我们打开index.android.bundle

大致可以将所有离线Bundle文件分成如图三个圈所示的三个部分：

     (1)公共的头文件部分
     
     (2)当前rn页面的业务代码，里面包含了我们写的js页面的 所有的js代码包括 组件和Style样式等等  __d(0)
     
     (3)由于这个页面比较大，绿圈并没有完整的包含全部的js方法__d(12)——__d(374)是公共的js方法，所有的Rn js文件打包都会有的方法，然后__d(375+)则是一些额外的方法，比如引用到了外部的某个图片资源 又或者是导入了外部的模块
     
    在文件最后一般会require(177)和require(0)做为整个RN页面的入口 177是Js基础文件的配置入口，0是当前页面的业务入口。
    
整个基础部分的bundle文件的js方法有300多个，并且有530kb，而业务部分的js代码只有1个，如果使用react-native bundle打出的离线包，每一个RN页面都有530kb的内容是重复的，有多个页面的时候肯定会导致文件臃肿，并且热跟新效率不高，通过内置bundle文件会增加apk的体积，通过接口从服务端下载也会浪费很多流量。

基于对bundle文件内容的方式我们可以得到一种解决方案：

    (1) 本地内置一个common.js文件，里面包含了bundle文件公共部分的代码，
    
    (2)将业务部分的就是代码也就是绿圈部分单独生成一个js文件，business.js,
    
    (3)在需要展示加载某一个页面的时，将common.js和当前页面需要加载的business.js合并，然后再加载。
    
这个方案比较简单粗暴，能够解决bundle文件臃肿，体积太大的问题，但是还是有缺陷，例如每次往js引擎加载bundle文件的时候，往内存读取了相同的一部分文件（common.js）并且文件的体积很大，页面加载完成之前会有明显的白屏显示，如果js页面的引擎能够重用，并且将加载引擎的动作放到后台执行 （重用ReactInstanceManager）是不是能进一步优化RN的性能。
在这里先提一下unbundle 命令，其实在react Native 生成离线包的时候还支持一个unbundle命令：

react-native unbundle --platform android --dev false --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle

先看一下打包后在assets下生成的文件目录：除了生成index.android.bundle文件外还额外生成了一个js-modules文件夹，

里面的xx.js里面的内容就是将之前的__d(xx)抽出来单独放到一个文件里面，通过require(xx)加载到内存供调用

基于unbundle命令我们可以设计一个公的空白页面的RNActivity用来加载相应的业务模块，这个页面可以预先在后台初始化js引擎，将公共部分的common.js文件读取到内存，然后设置一个监听事件，通过emmit方式，当需要加载某个页面的的module的时候讲这个页面的module的id传递过来，然后通过require方法调用这个模块

还可以建立一个config文件，记录当前被加载的模块，如果某个模块已经被加载读取，则不需要重复加载。




