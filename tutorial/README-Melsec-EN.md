# MITSUBISHI MELSEC(MC) Protocol Tutorial

[HOME BACK](../README.md)

## Foreword

> Tips

- Device is divided into bit device and word device.
- **1** device address = **2** bytes.
- The small-end mode is adopted, the encoding format of **4 bytes** data = **AB_CD**. (big-endian mode = **DC_BA**
  ，little-endian mode = **AB_CD**)
- Only read and write in binary is supported. Read and write in ASCII is not supported
- Support TCP mode, serial ports are not supported
- Support PLC: iQ-R series, Q/L series, QnA series, A series, the L Series(L02), FX Series(FX5U-32M), R Series(R08) has been
  tested
- Support automatic reconnection.

> PLC series

| Series | Series Parameter | Frame Type | Connect  | Model |
|:------:|------------------|:----------:|----------|:-----:|
|  FX3   | A                |     1E     | Ethernet | FX3U  |
|   L    | QnA/Q_L          |     3E     | Ethernet |   L   |
|   Q    | QnA/Q_L          |   3E/4E    | Ethernet |   Q   |
|  iQ-F  | Q_L              |     3E     | Ethernet | FX5U  |
|  iQ-L  | iQ-R             |     3E     | Ethernet |   -   |
|  iQ-R  | iQ-R             |   3E/4E    | Ethernet |   R   |

> Address format, case compatible.

1.The addresses of the devices in this table are represented in decimal.

|  Abbr  | Device Name                        | Symbol | Address |   Type    | Notation | FX5U |
|:------:|:-----------------------------------|:------:|:-------:|:---------:|:---------|------|
|  SM10  | Special relay                      |   SM   |   10    |    BIT    | 10       | ✅    |
|  SD12  | Special register                   |   SD   |   12    |   WORD    | 10       | ✅    |
|  M100  | Internal relay                     |   M    |   100   |    BIT    | 10       | ✅    |
|  L10   | Latch relay                        |   L    |   10    |    BIT    | 10       | ✅    |
|  F10   | Annunciator                        |   F    |   10    |    BIT    | 10       | ✅    |
|   V9   | Edge relay                         |   V    |    9    |    BIT    | 10       | ❌    |
|  D100  | Data register                      |   D    |   100   |   WORD    | 10       | ✅    |
|  TS10  | Timer Contact                      |   TS   |   10    |    BIT    | 10       | ✅    |
|  TC10  | Timer Coil                         |   TC   |   10    |    BIT    | 10       | ✅    |
|  TN10  | Timer Current value                |   TN   |   10    |   WORD    | 10       | ✅    |
| LTS10  | Long timer Contact                 |  LTS   |   10    |    BIT    | 10       | ❌    |
| LTC10  | Long timer Coil                    |  LTC   |   10    |    BIT    | 10       | ❌    |
| LTN10  | Long timer Current value           |  LTN   |   10    | **DWORD** | 10       | ❌    |
| STS10  | Retentive timer Contact            |  STS   |   10    |    BIT    | 10       | ✅    |
| STC10  | Retentive timer Coil               |  STC   |   10    |    BIT    | 10       | ✅    |
| STN10  | Retentive timer Current value      |  STN   |   10    |   WORD    | 10       | ✅    |
| LSTS10 | Long retentive timer Contact       |  LSTS  |   10    |    BIT    | 10       | ❌    |
| LSTC10 | Long retentive timer Coil          |  LSTC  |   10    |    BIT    | 10       | ❌    |
| LSTN10 | Long retentive timer Current value |  LSTN  |   10    | **DWORD** | 10       | ❌    |
|  CS10  | Counter Contact                    |   CS   |   10    |    BIT    | 10       | ✅    |
|  CC10  | Counter Coil                       |   CC   |   10    |    BIT    | 10       | ✅    |
|  CN10  | Counter Current value              |   CN   |   10    |   WORD    | 10       | ✅    |
| LCS10  | Long counter Contact               |  LCS   |   10    |    BIT    | 10       | ❌    |
| LCC10  | Long counter Coil                  |  LCC   |   10    |    BIT    | 10       | ❌    |
| LCN10  | Long counter Current value         |  LCN   |   10    | **DWORD** | 10       | ❌    |
|  Z10   | Index register                     |   Z    |   10    |   WORD    | 10       | ✅    |
|  LZ10  | Long index register                |   LZ   |   10    | **DWORD** | 10       | ✅    |
|  R10   | Block switching method             |   R    |   10    |   WORD    | 10       | ✅    |
|  RD10  | Refresh data register              |   RD   |   10    |   WORD    | 10       | ❌    |

