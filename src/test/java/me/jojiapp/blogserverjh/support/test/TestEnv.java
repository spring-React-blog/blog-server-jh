package me.jojiapp.blogserverjh.support.test;

import org.springframework.test.context.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles(profiles = {"test"})
public @interface TestEnv {
}
