package com.ashlikun.baseproject.module.main.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.os.RemoteException
import android.widget.Toast
import com.ashlikun.baseproject.common.utils.other.BleUtils
import com.ashlikun.baseproject.libcore.utils.extend.requestPermission
import com.ashlikun.baseproject.module.main.databinding.MainActivityBleBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DateUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.hex.hexToBytes
import com.ashlikun.utils.other.hex.toHexStr
import com.ashlikun.utils.other.hex.toHexStrSpace
import com.ashlikun.utils.other.readByteArrayBE
import com.ashlikun.utils.ui.extend.click
import com.ashlikun.utils.ui.extend.toast
import com.ashlikun.utils.ui.extend.toastInfo
import okhttp3.internal.and
import okhttp3.internal.toHexString
import java.util.UUID


/**
 * 作者　　: 李坤
 * 创建时间: 2024/3/29　14:19
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
@SuppressLint("MissingPermission")
class BleTestActivity : BaseActivity() {
    override val binding by lazy {
        MainActivityBleBinding.inflate(layoutInflater)
    }

    // 初始化蓝牙适配器
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var bleGatt: BluetoothGatt? = null
    var writeCharacteristic: BluetoothGattCharacteristic? = null
    var notifyCharacteristic: BluetoothGattCharacteristic? = null
    val gattCallback by lazy {
        initializeGattCallback()
    }

    fun sendData(data: BleLockData) {
        val result = data.toBytes()
        LogUtils.e("发送的数据是   ${result.toHexStrSpace}")
        writeCharacteristic?.value = result
        bleGatt?.writeCharacteristic(writeCharacteristic)
    }

    //    val devKey = "505152535455565758595A5B5C5D5E5F".hexToBytes
    val devKey = "01020304050607080102030405060708".hexToBytes

    val R1 = ByteArray(8) { (0..0xFF).random().toByte() }


    var tac = byteArrayOf()
    var R2 = byteArrayOf()
    override fun initView() {
        binding.button0.click {
            startScanningForDevices()
        }
        binding.button1.click {
            bleGatt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                bluetoothAdapter.getRemoteDevice("DA:4F:00:07:38:31").connectGatt(AppUtils.app, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
            } else {
                bluetoothAdapter.getRemoteDevice("DA:4F:00:07:38:31").connectGatt(AppUtils.app, false, gattCallback)
            }
        }

        binding.apply {
            button22.click {
                if (bleGatt != null) {
                    val currentDate = DateUtils.getCurrentTime("YYMMDDHHMMSS ")
                    val data = currentDate.hexToBytes + R1
                    sendData(BleLockData(0xB1.toByte(), 0x10.toByte(), data))
                }
            }
            button2.click {
                if (bleGatt != null) {
                    //HAC = AES(DevKey, R2),取结果后4Byte作为HAC
                    val hacAll = BleUtils.aesEncrypt(getAesContent(R2), devKey)
                    LogUtils.e("HACALL = ${hacAll.toHexStrSpace}")
                    val hac = hacAll.readByteArrayBE(12, 4)
                    sendData(BleLockData(0xB1.toByte(), 0x11.toByte(), hac))
                }
            }
            button3.click {
                if (bleGatt != null) {
                    sendData(BleLockData(0xB1.toByte(), 0x24.toByte(), byteArrayOf(0.toByte())))
                }
            }
            button4.click {
                if (bleGatt != null) {
                    sendData(BleLockData(0xB1.toByte(), 0x26.toByte(), byteArrayOf(0.toByte())))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startScanningForDevices() {
        requestPermission(arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION)) {
            initBeacon()
//            val filters = mutableListOf<ScanFilter>()
//            val scanSettings = ScanSettings.Builder()
//                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
//                .build()
//            bluetoothAdapter.bluetoothLeScanner.startScan(
//                filters,
//                scanSettings,
//                scanCallback
//            )
        }

    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            if (device.name.startsWith("LOCK-")) {
                LogUtils.e("dddddd，${device.name} ,, ${device}")
            }
        }

        override fun onScanFailed(errorCode: Int) {
        }
    }

    // 实现 BleManager 的抽象方法，处理连接事件
    fun initializeGattCallback(): BluetoothGattCallback {
        return object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                LogUtils.e("onConnectionStateChange ${status},,${newState}")
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        gatt.discoverServices()
                    }

                    BluetoothProfile.STATE_DISCONNECTED -> {
                        // 可能重新尝试连接或其他逻辑
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                LogUtils.e("发现服务 ${status}")
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    bleGatt = gatt
                    // 查找并操作特定服务和特性
                    val service = gatt.getService(UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E"))
                    if (service != null) {
                        val notify = service.getCharacteristic(UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E"))
                        if (notify != null) {
                            if (BleUtils.enableNotification(gatt, true, notify)) {
                                notifyCharacteristic = notify
                            }
                        }

                        val write = service.getCharacteristic(UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E"))
                        if (write != null) {
                            if (write.properties and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE != 0) {
                                writeCharacteristic = write
                                //无回调形式的写入
                                write!!.writeType = BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
                                gatt.writeCharacteristic(write)
                            } else if (write.properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
                                //有回调形式
                                writeCharacteristic = write
                            }

                        }
                    }
                } else {
                    LogUtils.e("Service discovery failed with status $status")
                }
            }

            /**
             * 蓝牙Notify推送过来的数据
             * 子线程
             */
            override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray) {
                super.onCharacteristicChanged(gatt, characteristic, value)
                LogUtils.d("接收到原始数据：${characteristic.value.toHexStrSpace}")

                MainHandle.post {
                    handleData(characteristic.value)
                }
            }

            // 添加其他回调，如数据接收、读写特征值完成等
            // ...

            // 内部实现了断开连接后的自动重连机制
        }
    }

    private fun handleData(value: ByteArray) {
        //c1 10 0c 58 5b 7f 08 35 da 98 0c be 56 db 38
        val data = BleLockData(value[0], value[1], value.readByteArrayBE(3, value.size), value[2])
        LogUtils.e("处理结果 ${data}")
        if (data.isDevice()) {
            if (data.cmd == 0x10.toByte()) {
                //1、TAC：终端鉴别码，4Byte，锁没激活时取0xFFFFFFFF
                //2、随机数：8Byte
                tac = data.data.readByteArrayBE(0, 4)
                R2 = data.data.readByteArrayBE(4, 12)
                val result = BleUtils.aesEncrypt(getAesContent(R1), devKey)
                val jisuanTac = result.readByteArrayBE(12, 4)
                if (jisuanTac.toHexStr == tac.toHexStr) {
                    LogUtils.e("握手返回 校验通过")
                }
                LogUtils.e("握手返回 tac = ${tac.toHexStrSpace},,R2 = ${R2.toHexStrSpace}，，，，result=${result.toHexStrSpace},,")
            } else if (data.cmd == 0x11.toByte()) {
                //鉴权
                if (data.data.getOrNull(0) == 0x0.toByte()) {
                    LogUtils.e("鉴权成功")
                } else {
                    LogUtils.e("鉴权失败")
                }
            } else if (data.cmd == 0x24.toByte()) {
                val data0 = data.data.getOrNull(0)
                //0：开锁成功，1：开锁错误，2：关锁错误，3：检测到有速度，0xF2:权限不足
                if (data0 == 0x0.toByte()) {
                    LogUtils.e("开锁成功")
                } else if (data0 == 0x01.toByte()) {
                    LogUtils.e("开锁错误")
                } else if (data0 == 0x02.toByte()) {
                    LogUtils.e("关锁错误")
                } else if (data0 == 0x03.toByte()) {
                    LogUtils.e("检测到有速度")
                } else if (data0 == 0xF2.toByte()) {
                    LogUtils.e("权限不足")
                }
            } else if (data.cmd == 0x26.toByte()) {
                val data0 = data.data.getOrNull(0)
                //0：关锁成功，1：开锁错误，2：关锁错误，3：检测到有速度，0xF2:权限不足
                if (data0 == 0x0.toByte()) {
                    LogUtils.e("关锁成功")
                } else if (data0 == 0x01.toByte()) {
                    LogUtils.e("开锁错误")
                } else if (data0 == 0x02.toByte()) {
                    LogUtils.e("关锁错误")
                } else if (data0 == 0x03.toByte()) {
                    LogUtils.e("检测到有速度")
                } else if (data0 == 0xF2.toByte()) {
                    LogUtils.e("权限不足")
                }
            }
        }
    }

    //进行16字节补齐得到16字节
    fun getAesContent(byteArray: ByteArray): ByteArray {
        if (byteArray.size >= 16) return byteArray
        val diff = 16 - byteArray.size
        return byteArray + byteArrayOf(0x80.toByte()) + ByteArray(diff - 1) { 0x0.toByte() }
    }

    fun initBeacon() {


    }
}


