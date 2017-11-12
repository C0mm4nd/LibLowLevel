package me.command.liblowlevel;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("timed")
@RepeatedTest(10)
public @interface TimedTest {
}
