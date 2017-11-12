package me.command.liblowlevel;

import me.command.liblowlevel.core.MemoryHandler;
import me.command.liblowlevel.pointer.Pointer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static me.command.liblowlevel.core.Core.getCore;

@ExtendWith(TestUtils.class)
class UnitTest {

    private static MemoryHandler handler = getCore().getMemoryHandler();

    @BeforeAll
    static void hook(){
        handler.addSize(Integer.class, 16);
    }

    @TimedTest
    @DisplayName("Pointer creation (debug)")
    void pointerCreation(){
        Pointer<?> ptr = handler.allocatePointer(Integer.class, 10);
    }

    @TimedTest
    @DisplayName("Java instance creation")
    void instanceCreation(){
        Integer integer = new Integer(10);
    }


}