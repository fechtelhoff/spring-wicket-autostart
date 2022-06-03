package de.fechtelhoff.web.gui.page;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public final class HtmlHeaderSupport {

	private HtmlHeaderSupport() {
		// intentionally blank
	}

	public static void renderHead(final IHeaderResponse response) {
		addCssToHtmlHeader(response);
	}

	private static void addCssToHtmlHeader(final IHeaderResponse response) {
		final ResourceReference resourceReference = new PackageResourceReference(HtmlHeaderSupport.class, "style.css");
		final CssHeaderItem headerItem = CssHeaderItem.forReference(resourceReference);
		response.render(headerItem);
	}
}
