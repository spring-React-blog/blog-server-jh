package me.jojiapp.blogserverjh.support.test;

import com.fasterxml.jackson.databind.*;
import me.jojiapp.blogserverjh.support.config.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.restdocs.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureRestDocs
@Import({MockMVCConfig.class, SpringRestDocsConfig.class})
@Disabled
public abstract class APITest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
}
