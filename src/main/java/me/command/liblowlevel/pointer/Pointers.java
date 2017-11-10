package me.command.liblowlevel.pointer;

import java.lang.reflect.Constructor;

public class Pointers {

    public static <T extends Pointer> T cast(Pointer from, Class<T> to){
        try {
            Constructor<T> constructor = to.getDeclaredConstructor(long.class);
            return constructor.newInstance(from.getAddress());
        } catch (Exception e) {
            return null;
        }
    }

}
