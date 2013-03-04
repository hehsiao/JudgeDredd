package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class AdminService implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel rootPanel = RootPanel.get();
		
		FlexTable flexTable_Approval = new FlexTable();
		rootPanel.add(flexTable_Approval, 0, 0);
		flexTable_Approval.setSize("199px", "300px");
		
		ListBox listBox_Approval = new ListBox();
		listBox_Approval.setMultipleSelect(true);
		flexTable_Approval.setWidget(0, 0, listBox_Approval);
		listBox_Approval.setSize("191px", "290px");
		listBox_Approval.setName("Dataset Awaiting for Approval");
		listBox_Approval.addItem("January");
		listBox_Approval.addItem("February");
		listBox_Approval.addItem("March");
		listBox_Approval.addItem("April");
		listBox_Approval.addItem("May");
		listBox_Approval.addItem("June");
		listBox_Approval.addItem("July");
		listBox_Approval.addItem("August");
		listBox_Approval.addItem("September");
		listBox_Approval.addItem("October");
		listBox_Approval.addItem("November");
		listBox_Approval.addItem("December");
		listBox_Approval.setVisibleItemCount(5);
		flexTable_Approval.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_Approval.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable_Button = new FlexTable();
		rootPanel.add(flexTable_Button, 0, 306);
		flexTable_Button.setSize("199px", "42px");
		
		Button btnApprove = new Button("Approve");
		btnApprove.setText("Approve");
		flexTable_Button.setWidget(0, 0, btnApprove);
		flexTable_Button.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_Button.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
