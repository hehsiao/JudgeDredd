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

public class AdminPanel extends Composite {
	static AdminPanel APanel = new AdminPanel();

	public AdminPanel() {
		RootPanel rootPanel = RootPanel.get();
		
		
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 10, 200);
		flexTable.setSize("199px", "42px");
		
		Label lblApproval = new Label("Dataset(s) Awaiting Approval");
		flexTable.setWidget(0, 0, lblApproval);
		
		Label lblRemoval = new Label("Dataset(s) Awaiting Removal");
		flexTable.setWidget(0, 1, lblRemoval);
		
		final ListBox listBox_Approval = new ListBox();
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
		
		final ListBox listBox_Removal = new ListBox();
		listBox_Removal.setMultipleSelect(true);
		flexTable.setWidget(1, 1, listBox_Removal);
		listBox_Removal.setSize("191px", "290px");
		listBox_Removal.setVisibleItemCount(5);
		
		FlexTable flexTable_Button = new FlexTable();
		flexTable.setWidget(2, 0, flexTable_Button);
		flexTable_Button.setSize("199px", "42px");
		
		Button btnApprove = new Button("Approve");
		btnApprove.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				for (int index = 0; index < listBox_Approval.getItemCount(); index++)
				{
					if (listBox_Approval.isItemSelected(index))
					{
						listBox_Removal.addItem(listBox_Approval.getItemText(index));
						
						listBox_Approval.removeItem(index);
						
						index--;
					}	// end if
				}	// end for loop
			}	// end onClick(ClickEven event)
		});	// end btnApprove.addClickHandler
		
		btnApprove.setText("Approve");
		flexTable_Button.setWidget(0, 0, btnApprove);
		btnApprove.setSize("67", "30");
		flexTable_Button.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_Button.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnRemoval = new Button("Removal");
		btnRemoval.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				for (int index = 0; index < listBox_Removal.getItemCount(); index++)
				{
					if (listBox_Removal.isItemSelected(index))
					{
						listBox_Approval.addItem(listBox_Removal.getItemText(index));
						
						listBox_Removal.removeItem(index);
						
						index--;
					}	// end if
				}	// end for loop
			}	// end onClick(ClickEven event)
		});	// end btnRemoval.addClickHandler
		
		flexTable.setWidget(2, 1, btnRemoval);
		flexTable.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);;  
									}

	public static AdminPanel get() {
	    return APanel;
	  }
	}

