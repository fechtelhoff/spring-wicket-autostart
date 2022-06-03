package de.fechtelhoff;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringWicketAutostartApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
			.sources(SpringWicketAutostartApplication.class)
			.run(args);
	}
}
