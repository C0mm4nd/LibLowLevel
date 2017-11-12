package me.command.liblowlevel;

import me.command.liblowlevel.core.MemoryHandler;
import me.command.liblowlevel.pointer.Pointer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static me.command.liblowlevel.TestUtils.registerHook;
import static me.command.liblowlevel.core.Core.getCore;

@ExtendWith(TestUtils.class)
class UnitTest {

    private static MemoryHandler handler = getCore().getMemoryHandler();

    @BeforeAll
    static void hook(){
        registerHook("cached", () -> handler.setCachedSizeOf(true));
        registerHook("noCached", () -> handler.setCachedSizeOf(false));
        registerHook("debug", getCore()::enableDebug);
        handler.addSize(Integer.class, 16);
    }

    @Tag("debug")
    @Tag("noCached")
    @TimedTest
    @DisplayName("Pointer creation (debug)")
    void pointerCreation(){
        Pointer<?> ptr = handler.allocatePointer(Integer.class, 10);
    }

    @Tag("noDebug")
    @Tag("noCached")
    @TimedTest
    @DisplayName("Pointer creation (no debug)")
    void pointerCreationNoDebug(){
        Pointer<?> ptr = handler.allocatePointer(Integer.class, 10);
    }

    @Tag("debug")
    @Tag("cached")
    @TimedTest
    @DisplayName("Pointer creation (debug, cached size)")
    void pointerCreationCachedSize(){
        Pointer<?> ptr = handler.allocatePointer(Integer.class, 10);
    }

    @Tag("noDebug")
    @Tag("cached")
    @TimedTest
    @DisplayName("Pointer creation (no debug, cached size)")
    void pointerCreationCachedSizeNoDebug(){
        Pointer<?> ptr = handler.allocatePointer(Integer.class, 10);
    }

    @TimedTest
    @DisplayName("Java instance creation")
    void instanceCreation(){
        Integer integer = new Integer(10);
    }


}