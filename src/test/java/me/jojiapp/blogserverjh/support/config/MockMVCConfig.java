package me.jojiapp.blogserverjh.support.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.restdocs.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;
import org.springframework.web.filter.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@TestConfiguration
public class MockMVCConfig {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private RestDocumentationContextProvider documentationContextProvider;

	@Bean
	public MockMvc mockMvc() {
		return MockMvcBuilders.webAppContextSetup(context)
				.apply(
						documentationConfiguration(documentationContextProvider)
								.operationPreprocessors()
								.withRequestDefaults(prettyPrint())
								.withResponseDefaults(prettyPrint())
				)
				.apply(springSecurity())
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
	}
}
