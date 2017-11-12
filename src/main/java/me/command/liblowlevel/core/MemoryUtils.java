package me.command.liblowlevel.core;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

public final class MemoryUtils extends UnsafeClass {

    MemoryUtils(Unsafe theUnsafe){
        super(theUnsafe);
    }

    public String memoryPrint(long addr, int rows) {
        StringBuilder res = new StringBuilder();
        res.append("MEMDUMP\t\t\t 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F\n");
        for(int i = 0; i<rows; i++){
            long tempAddr = (addr & 0xFFFFFFF0) + i*0x10;
            res.append(String.format("%016X ", tempAddr));
            for(int j = 0; j<16; j++){
                res.append(String.format("%02X ", theUnsafe.getByte(tempAddr + j)));
            }
            res.append("\n");
        }
        return res.toString();
    }

    public byte[] memoryDump(long addr, int size) {
        byte[] b = new byte[size];
        for(long addr2 = addr;addr2<addr+size; addr2++){
            int i = (int)(addr2-addr);
            b[i] = theUnsafe.getByte(addr2);
        }
        return b;
    }

    public void memoryMove(long src, long dst, long size) {
        memoryCopy(src, dst, size);
        memorySet(src, size, (byte)0x00);
    }

    public void memorySet(long addr, long size, byte setTo){
        theUnsafe.setMemory(addr, size, setTo);
    }

    public void memoryCopy(long src, long dst, long size){
        theUnsafe.copyMemory(src, dst, size);
    }

    public <T> long sizeOf(T o) {
        Unsafe u = theUnsafe;
        HashSet<Field> fields = new HashSet<>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }

    public long getAddress(Object obj) {
        Object[] array = new Object[] {obj};
        long baseOffset = theUnsafe.arrayBaseOffset(Object[].class);
        return theUnsafe.getLong(array, baseOffset);
    }

    public Object getObject(long address) {
        Object[] array = new Object[] {null};
        long baseOffset = theUnsafe.arrayBaseOffset(Object[].class);
        theUnsafe.putLong(array, baseOffset, address);
        return array[0];
    }

}
