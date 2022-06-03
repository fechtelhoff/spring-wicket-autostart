package de.fechtelhoff.web;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.WebPage;
import de.fechtelhoff.web.gui.HomePage;

public final class WicketApplicationPrettyUrlConfig {

	private static final Map<String, Class<? extends WebPage>> CONFIG = new HashMap<>();

	static {
		//@formatter:off

		CONFIG.put("/home", HomePage.class);

		//@formatter:on
	}

	private WicketApplicationPrettyUrlConfig() {
		// intentionally blank
	}

	public static Map<String, Class<? extends WebPage>> getPageClassByPrettyUrlMap() {
		return CONFIG;
	}
}
