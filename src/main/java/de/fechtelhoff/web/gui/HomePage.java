package de.fechtelhoff.web.gui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.fechtelhoff.web.gui.panel.WicketSettingsPanel;

@WicketHomePage
// java:S106 -> Standard outputs should not be used directly to log anything
// java:S110 -> Inheritance tree of classes should not be too deep
@SuppressWarnings({"java:S106", "java:S110"})
public class HomePage extends WebPage {

	public HomePage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new WicketSettingsPanel("wicketSettingsPanel"));

		final Form<Void> form = new Form<>("form");
		form.add(new Label("label", "Start here ..."));
		form.add(new BootstrapButton("exitButton", Buttons.Type.Primary) {
			@Override
			public void onSubmit() {
				super.onSubmit();
				System.out.println("Button 'Beenden' wurde gedrückt.");
				System.exit(0);
			}
		}.setLabel(Model.of(getString("label.button.exit"))));
		add(form);
	}
}
