package com.vivwe.base.exception

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.widget.Toast
import com.vivwe.main.activity.MainActivity



/**
 * ahtor: super_link
 * date: 2019/6/5 18:39
 * remark:
 */
class CrashCollectHandler: Thread.UncaughtExceptionHandler {

    var mContext: Context? = null
    var mDefaultHandler:Thread.UncaughtExceptionHandler ?=null


    fun init(pContext: Context) {
        this.mContext = pContext
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    //当UncaughtException发生时会转入该函数来处理
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (!handleException(e) && mDefaultHandler!=null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler?.uncaughtException(t,e)
        }else{

//            Log.e(">>>", "很抱歉,程序出现异常,即将退出!")
//            try {
//                //给Toast留出时间
//                Thread.sleep(2000)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
            //退出程序
            //mContext!!.applicationContext.i.removeAllActivity()


//            val intent = Intent()
//            intent.action = Globals.EXIT_APP
//            mContext!!.sendBroadcast(intent)
//            android.os.Process.killProcess(android.os.Process.myPid())
//            System.exit(0)
            restartApp()

        }

    }

    fun handleException(ex: Throwable?):Boolean {
        if (ex == null){
            return false
        }
        Thread{
            Looper.prepare()
            //toast("很抱歉,程序出现异常,即将退出")
            Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出!", Toast.LENGTH_LONG).show()
            Looper.loop()
        }.start()
        //收集设备参数信息
        //collectDeviceInfo(mContext);
        //保存日志文件
        //saveCrashInfo2File(ex);
        // 注：收集设备信息和保存日志文件的代码就没必要在这里贴出来了
        //文中只是提供思路，并不一定必须收集信息和保存日志
        //因为现在大部分的项目里都集成了第三方的崩溃分析日志，如`Bugly` 或 `啄木鸟等`
        //如果自己定制化处理的话，反而比较麻烦和消耗精力，毕竟开发资源是有限的
        return true
    }


    fun restartApp() {
        val intent = Intent(mContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext!!.startActivity(intent)
        android.os.Process.killProcess(android.os.Process.myPid())  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}
