package me.zohar.unionpaywatch

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.util.regex.Pattern

class NotificationListener : NotificationListenerService() {

    override fun onListenerConnected() {
        super.onListenerConnected()

    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        Log.d(TAG, "onNotificationPosted: $sbn")


        val extras = sbn.notification.extras


        Log.d(TAG, "onNotificationPosted: $extras")

        val text = extras.getString("android.text")


        if (sbn.packageName == "com.unionpay") {
            //title:消息推送 content:云闪付收款1元。
            if (!text!!.isEmpty()) {

            }
        } else if (sbn.packageName == "com.xiaomi.xmsf") {
            //云闪付扫码收款

            if (text != null) {

            }

        } else if (sbn.packageName == "me.zohar.unionpaywatch") {
            //测试推送
            val money = text?.let { extractMoney(it) }
            if (money != null) {
                //说明收到款了 推送服务器


            }
        }
    }


    /**
     * @param content eg: *小鸡通过扫码向您付款1110.10元,您的收款卡号..
     * @return
     */
    private fun extractMoney(content: String): String? {
        val pattern = Pattern.compile("(向您付款)(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?元")
        val matcher = pattern.matcher(content)

        if (matcher.find()) {
            val tmp = matcher.group()
            val patternnum = Pattern.compile("(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?")
            val matchernum = patternnum.matcher(tmp)
            return if (matchernum.find()) matchernum.group() else null
        } else {
            return null
        }

    }

    companion object {
        private val TAG = "NotificationListener"
    }

}
