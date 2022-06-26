package me.jojiapp.blogserverjh.support.test;

import me.jojiapp.blogserverjh.global.config.*;
import me.jojiapp.blogserverjh.support.config.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(
	includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {
			JPAQueryDSLConfig.class,
			JPAAuditingConfig.class,
			TestAuditorAware.class
		}
	)
)
@TestEnv
public @interface RepoTest {
}
