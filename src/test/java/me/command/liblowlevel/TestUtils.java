package me.command.liblowlevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestUtils implements AfterAllCallback, AfterTestExecutionCallback, BeforeTestExecutionCallback, BeforeEachCallback, AfterEachCallback {

    private static final String START_TIME = "start_time";
    private Map<Method, List<Long>> timings = new HashMap<>();
    private static Map<String, Function<ExtensionContext, Void>> hooks = new HashMap<>();

    boolean timedTest = false;

    public static void registerHook(String tag, Runnable hook){
        registerHook(tag, new Function<ExtensionContext, Void>() {
            Runnable hookInternal = hook;
            @Override
            public Void apply(ExtensionContext extensionContext) {
                hookInternal.run();
                return null;
            }
        });
    }

    public static void registerHook(String tag, Function<ExtensionContext, Void> hook) {
        hooks.putIfAbsent(tag, hook);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("Executing test [%s, %s]\n",
                methodNameOrTestName(extensionContext.getRequiredTestMethod()),
                extensionContext.getDisplayName());
        doIfCondition(hasTag(extensionContext, "timed"),
                () -> System.out.println("Starting timer...\n"));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        for(String tag:hooks.keySet()){
            doIfCondition(hasTag(context, tag), hooks.get(tag), context);
        }
        doIfCondition(hasTag(context, "timed"), () -> {
            timings.putIfAbsent(context.getRequiredTestMethod(), new ArrayList<>());
            getStore(context).put(START_TIME, System.nanoTime());
        });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        doIfCondition(hasTag(context, "timed"), () -> {
            Method testMethod = context.getRequiredTestMethod();
            long startTime = getStore(context).remove(START_TIME, long.class);
            long duration = System.nanoTime() - startTime;
            System.out.printf("\nMethod [%s] took %f ms.\n", testMethod.getName(), duration / 1000000.0D);
            timings.get(context.getRequiredTestMethod()).add(duration);
        });
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.printf("Test [%s, %s] finished\n",
                methodNameOrTestName(extensionContext.getRequiredTestMethod()),
                extensionContext.getDisplayName());
        System.out.println("--------------------------------------------------------------------\n\n\n");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("------------------------------Results-------------------------------");
        for(Method m:timings.keySet()){
            List<Long> timingsList = timings.get(m);
            long res = 0;
            for(long l:timingsList){
                res += l;
            }
            res /= 100;
            System.out.printf("Method [%s] average: %f ms.\n", methodNameOrTestName(m), res/1000000.0D);
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

    private String methodNameOrTestName(Method method){
        DisplayName annotation = method.getAnnotation(DisplayName.class);
        if(annotation != null){
            return annotation.value();
        } else {
            return method.getName();
        }

    }

    private boolean hasTag(ExtensionContext ec, String tag){
        return ec.getTags().contains(tag);
    }

    private void doIfCondition(boolean condition,
                               Function<ExtensionContext, Void> run,
                               ExtensionContext ctx){
        if(condition) run.apply(ctx);
    }

    private void doIfCondition(boolean condition, Runnable run){
        if(condition) run.run();
    }
}
