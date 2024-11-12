package com.ashlikun.baseproject.common.utils.other

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.ashlikun.utils.encryption.AESUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.hex.toHexStrSpace
import java.io.UnsupportedEncodingException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * 作者　　: 李坤
 * 创建时间: 2024/3/29　15:11
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object BleUtils {

    /**
     * 是否开启蓝牙的通知
     */
    @SuppressLint("MissingPermission")
    fun enableNotification(bluetoothGatt: BluetoothGatt, enable: Boolean, characteristic: BluetoothGattCharacteristic): Boolean {
        if (!bluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            return false
        }
        var result = false
        //获取到Notify当中的Descriptor通道  然后再进行注册
        characteristic.descriptors.forEach {
            //写入描述值
            if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                it.value = if (enable) BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            } else if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                //两个都是通知的意思，notify和indication的区别在于，notify只是将你要发的数据发送给手机，没有确认机制，
                //不会保证数据发送是否到达。而indication的方式在手机收到数据时会主动回一个ack回来。即有确认机制，只有收
                //到这个ack你才能继续发送下一个数据。这保证了数据的正确到达，也起到了流控的作用。所以在打开通知的时候，需要设置一下。
                it.value = if (enable) BluetoothGattDescriptor.ENABLE_INDICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            }
            if (bluetoothGatt.writeDescriptor(it)) {
                result = true
            }
        }
        return result
    }

    fun aesEncrypt(content: ByteArray,password: ByteArray,): ByteArray {
        LogUtils.e("加密数据 ，content = ${content.toHexStrSpace},,,,password=${password.toHexStrSpace}")
        var password = password
        try {
            val secretKeySpec = SecretKeySpec(password, AESUtils.AES)
            val cipher = Cipher.getInstance(AESUtils.DEFAULT_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            return cipher.doFinal(content)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return byteArrayOf()
    }
}