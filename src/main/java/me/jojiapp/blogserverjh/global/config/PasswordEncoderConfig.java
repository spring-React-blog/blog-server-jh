package me.jojiapp.blogserverjh.global.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;

@Configuration
public class PasswordEncoderConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
