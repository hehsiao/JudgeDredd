package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

public class AdminService implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel rootPanel = RootPanel.get();
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 0, 0);
		flexTable.setSize("450px", "450px");
		
		Label lblTitle = new Label("Dataset(s) Awaiting Approval");
		flexTable.setWidget(0, 0, lblTitle);
		
		ListBox listBox_Approval = new ListBox();
		flexTable.setWidget(1, 0, listBox_Approval);
		listBox_Approval.setMultipleSelect(true);
		listBox_Approval.setSize("191px", "290px");
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
		
		FlexTable flexTable_Button = new FlexTable();
		flexTable.setWidget(2, 0, flexTable_Button);
		flexTable_Button.setSize("199px", "42px");
		
		Button btnApprove = new Button("Approve");
		btnApprove.setText("Approve");
		flexTable_Button.setWidget(0, 0, btnApprove);
		flexTable_Button.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_Button.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