data class BleLockData(
    val head: Byte,
    var cmd: Byte,
    val data: ByteArray,
    var length: Byte = -1,
) {
    init {
        if (length.toInt() == -1.toByte()) {
            length = data.size.toByte()
        }
    }

    override fun toString(): String {
        return "BleLockData(head=${head.toUByte().toInt().toHexString()}, cmd=${cmd.toUByte().toInt().toHexString()}, length=${length.toUByte().toInt().toHexString()}, data=${data.toHexStrSpace})"
    }

    fun toBytes() = byteArrayOf(head, cmd, data.size.toByte()) + data
    fun isDevice() = (head and 0xF0) == 0xC0
    fun isApp() = (head and 0xF0) == 0xB0
    //单桢
    /**
     * bit	值	定义	描述
     * 7:4	0xB	主机端	主机App发出
     * 	0xC	从机端	从机设备Device发出（锁端发出）
     * 3:0	0x1	单帧	所有数据一帧发完（无后续帧）
     * 	0x2	多帧前续帧	传输过程中，还有后续帧
     * 	0x3	结束帧	最后一包数据，无后续帧
     * 	0x5	安全通道	仅用于单帧，帧头+16字节密文
     *
     */
    fun isFrameOnd() = (head and 0x0F) == 0x01
    fun isFrameMove() = (head and 0x0F) == 0x02
    fun isFrameEnd() = (head and 0x0F) == 0x03
    fun isFrameAnquan() = (head and 0x0F) == 0x05

    /**
     * 序号	命令名	CMD	说明	用户
     * 1	握手	0x10	同步时间，鉴权预处理	All
     * 2	鉴权	0x11	执行双向鉴权	Admin
     * 3	配置出厂参数	0x12	预留	Admin
     * 4	读取出厂参数	0x13	预留	Admin
     * 5	修改广播名	0x14	修改蓝牙广播名	Admin
     * 6	修改密码	0x15	修改设备密钥	Admin
     * 7	查询设备信息	0x16	 例如设备的版本号等	Admin
     * 8	查询锁状态	0x22	查询当前锁状态	Admin
     * 9	开锁	0x24	进行开锁操作	Admin
     * 10	关锁	0x26	进行关锁操作	Admin
     * 11	获取电量	0x2A	获取电量操作	All
     * 12	查看无感解锁位	0x34	预留	Admin
     * 13	设置无感解锁位	0x36	预留	Admin
     * 14	进入空中升级	0xAB	OTA功能查询	Admin
     */


}
