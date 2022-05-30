package me.jojiapp.blogserverjh.global.config;

import com.querydsl.jpa.impl.*;
import lombok.*;
import org.springframework.context.annotation.*;

import javax.persistence.*;

@Configuration
@RequiredArgsConstructor
public class JPAQueryDSLConfig {

	private final EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}
