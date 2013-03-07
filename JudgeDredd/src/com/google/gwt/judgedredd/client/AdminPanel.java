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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

public class AdminPanel extends Composite {
	static AdminPanel APanel = new AdminPanel();

	public AdminPanel() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("665px", "780px");
		
		FlexTable flexTable_AdminReview = new FlexTable();
		flexTable_AdminReview.setStyleName("BackgroundColor-White");
		rootPanel.add(flexTable_AdminReview, 0, 10);
		flexTable_AdminReview.setSize("666px", "364px");
		
		Label lblApproval = new Label("Dataset(s) Awaiting Approval");
		lblApproval.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.setWidget(0, 0, lblApproval);
		
		Label lblRemoval = new Label("Dataset(s) Awaiting Removal");
		lblRemoval.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.setWidget(0, 1, lblRemoval);
		
		final ListBox listBox_Approval = new ListBox();
		flexTable_AdminReview.setWidget(1, 0, listBox_Approval);
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
		flexTable_AdminReview.setWidget(1, 1, listBox_Removal);
		listBox_Removal.setSize("191px", "290px");
		listBox_Removal.setVisibleItemCount(5);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnApprove = new Button("Approve");
		flexTable_AdminReview.setWidget(2, 0, btnApprove);
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
		});	// end btnApprove.addClickHandler;  
		
		btnApprove.setText("Approve");
		btnApprove.setSize("67", "30");
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
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
		
		flexTable_AdminReview.setWidget(2, 1, btnRemoval);
		
		FlexTable flexTable_DataDisplay = new FlexTable();
		rootPanel.add(flexTable_DataDisplay, 0, 395);
		flexTable_DataDisplay.setSize("666px", "332px");
		
		Label lblDataDisplay = new Label("Approved Data Display");
		lblDataDisplay.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_DataDisplay.setWidget(0, 0, lblDataDisplay);
		
		TextArea textArea_DataDisplay = new TextArea();
		textArea_DataDisplay.setAlignment(TextAlignment.CENTER);
		flexTable_DataDisplay.setWidget(1, 0, textArea_DataDisplay);
		textArea_DataDisplay.setSize("666px", "300px");
		flexTable_DataDisplay.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_DataDisplay.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_DataDisplay.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_DataDisplay.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnRetrieveData = new Button("Retrieve Data");
		flexTable_DataDisplay.setWidget(2, 0, btnRetrieveData);
		flexTable_DataDisplay.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_DataDisplay.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
									}

	public static AdminPanel get() {
	    return APanel;
	  }
	}
