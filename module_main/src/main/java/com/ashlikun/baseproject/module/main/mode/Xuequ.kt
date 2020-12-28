package com.ashlikun.baseproject.module.main.mode

/**
 * 作者　　: 李坤
 * 创建时间: 2020/11/26　11:08
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
fun main() {
    var xuequ = Xuequ()
    print(xuequ.hex2arr(xuequ.polys))
}

class Xuequ {
    companion object {
        fun main() {
            var xuequ = Xuequ()
            print(xuequ.hex2arr(xuequ.polys))
        }
    }

    var polys = "c780ecf5ee305e40abeb504d49563f40010014001b1b338cdfa269418c1871e7e8e24b41bb63d6cbdea2694109cf2835e5e24b4171592a8de1a26941f5dbb332aee14b4179cda7cbe5a2694129058bf3eadf4b41384fb55de6a269417103c830e7df4b41a947645d51a5694103bcc42bfde04b41444888da03a669411bdfb5345de14b41b73abf9ae1a66941300cd22b21e24b4116d2709cc7a669419ba87b35a1e34b41aea6969dc2a669416fa70134d8e34b414eef39ddc1a66941accfe72dffe34b41259ac05ab5a6694108539b2babe44b41c7e74b1bafa66941e8139d36f1e44b413e76b01c8da669419cb2eb2fb1e54b4109dcd49a1aa66941da95672b3fe54b41d8d76fddcea5694147462636fde44b41984ce0daa9a4694195f5fc2f0de44b41d734169d78a36941dd30c73550e34b4164e4845d28a3694168849e291be34b411b1b338cdfa269418c1871e7e8e24b41"

    fun hex2arr(str: String): MutableList<Int> {

        var result = mutableListOf<Int>()
        var t = str.length
        for (i in 0 until t step 2) {
            var n = str.subSequence(i, i + 2).toString().toInt(16)
            result.add(n)
        }
        return result
    }

//    fun unpack(n: String, data: MutableList<Int>, p: Int): Unit {
//        var e = n.startsWith("<")
//
//        for (var o, r, s, l = new RegExp(this._sPattern, "g"), c = []; o = l.exec(n);) {
//            if (p + (r = null == o[1] || "" == o[1] ? 1 : parseInt(o[1])) * (s = this._lenLut[o[2]]) > a.length) return;
//            switch (o[2]) {
//                case "A":
//                case "s":
//                c.push(this._elLut[o[2]].de(a, p, r));
//                break;
//                case "c":
//                case "b":
//                case "B":
//                case "h":
//                case "H":
//                case "i":
//                case "I":
//                case "l":
//                case "L":
//                case "f":
//                case "d":
//                case "q":
//                case "Q":
//                t = this._elLut[o[2]],
//                c.push(this._UnpackSeries(r, s, a, p))
//            }
//            p += r * s
//        }
//        return Array.prototype.concat.apply([], c)
//    }
}