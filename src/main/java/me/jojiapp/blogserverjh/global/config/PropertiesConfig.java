package me.jojiapp.blogserverjh.global.config;

import me.jojiapp.blogserverjh.global.jwt.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

/**
 * application.yml에서 값을 읽어와 주입하는 클래스들을 Bean 등록하기 위한 클래스
 *
 * @see JWTProperties JWT 설정 클래스
 */
@Configuration
@EnableConfigurationProperties(value = {JWTProperties.class})
public class PropertiesConfig {
}
