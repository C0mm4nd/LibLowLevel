package me.command.liblowlevel.pointer;

import me.command.liblowlevel.core.Core;
import me.command.liblowlevel.core.MemoryUtils;

public class ClassPointer<T> extends Pointer<T> {

    public ClassPointer(long pointee){
        super(pointee);
    }

    @Override
    public T getPointee(){
        return (T)Core.getCore().getMemoryUtils().getObject(pointee);
    }

    @Override
    public void setPointee(T to) {
        MemoryUtils memUtils = Core.getCore().getMemoryUtils();
        long from = memUtils.getAddress(to);
        long size = memUtils.sizeOf(to);
        memUtils.memoryMove(from, pointee, size);
    }

    @Override
    @Deprecated
    public void increment(int by){}

    @Override
    @Deprecated
    public void decrement(int by){}
}
