package me.command.liblowlevel.pointer;

import me.command.liblowlevel.core.Core;
import sun.misc.Unsafe;

public abstract class Pointer<T> {

    long pointee;
    static long size = 1;
    static Unsafe theUnsafe;
    public Pointer(long pointee){
        this.pointee = pointee;
        if(theUnsafe == null){
            theUnsafe = Core.getCore().getUnsafe();
        }
    }

    public long getSize(){
        return size;
    }

    public void increment(int by){
        pointee += by*size;
    }

    public void decrement(int by){
        pointee -= by*size;
    }

    public long getAddress(){
        return pointee;
    }

    public void free(){
        theUnsafe.freeMemory(pointee);
    }

    public abstract T getPointee();

    public abstract void setPointee(T to);

}
