package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Login extends Composite {
	private TextBox textBoxUsername;
	private TextBox textBoxPassword;

	public Login() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("335px", "187px");
		
		Label lblSignInTo = new Label("Sign in to your account");
		lblSignInTo.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblSignInTo);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(2);
		flexTable.setBorderWidth(0);
		flexTable.setCellPadding(1);
		verticalPanel.add(flexTable);
		flexTable.setSize("335px", "131px");
		
		Label lblUsername = new Label("Username:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);
		
		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);
		
		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblPassword);
		
		textBoxPassword = new TextBox();
		flexTable.setWidget(1, 1, textBoxPassword);
		
		Button btnSignIn = new Button("Sign In");
		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsername.getText().length() == 0 || textBoxPassword.getText().length() == 0) {
					Window.alert("Username or password is empty."); 
				}

				if (textBoxUsername.getText().length() != 0 && textBoxPassword.getText().length() != 0 &&
						(!textBoxUsername.getText().equals("admin") || !textBoxPassword.getText().equals("admin"))) {
					Window.alert("Incorrect Username or password."); 
				}
				if (textBoxUsername.getText().equals("admin") && textBoxPassword.getText().equals("admin")) {
					AdminPanel aPanel = AdminPanel.get();
				}
				}
		});
		flexTable.setWidget(2, 1, btnSignIn);
	}

}
