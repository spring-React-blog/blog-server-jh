package me.jojiapp.blogserverjh.global.cookie;

import lombok.*;
import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "server.servlet.session.cookie")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class CookieProperties {

	private final boolean secure;
	private final String domain;
	private final boolean httpOnly;

}
