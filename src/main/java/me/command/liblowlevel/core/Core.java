package me.command.liblowlevel.core;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public final class Core extends UnsafeClass {

    private static Core instance;
    private MemoryUtils memoryUtils;
    private MemoryHandler memoryHandler;

    private Core(Unsafe unsafe) {
        super(unsafe);
            this.memoryUtils = new MemoryUtils(theUnsafe);
            this.memoryHandler = new MemoryHandler(theUnsafe, memoryUtils);
    }

    public static Core getCore() {
        if(instance == null) {
            try {
                Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                instance = new Core(constructor.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Unsafe class not found");
            }
        }
        return instance;

    }

    public Unsafe getUnsafe() {
        return theUnsafe;
    }
    public MemoryUtils getMemoryUtils(){ return memoryUtils; }
    public MemoryHandler getMemoryHandler(){ return memoryHandler; }

}
