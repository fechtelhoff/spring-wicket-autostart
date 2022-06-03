package de.fechtelhoff.web;

import org.apache.wicket.protocol.http.WebApplication;
import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.CookieThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;

@ApplicationInitExtension
public class WicketApplicationConfig implements WicketApplicationInitConfiguration {

	@Override
	public void init(final WebApplication webApplication) {
		initializeBootstrap(webApplication);
		mountPages(webApplication);
	}

	private void initializeBootstrap(final WebApplication webApplication) {
		final IBootstrapSettings bootstrapSettings = new BootstrapSettings();
		final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Cerulean);
		bootstrapSettings.setThemeProvider(themeProvider);
		bootstrapSettings.setActiveThemeProvider(new CookieThemeProvider());
		Bootstrap.install(webApplication, bootstrapSettings);
	}

	private void mountPages(final WebApplication webApplication) {
		WicketApplicationPrettyUrlConfig.getPageClassByPrettyUrlMap().forEach(webApplication::mountPage);
	}
}
