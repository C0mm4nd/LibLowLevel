package me.command.liblowlevel.pointer;

import me.command.liblowlevel.core.Core;

import java.lang.reflect.Constructor;

public class Pointers {

    public static <T extends Pointer> T cast(Pointer from, Class<T> to) {
        try {
            Constructor<T> constructor = to.getDeclaredConstructor(long.class);
            return constructor.newInstance(from.getAddress());
        } catch (Exception e) {
            return null;
        }
    }

    public static void free(Pointer toFree) {
        toFree.free();
        Core.getCore().getMemoryUtils().memorySet(
                toFree.getAddress(),
                toFree.getSize(),
                (byte)0x00
        );
    }

}
