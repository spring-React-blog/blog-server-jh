package me.jojiapp.blogserverjh.global.config;

import me.jojiapp.blogserverjh.global.cookie.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties(value = {JWTProperties.class, CookieProperties.class})
public class PropertiesConfig {
}
