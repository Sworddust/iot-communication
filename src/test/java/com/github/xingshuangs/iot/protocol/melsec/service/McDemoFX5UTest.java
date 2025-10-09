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

package com.github.xingshuangs.iot.protocol.melsec.service;


import com.github.xingshuangs.iot.protocol.melsec.enums.EMcSeries;
import com.github.xingshuangs.iot.utils.HexUtil;

/**
 * @author xingshuang
 */
public class McDemoFX5UTest {

    public static void main(String[] args) {
        McPLC mcPLC = new McPLC(EMcSeries.Q_L, "127.0.0.1", 6000);

        // optional
        mcPLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));

        // read and write SM
        mcPLC.writeBoolean("SM21", true);
        boolean sm = mcPLC.readBoolean("SM21");

        // read and write SD
        mcPLC.writeUInt16("SD13", 12345);
        int sd = mcPLC.readUInt16("SD13");

        // read and write X
        mcPLC.writeBoolean("XFF", true);
        boolean x = mcPLC.readBoolean("XFF");

        // read and write Y
        mcPLC.writeBoolean("YFF", true);
        boolean y = mcPLC.readBoolean("YFF");

        // read and write M
        mcPLC.writeBoolean("M22", true);
        boolean m = mcPLC.readBoolean("M22");

        // read and write L
        mcPLC.writeBoolean("L22", true);
        boolean l = mcPLC.readBoolean("L22");

        // read and write F
        mcPLC.writeBoolean("F22", true);
        boolean f = mcPLC.readBoolean("F22");

        // read and write B
        mcPLC.writeBoolean("B2F", true);
        boolean b = mcPLC.readBoolean("B2F");

        // read and write D
        mcPLC.writeUInt16("D13", 12345);
        int d = mcPLC.readUInt16("D13");

        // read and write W
        mcPLC.writeUInt16("W2F", 12345);
        int w = mcPLC.readUInt16("W2F");

        // read and write T
        mcPLC.writeBoolean("TS3", true);
        boolean ts = mcPLC.readBoolean("TS3");
        mcPLC.writeBoolean("TC3", true);
        boolean tc = mcPLC.readBoolean("TC3");
        mcPLC.writeUInt16("TN3", 15);
        int tn = mcPLC.readUInt16("TN3");

        // read and write ST
        mcPLC.writeBoolean("STS3", true);
        boolean sts = mcPLC.readBoolean("STS3");
        mcPLC.writeBoolean("STC3", true);
        boolean stc = mcPLC.readBoolean("STC3");
        mcPLC.writeUInt16("STN3", 15);
        int stn = mcPLC.readUInt16("STN3");

        // read and write C
        mcPLC.writeBoolean("CS3", true);
        boolean cs = mcPLC.readBoolean("CS3");
        mcPLC.writeBoolean("CC3", true);
        boolean cc = mcPLC.readBoolean("CC3");
        mcPLC.writeUInt16("CN3", 15);
        int cn = mcPLC.readUInt16("CN3");

        // read and write SB
        mcPLC.writeBoolean("SB3F", true);
        boolean sb = mcPLC.readBoolean("SB3F");

        // read and write SW
        mcPLC.writeUInt16("SW2F", 12345);
        int sw = mcPLC.readUInt16("SW2F");

        // read and write Z
        mcPLC.writeUInt16("Z10", 12345);
        int z = mcPLC.readUInt16("Z10");

        // read and write R
        mcPLC.writeUInt16("R10", 12345);
        int r = mcPLC.readUInt16("R10");

        mcPLC.close();
    }
}
