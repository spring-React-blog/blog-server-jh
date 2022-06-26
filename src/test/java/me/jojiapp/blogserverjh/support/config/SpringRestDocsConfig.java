package me.jojiapp.blogserverjh.support.config;

import org.springframework.boot.test.autoconfigure.restdocs.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@TestConfiguration
public class SpringRestDocsConfig {

	@Bean
	public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
		return (it) -> {
			it.operationPreprocessors()
				.withRequestDefaults(prettyPrint())
				.withResponseDefaults(prettyPrint());
		};
	}
}
