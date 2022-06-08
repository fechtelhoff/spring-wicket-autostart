package de.fechtelhoff;

import java.io.IOException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

@Component
@Profile("!test")
public class BrowserLauncher {

	private static final String URL = "http://localhost:8080";

	@EventListener(ApplicationReadyEvent.class)
	@SuppressWarnings("java:S2142") // java:S2142 -> "InterruptedException" should not be ignored
	public void launchBrowser() {
		System.setProperty("java.awt.headless", "false");
/*
		final Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(URL));
		} catch (final Exception exception) {
			// Intentionally blank
		}
*/
		final boolean useOsr = false;
		final boolean isTransparent = false;
		final String[] args = {""};
		try {
			new MainFrame(URL, useOsr, isTransparent, args);
		} catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException exception) {
			throw new IllegalStateException("Unhandled exception occurred.", exception);
		}
	}
}
