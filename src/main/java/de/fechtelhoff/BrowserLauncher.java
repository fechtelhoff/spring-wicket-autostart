package de.fechtelhoff;

import java.awt.Desktop;
import java.net.URI;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class BrowserLauncher {

	private static final String URL = "http://localhost:8080";

	@EventListener(ApplicationReadyEvent.class)
	public void launchBrowser() {
		System.setProperty("java.awt.headless", "false");
		final Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(URL));
		} catch (final Exception exception) {
			// Intentionally blank
		}
	}
}
