// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package de.fechtelhoff;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefFocusHandlerAdapter;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;

/**
 * This is a simple example application using JCEF.
 * It displays a JFrame with a JTextField at its top and a CefBrowser in its
 * center. The JTextField is used to enter and assign an URL to the browser UI.
 * No additional handlers or callbacks are used in this example.
 * <p>
 * The number of used JCEF classes is reduced (nearly) to its minimum and should
 * assist you to get familiar with JCEF.
 * <p>
 * For a more feature complete example have also a look onto the example code
 * within the package "tests.detailed".
 */
public class MainFrame extends JFrame {

	private boolean browserFocus = true;

	/**
	 * To display a simple browser window, it suffices completely to create an
	 * instance of the class CefBrowser and to assign its UI component to your
	 * application (e.g. to your content pane).
	 * But to be more verbose, this CTOR keeps an instance of each object on the
	 * way to the browser UI.
	 */
	public MainFrame(final String startURL, final boolean useOSR, final boolean isTransparent) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
		// (0) Initialize CEF using the maven loader
		CefAppBuilder builder = new CefAppBuilder();
		// windowless_rendering_enabled must be set to false if not wanted.
		builder.getCefSettings().windowless_rendering_enabled = useOSR;
		// USE builder.setAppHandler INSTEAD OF CefApp.addAppHandler!
		// Fixes compatibility issues with MacOSX
		builder.setAppHandler(new MavenCefAppHandlerAdapter() {
			@Override
			public void stateHasChanged(CefAppState state) {
				// Shutdown the app if the native CEF part is terminated
				if (state == CefAppState.TERMINATED) {
					System.exit(0);
				}
			}
		});

		// (1) The entry point to JCEF is always the class CefApp. There is only one
		//     instance per application and therefore you have to call the method
		//     "getInstance()" instead of a CTOR.
		//
		//     CefApp is responsible for the global CEF context. It loads all
		//     required native libraries, initializes CEF accordingly, starts a
		//     background task to handle CEF's message loop and takes care of
		//     shutting down CEF after disposing it.
		//
		//     WHEN WORKING WITH MAVEN: Use the builder.build() method to
		//     build the CefApp on first run and fetch the instance on all consecutive
		//     runs. This method is thread-safe and will always return a valid app
		//     instance.
		CefApp cefApp = builder.build();

		// (2) JCEF can handle one to many browser instances simultaneous. These
		//     browser instances are logically grouped together by an instance of
		//     the class CefClient. In your application you can create one to many
		//     instances of CefClient with one to many CefBrowser instances per
		//     client. To get an instance of CefClient you have to use the method
		//     "createClient()" of your CefApp instance. Calling an CTOR of
		//     CefClient is not supported.
		//
		//     CefClient is a connector to all possible events which come from the
		//     CefBrowser instances. Those events could be simple things like the
		//     change of the browser title or more complex ones like context menu
		//     events. By assigning handlers to CefClient you can control the
		//     behavior of the browser. See tests.detailed.MainFrame for an example
		//     of how to use these handlers.
		CefClient client = cefApp.createClient();

		// (3) Create a simple message router to receive messages from CEF.
		CefMessageRouter msgRouter = CefMessageRouter.create();
		client.addMessageRouter(msgRouter);

		// (4) One CefBrowser instance is responsible to control what you'll see on
		//     the UI component of the instance. It can be displayed off-screen
		//     rendered or windowed rendered. To get an instance of CefBrowser you
		//     have to call the method "createBrowser()" of your CefClient
		//     instances.
		//
		//     CefBrowser has methods like "goBack()", "goForward()", "loadURL()",
		//     and many more which are used to control the behavior of the displayed
		//     content. The UI is held within a UI-Component which can be accessed
		//     by calling the method "getUIComponent()" on the instance of CefBrowser.
		//     The UI component is inherited from a java.awt.Component and therefore
		//     it can be embedded into any AWT UI.
		CefBrowser browser = client.createBrowser(startURL, useOSR, isTransparent);
		Component browserUI = browser.getUIComponent();

		// Clear focus from the address field when the browser gains focus.
		client.addFocusHandler(new CefFocusHandlerAdapter() {
			@Override
			public void onGotFocus(CefBrowser browser) {
				if (browserFocus) {
					return;
				}
				browserFocus = true;
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				browser.setFocus(true);
			}

			@Override
			public void onTakeFocus(CefBrowser browser, boolean next) {
				browserFocus = false;
			}
		});

		// (5) All UI components are assigned to the default content pane of this
		//     JFrame and afterwards the frame is made visible to the user.
		getContentPane().add(browserUI, BorderLayout.CENTER);
		pack();
		final ScreenSize screenSize = getScreenSize();
		setSize(screenSize.width - 100, screenSize.height - 100);
		setVisible(true);

		// (6) To take care of shutting down CEF accordingly, it's important to call
		//     the method "dispose()" of the CefApp instance if the Java
		//     application will be closed. Otherwise, you'll get asserts from CEF.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CefApp.getInstance().dispose();
				dispose();
			}
		});
	}

	private ScreenSize getScreenSize() {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
		return new ScreenSize(width, height);
	}

	private record ScreenSize(int width, int height) {
	}
}
