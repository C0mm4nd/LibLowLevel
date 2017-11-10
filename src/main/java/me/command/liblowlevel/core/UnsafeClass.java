package me.command.liblowlevel.core;

import sun.misc.Unsafe;

public abstract class UnsafeClass {

    protected Unsafe theUnsafe;

    public UnsafeClass(Unsafe theUnsafe){
        this.theUnsafe = theUnsafe;
    }

}