2.The addresses of the devices in this table are represented in hexadecimal. The address `X1F` corresponds to the `31` bit of the `X` device.

| Abbr  | Device Name                 | Symbol | Address | Type | Notation | FX5U |
|:-----:|:----------------------------|:------:|:-------:|:----:|:---------|------|
|  X2F  | Input                       |   X    |  0x2F   | BIT  | **16**   | ✅    |
| Y12F  | Output                      |   Y    |  0x12F  | BIT  | **16**   | ✅    |
|  B2F  | Link relay                  |   B    |  0x2F   | BIT  | **16**   | ✅    |
|  W1F  | Link register               |   W    |  0x1F   | WORD | **16**   | ✅    |
| SB1F  | Link special relay          |   SB   |  0x1F   | BIT  | **16**   | ✅    |
| SW1F  | Link special register       |   SW   |  0x1F   | WORD | **16**   | ✅    |
| DX2F  | Direct access input         |   DX   |  0x2F   | BIT  | **16**   | ❌    |
| DY12F | Direct access output        |   DY   |  0x12F  | BIT  | **16**   | ❌    |
| ZR1C  | Serial number access method |   ZR   |  0x1C   | WORD | **16**   | ❌    |

> Shortcut access interface

| number | Method       | Device Count | Size in Byte | Size in Bit |
|:------:|:-------------|:------------:|:------------:|:-----------:|
|   1    | readBoolean  |      1       |     1/8      |      1      |
|   2    | readInt16    |      1       |      2       |     16      |
|   3    | readUInt16   |      1       |      2       |     16      |
|   4    | readInt32    |      2       |      4       |     32      |
|   5    | readUInt32   |      2       |      4       |     32      |
|   6    | readFloat32  |      2       |      4       |     32      |
|   7    | readFloat64  |      4       |      8       |     64      |
|   8    | readString   |      n       |      2n      |     16n     |
|   9    | readBoolean  |      1       |     1/8      |      1      |
|   10   | writeInt16   |      1       |      2       |     16      |
|   11   | writeUInt16  |      1       |      2       |     16      |
|   12   | writeInt32   |      2       |      4       |     32      |
|   13   | writeUInt32  |      2       |      4       |     32      |
|   14   | writeFloat32 |      2       |      4       |     32      |
|   15   | writeFloat64 |      4       |      8       |     64      |
|   16   | writeString  |      n       |      2n      |     16n     |

## Print Message

If you want to know the actual input and output of packets during communication, you can print packet information by
yourself.

```java
class Demo {
    public static void main(String[] args) {
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);
        // optional
        mcPLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));
        // read boolean
        boolean booleanData = mcPLC.readBoolean("M100");
        mcPLC.close();
    }
}
```

## Communication Connection

- By default, the long connection mode is adopted. You need to close connection manually when it is not in use.
- If a short connection is required, you need to set it manually.

### 1. Long Connection Mode

```java
class Demo {
    public static void main(String[] args) {
        // long connection mode, persistence = true
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);
        boolean booleanData = mcPLC.readBoolean("M100");
        // close it manually
        plc.close();
    }
}
```

### 2. Short Connection Mode

```java
class Demo {
    public static void main(String[] args) {
        // short connection mode
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);
        // set short connection mode, persistence = false
        mcPLC.setPersistence(false);
        boolean booleanData = mcPLC.readBoolean("M100");
    }
}
```

## Read and Write in General

### 1. Read Data

