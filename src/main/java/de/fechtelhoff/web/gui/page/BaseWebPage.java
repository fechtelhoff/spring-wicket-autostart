package de.fechtelhoff.web.gui.page;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings({"java:S110"}) // java:S110 -> This class has 7 parents which is greater than 5 authorized.
public class BaseWebPage extends WebPage {

	public BaseWebPage() {
	}

	public BaseWebPage(final IModel<?> model) {
		super(model);
	}

	public BaseWebPage(final PageParameters parameters) {
		super(parameters);
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		HtmlHeaderSupport.renderHead(response);
	}
}
