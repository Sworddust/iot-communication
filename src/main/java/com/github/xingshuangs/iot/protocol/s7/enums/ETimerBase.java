/*
 * MIT License
 *
 * Copyright (c) 2021-2099 Oscura (xingshuang) <xingshuang_cool@163.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xingshuangs.iot.protocol.s7.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * Data area.
 * 数据的区域
 *
 * @author xingshuang
 */
public enum ETimerBase {

    /**
     * 最小粒度 10ms
     */
    T_10MS((byte) 0x00, 10),

    /**
     * 最小粒度 100ms
     */
    T_100MS((byte) 0x01, 100),

    /**
     * 最小粒度 1s
     */
    T_1S((byte) 0x02, 1000),

    /**
     * 最小粒度 10s
     */
    T_10S((byte) 0x03, 10000),

    ;

    // 静态内部类（static 内部类）实现懒加载
    private static class Holder {
        private static final Map<Byte, ETimerBase> INSTANCE = createMap();

        private static Map<Byte, ETimerBase> createMap() {
            Map<Byte, ETimerBase> map = new HashMap<>();
            for (ETimerBase item : ETimerBase.values()) {
                map.put(item.code, item);
            }
            return map;
        }
    }

    public static ETimerBase from(byte data) {
        return ETimerBase.Holder.INSTANCE.get(data);
    }

    private final byte code;

    /**
     * 时间基数，单位ms
     */
    private final int timeBase;

    ETimerBase(byte code, int timeBase) {
        this.code = code;
        this.timeBase = timeBase;
    }

    public byte getCode() {
        return code;
    }

    public int getTimeBase() {
        return timeBase;
    }
}