```java
class Demo {
    public static void main(String[] args) {
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);

        // optional
        mcPLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));

        // read boolean
        boolean booleanData = mcPLC.readBoolean("M100");

        // read boolean list
        List<Boolean> booleanList = mcPLC.readBoolean("M100", 10);

        // read one byte
        byte byteData = mcPLC.readByte("D100");

        // read multi byte
        byte[] bytes = mcPLC.readBytes("D100", 10);

        // read int16
        short int16Data = mcPLC.readInt16("D100");

        // read int16 list
        List<Integer> int16List = mcPLC.readUInt16("D100", "D108", "D130");

        // read uint16
        int uint16Data = mcPLC.readUInt16("D100");

        // read uint16 list
        List<Integer> uint16List = mcPLC.readUInt16("D100", "D108", "D130");

        // read int32
        int int32Data = mcPLC.readInt32("D100");

        // read int32 list
        List<Integer> int32List = mcPLC.readInt32("D100", "D108", "D130");

        // read uint32
        long uint32Data = mcPLC.readUInt32("D100");

        // read uint32 list
        List<Long> uint32List = mcPLC.readUInt32("D100", "D108", "D130");

        // read float32
        float float32Data = mcPLC.readFloat32("D100");

        // read float32 list
        List<Float> float32List = mcPLC.readFloat32("D100", "D108", "D130");

        // read float64
        double float64Data = mcPLC.readFloat64("D100");

        // read string
        String stringData = mcPLC.readString("D100", 6);

        // read multi address, only support word and dword
        McMultiAddressRead addressRead = new McMultiAddressRead();
        addressRead.addWordData("D100");
        addressRead.addWordData("D150");
        addressRead.addWordData("D130");
        addressRead.addWordData("D110");
        addressRead.addDWordData("D112");
        addressRead.addDWordData("D116");
        addressRead.addDWordData("D126");
        List<McDeviceContent> contentList = mcPLC.readMultiAddress(addressRead);

        mcPLC.close();
    }
}
```

### 2. Write Data

```java
class Demo {
    public static void main(String[] args) {
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);

        // optional
        mcPLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));

        // write one boolean
        mcPLC.writeBoolean("M100", true);

        // write multi boolean
        mcPLC.writeBoolean("M100", true, false, true);

        // write one byte
        mcPLC.writeByte("D100", (byte) 0x01);

        // write multi byte
        mcPLC.writeBytes("D100", new byte[]{0x01, 0x02, 0x03});

        // write one int16
        mcPLC.writeInt16("D100", (short) 16);

        // write multi int16
        mcPLC.writeInt16("D100", (short) 16, (short) 17, (short) 118);

        // write one uint16
        mcPLC.writeUInt16("D100", 16);

        // write multi uint16
        mcPLC.writeUInt16("D100", 16, 17, 18);

        // write one int32
        mcPLC.writeInt32("D100", 55);

        // write multi int32
        mcPLC.writeInt32("D100", 55, 66, 77);

        // write one uint32
        mcPLC.writeUInt32("D100", 55);

        // write multi uint32
        mcPLC.writeUInt32("D100", 55L, 66L, 77L);

        // write one float32
        mcPLC.writeFloat32("D100", 0.123f);

        // write multi float32
        mcPLC.writeFloat32("D100", 0.123f, 145.56f, 88.12f);

        // write one float64
        mcPLC.writeFloat64("D100", 0.123);

        // write multi float64
        mcPLC.writeFloat64("D100", 0.123, 145.56, 88.12);

        // write string
        mcPLC.writeString("D100", "1234567890");

        // multi write, only support int16 uint16 int32 uint32 float32
        McMultiAddressWrite addressWrite = new McMultiAddressWrite();
        addressWrite.addInt16("D100", 110);
        addressWrite.addUInt16("D101", 110);
        addressWrite.addInt32("D102", 110);
        addressWrite.addUInt32("D104", 110);
        addressWrite.addFloat32("D106", 110);
        mcPLC.writeMultiAddress(addressWrite);

        mcPLC.close();
    }
}
```

## Read and Write in Custom

