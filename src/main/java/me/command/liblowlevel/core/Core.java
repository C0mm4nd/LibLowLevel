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

}
