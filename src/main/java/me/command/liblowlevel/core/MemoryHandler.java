package me.command.liblowlevel.core;

import me.command.liblowlevel.pointer.BytePointer;
import me.command.liblowlevel.pointer.ClassPointer;
import me.command.liblowlevel.pointer.Pointer;
import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.Map;


public class MemoryHandler extends UnsafeClass{

    private MemoryUtils memUtils;
    private boolean shouldDebug = false;
    private boolean cachedSizeOf = true;
    private Map<Class<?>, Long> sizes;

    public MemoryHandler(Unsafe theUnsafe, MemoryUtils utils) {
        super(theUnsafe);
        this.memUtils = utils;
        this.sizes = new HashMap<>();
    }

    public void addSize(Class<?> object, long size){
        this.sizes.putIfAbsent(object, size);
    }

    public void setCachedSizeOf(boolean cachedSizeOf){
        this.cachedSizeOf = cachedSizeOf;
    }

    protected void updateDebug(){
        this.shouldDebug = Core.getCore().shouldDebug();
    }

    public Pointer<?> allocMemory(long size) {
        return new BytePointer(theUnsafe.allocateMemory(size));
    }

    void freePointer(Pointer ptr){
        theUnsafe.freeMemory(ptr.getAddress());

    }

    private void replaceClassToRClass(Class<?>[] arr){
        for (int i = 0; i < arr.length; i++) {
            Class<?> klass = arr[i];
            if(klass.equals(Boolean.class))
                arr[i] = boolean.class;
            else if(klass.equals(Byte.class))
                arr[i] = byte.class;
            else if(klass.equals(Character.class))
                arr[i] = char.class;
            else if(klass.equals(Short.class))
                arr[i] = short.class;
            else if(klass.equals(Integer.class))
                arr[i] = int.class;
            else if(klass.equals(Float.class))
                arr[i] = float.class;
            else if(klass.equals(Long.class))
                arr[i] = long.class;
            else if(klass.equals(Double.class))
                arr[i] = double.class;
        }
    }

    public <T> ClassPointer<T> allocatePointer(Class<T> type, Object... argsObjectsArray) throws RuntimeException{
        try {
            Class<?>[] argsClassesArray = new Class[argsObjectsArray.length];
            for(int i = 0; i < argsObjectsArray.length; i++)
                argsClassesArray[i] = argsObjectsArray[i].getClass();
            replaceClassToRClass(argsClassesArray);
            Object tempInst2 = theUnsafe.allocateInstance(type);
            long sizeOf;
            if(cachedSizeOf) {
                if (sizes.containsKey(type)) {
                    sizeOf = sizes.get(type);
                } else {
                    sizeOf = memUtils.sizeOf(tempInst2);
                    sizes.put(type, sizeOf);
                }
            } else {
                sizeOf = memUtils.sizeOf(tempInst2);
            }
            memUtils.memoryMove(memUtils.getAddress(type.getConstructor(argsClassesArray)
                                    .newInstance(argsObjectsArray)),
                    memUtils.getAddress(tempInst2),
                    memUtils.sizeOf(tempInst2));
            if(shouldDebug) {
                System.out.println("Size of " + type.getName() + ": " + memUtils.sizeOf(tempInst2) + " bytes");
                memUtils.memoryPrint(memUtils.getAddress(tempInst2), 2);
            }
            return new ClassPointer<>(memUtils.getAddress(tempInst2));


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Can't instantiate the pointee");
        }
    }


}
