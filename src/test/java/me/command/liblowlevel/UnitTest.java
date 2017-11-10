package me.command.liblowlevel;

import me.command.liblowlevel.core.Core;
import me.command.liblowlevel.core.MemoryHandler;
import me.command.liblowlevel.pointer.Pointer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static me.command.liblowlevel.TestUtils.registerHook;
import static me.command.liblowlevel.core.Core.getCore;

@ExtendWith(TestUtils.class)
class UnitTest {

    static MemoryHandler handler = getCore().getMemoryHandler();

    @BeforeAll
    public static void hook(){
        registerHook("cached", () -> handler.setCachedSizeOf(true));
        registerHook("noCached", () -> handler.setCachedSizeOf(false));
        registerHook("debug", Core.getCore()::enableDebug);
        registerHook("noDebug", Core.getCore()::disableDebug);
        handler.addSize(String.class, 24);
    }

    @Tag("debug")
    @Tag("noCached")
    @Timed
    @RepeatedTest(10)
    @DisplayName("Pointer creation (debug)")
    void pointerCreation(){
        Pointer<?> ptr = handler.allocatePointer(String.class, "Pointer creation debug");
    }

    @Tag("noDebug")
    @Tag("noCached")
    @Timed
    @RepeatedTest(10)
    @DisplayName("Pointer creation (no debug)")
    void pointerCreationNoDebug(){
        Pointer<?> ptr = handler.allocatePointer(String.class, "Pointer creation no debug");
    }

    @Tag("debug")
    @Tag("cached")
    @Timed
    @RepeatedTest(10)
    @DisplayName("Pointer creation (debug, cached size)")
    void pointerCreationCachedSize(){
        Pointer<?> ptr = handler.allocatePointer(String.class, "Pointer creation cached");
    }

    @Tag("noDebug")
    @Tag("cached")
    @Timed
    @RepeatedTest(10)
    @DisplayName("Pointer creation (no debug, cached size)")
    void pointerCreationCachedSizeNoDebug(){
        Pointer<?> ptr = handler.allocatePointer(String.class, "Pointer creation cached no debug");
    }

    @Timed
    @RepeatedTest(10)
    @DisplayName("Java instance creation")
    void instanceCreation(){
        String str = "Hello World";
    }


}