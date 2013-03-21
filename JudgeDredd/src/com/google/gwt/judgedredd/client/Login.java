package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Login extends Composite {
	private TextBox textBoxUsername;

	public Login() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("386px", "200px");
		
		final Label lblSignInTo = new Label("Citizens of Vancouver, this is Judge Dredd. Identify yourself: ");
		lblSignInTo.setSize("380px", "35px");
		lblSignInTo.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblSignInTo);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(2);
		flexTable.setBorderWidth(0);
		flexTable.setCellPadding(1);
		verticalPanel.add(flexTable);
		flexTable.setSize("368px", "130px");
		
		Label lblUsername = new Label("Login ID:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);
		
		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);
		textBoxUsername.setSize("175px", "20px");
		
		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblPassword);
		
		final PasswordTextBox textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, textBoxPassword);
		textBoxPassword.setSize("175px", "20px");
		
		Button btnSignIn = new Button("Sign In");
		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsername.getText().length() == 0 || textBoxPassword.getText().length() == 0) {
					Window.alert("Username or password is empty."); 
				}
				if (textBoxUsername.getText().equals("admin") && textBoxPassword.getText().equals("admin")) {
					@SuppressWarnings("unused")
					AdminPanel aPanel = AdminPanel.get();
				}
				if (textBoxUsername.getText().length() != 0 && textBoxPassword.getText().length() != 0 &&
						(!textBoxUsername.getText().equals("admin") || !textBoxPassword.getText().equals("admin"))) {
					@SuppressWarnings("unused")
					UserPanel uPanel = UserPanel.get(); 
				}
			}
		});
		
		flexTable.setWidget(2, 1, btnSignIn);
		flexTable.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_LEFT);
	}
	
	public void hide() {
		this.setVisible(false);
	}
	
}