```java
class Demo {
    public static void main(String[] args) {
        McPLC mcPLC = new McPLC(EMcSeries.QnA, "127.0.0.1", 6000);

        // optional
        mcPLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));

        // ------------------ read and write device batch in word ------------------------
        byte[] expect = new byte[]{0x34, 0x12, 0x02, 0x00};
        McDeviceContent reqContent = McDeviceContent.createBy("D110", 2, expect);
        mcPLC.writeDeviceBatchInWord(reqContent);
        McDeviceAddress address = McDeviceAddress.createBy("D110", 2);
        McDeviceContent ackContent = mcPLC.readDeviceBatchInWord(address);

        // ------------------ read and write device batch in bit --------------------------
        expect = new byte[]{0x11, 0x00, 0x01, 0x10};
        reqContent = McDeviceContent.createBy("M110", 8, expect);
        mcPLC.writeDeviceBatchInBit(reqContent);
        address = McDeviceAddress.createBy("M110", 8);
        ackContent = mcPLC.readDeviceBatchInBit(address);

        // ------------------ read and write device random in word ------------------------
        List<McDeviceContent> writeWord = new ArrayList<>();
        writeWord.add(McDeviceContent.createBy("D110", new byte[]{0x50, 0x05}));
        writeWord.add(McDeviceContent.createBy("D111", new byte[]{0x75, 0x05}));
        writeWord.add(McDeviceContent.createBy("M110", new byte[]{0x40, 0x05}));
        List<McDeviceContent> writeDWord = new ArrayList<>();
        writeDWord.add(McDeviceContent.createBy("D120", new byte[]{0x02, 0x12, 0x39, 0x04}));
        writeDWord.add(McDeviceContent.createBy("M130", new byte[]{0x75, 0x04, 0x25, 0x04}));
        mcPLC.writeDeviceRandomInWord(writeWord, writeDWord);

        List<McDeviceAddress> readWord = new ArrayList<>();
        readWord.add(McDeviceAddress.createBy("D110"));
        readWord.add(McDeviceAddress.createBy("D111"));
        readWord.add(McDeviceAddress.createBy("M110"));
        List<McDeviceAddress> readDWord = new ArrayList<>();
        readDWord.add(McDeviceAddress.createBy("D120"));
        readDWord.add(McDeviceAddress.createBy("M130"));
        List<McDeviceContent> mcDeviceContents = mcPLC.readDeviceRandomInWord(readWord, readDWord);

        // ------------------------ write device random in bit ---------------------------
        List<McDeviceContent> contents = new ArrayList<>();
        contents.add(McDeviceContent.createBy("M110", new byte[]{0x01}));
        contents.add(McDeviceContent.createBy("M112", new byte[]{0x01}));
        contents.add(McDeviceContent.createBy("M113", new byte[]{0x01}));
        mcPLC.writeDeviceRandomInBit(contents);

        // ---------- read and write device multi blocks (test failed, not get reason) ----
        List<McDeviceContent> wordContents = new ArrayList<>();
        wordContents.add(McDeviceContent.createBy("D110", 2, new byte[]{0x01, 0x02, 0x03, 0x04}));
        wordContents.add(McDeviceContent.createBy("D0", 1, new byte[]{0x08, 0x07}));
        List<McDeviceContent> bitContents = new ArrayList<>();
        bitContents.add(McDeviceContent.createBy("M110", 1, new byte[]{0x03, 0x04}));
        mcPLC.writeDeviceBatchMultiBlocks(wordContents, bitContents);

        List<McDeviceAddress> wordAddresses = new ArrayList<>();
        wordAddresses.add(McDeviceAddress.createBy("D110", 2));
        wordAddresses.add(McDeviceAddress.createBy("D114", 1));
        List<McDeviceAddress> bitAddresses = new ArrayList<>();
        bitAddresses.add(McDeviceAddress.createBy("M110", 1));
        mcDeviceContents = mcPLC.readDeviceBatchMultiBlocks(wordAddresses, bitAddresses);

        mcPLC.close();
    }
}
```

## PLC

### 1. FX5U demo

```java
class Demo {
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
```

## Q&A

> Settings of accessing ZR device in the R series PLC

- For Mitsubishi R series PLC, open GXworks3 (Mitsubishi Software for making changes to its PLC). Go to Module Parameter
  -> make Enable/Disable
  Online Change to "Enable All(SLMP)" -> Click on Apply -> Go to Online menu -> Click on Write to PLC -> Power off and
  then restart
  the R series PLC.
- ZR uses hexadecimal as the addresses. For example:- mcPlc.writeInt16("ZR100", 4852) -> the value will be written to "
  ZR256" (256 is decimal)