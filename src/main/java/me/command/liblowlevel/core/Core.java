package me.command.liblowlevel.core;

import me.command.liblowlevel.pointer.*;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public class Core {

    private static Core instance = new Core();
    private Unsafe myUnsafe;
    private MemoryUtils memoryUtils;
    private MemoryHandler memoryHandler;
    private boolean debug = false;

    private Core() {
        try {
            Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            this.myUnsafe = constructor.newInstance();
            this.memoryUtils = new MemoryUtils(myUnsafe);
            this.memoryHandler = new MemoryHandler(myUnsafe, memoryUtils);
        } catch (Exception e) {

            e.printStackTrace();
            throw new IllegalStateException("Unsafe class not found");
        }
    }

    public void enableDebug(){
        this.debug = true;
        updateDebug();
    }

    public void disableDebug(){
        this.debug = false;
        updateDebug();
    }

    private void updateDebug(){
        memoryHandler.updateDebug();
    }

    public boolean shouldDebug(){
        return this.debug;
    }

    public static Core getCore(){
        return instance;
    }

    public Unsafe getUnsafe() {
        return myUnsafe;
    }
    public MemoryUtils getMemoryUtils(){ return memoryUtils; }
    public MemoryHandler getMemoryHandler(){ return memoryHandler; }

    public static void main(String... args){
        getCore().enableDebug();
        long time = System.nanoTime();
        ClassPointer<?> ptr = getCore().getMemoryHandler().allocatePointer(String.class, "Hello World bby");
        long time2 = System.nanoTime();
        System.out.printf("\nSeconds for a pointer instance: %fs\n", ((time2-time)/1000000000.0D));
        time = System.nanoTime();
        String s = new String("Hello World bby");
        time2 = System.nanoTime();
        System.out.printf("Seconds for a java instance: %fs\n", ((time2-time)/1000000000.0D));

        System.out.format("\n\nMy pointer address: 0x%016X\n", ptr.getAddress());
        System.out.format("Random object addr: 0x%016X\n", Core.getCore().memoryUtils.getAddress(new Object()));

        Pointer p = getCore().getMemoryHandler().allocMemory(1);
        IntPointer intPointer = Pointers.cast(p, IntPointer.class);
        for(int i = 0; i<10; i++){
            System.out.printf("Pointee #%d: %08X\n", i, p.getPointee());
            p.increment(1);
        }
        BytePointer bytePointer = Pointers.cast(intPointer, BytePointer.class);
        for(int i = 0; i<10; i++){
            System.out.printf("Pointee #%d: %02X\n", i, p.getPointee());
            p.decrement(1);
        }
    }
}
