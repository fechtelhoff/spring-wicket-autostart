package de.fechtelhoff;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

@Component
@Profile("!test")
public class BrowserLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrowserLauncher.class);

	private final ServletWebServerApplicationContext webServerApplicationContext;

	@Autowired
	public BrowserLauncher(final ServletWebServerApplicationContext webServerApplicationContext) {
		this.webServerApplicationContext = webServerApplicationContext;
	}

	@EventListener(ApplicationReadyEvent.class)
	@SuppressWarnings("java:S2142") // java:S2142 -> "InterruptedException" should not be ignored
	public void launchBrowser() {
		System.setProperty("java.awt.headless", "false");
		final boolean useOsr = false;
		final boolean isTransparent = false;
		try {
			final String url = "http://localhost:" + webServerApplicationContext.getWebServer().getPort();
			LOGGER.info("URL: {}", url);
			new MainFrame(url, useOsr, isTransparent);
		} catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException exception) {
			throw new IllegalStateException("Unhandled exception occurred.", exception);
		}
	}
}
