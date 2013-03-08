package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

public class AdminPanel extends Composite {
	static AdminPanel APanel = new AdminPanel();
	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);

	public AdminPanel() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("700px", "700px");
		
		FlexTable flexTable_AdminReview = new FlexTable();
		flexTable_AdminReview.setStyleName("BackgroundColor-White");
		rootPanel.add(flexTable_AdminReview, 115, 10);
		flexTable_AdminReview.setSize("700px", "400px");
		
		Label lblApproval = new Label("Dataset(s) Awaiting Approval");
		lblApproval.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.setWidget(1, 0, lblApproval);
		
		Label lblRemoval = new Label("Dataset(s) Awaiting Removal");
		lblRemoval.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.setWidget(1, 2, lblRemoval);
		
		final ListBox listBox_Approval = new ListBox(true);
		flexTable_AdminReview.setWidget(2, 0, listBox_Approval);
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
		
		final Button btnParseData = new Button("Parse Data");
		btnParseData.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				btnParseData.setEnabled(false);
				
				crimeService.addReport("Test", new AsyncCallback<Void>() 
				{
					public void onFailure(Throwable error) 
					{
				         System.out.println("Failed");
				    }
				    public void onSuccess(Void ignore) 
				    {
				    	  System.out.println("Success");
				    }
				});
		    }
		});
		
		flexTable_AdminReview.setWidget(2, 1, btnParseData);
		
		final ListBox listBox_Removal = new ListBox(true);
		flexTable_AdminReview.setWidget(2, 2, listBox_Removal);
		listBox_Removal.setSize("191px", "290px");
		listBox_Removal.setVisibleItemCount(5);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(2, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(2, 2, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnApprove = new Button("Approve");
		flexTable_AdminReview.setWidget(4, 0, btnApprove);
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
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(4, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(4, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
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
		
		flexTable_AdminReview.setWidget(4, 2, btnRemoval);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblDataDisplay = new Label("Approved Data Display");
		flexTable_AdminReview.setWidget(5, 1, lblDataDisplay);
		lblDataDisplay.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblDataDisplay.setWidth("200px");
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(5, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		TextArea textArea_DataDisplay = new TextArea();
		flexTable_AdminReview.setWidget(6, 1, textArea_DataDisplay);
		textArea_DataDisplay.setAlignment(TextAlignment.CENTER);
		textArea_DataDisplay.setSize("600px", "250px");
		
		Button btnRetrieveData = new Button("Retrieve Data");
		flexTable_AdminReview.setWidget(7, 1, btnRetrieveData);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_AdminReview.getCellFormatter().setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_AdminReview.getCellFormatter().setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		btnRetrieveData.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				System.out.println("retrieving data");
				
				/*
				Crime[] c = crimeService.getMonthlyCrimes(2, new AsyncCallback<Crime[]>() {
								public void onFailure(Throwable error) {
									System.out.println("failed to retrieve data");
								}
							}); */
			}
		});
		
									}

	public static AdminPanel get() {
	    return APanel;
	  }
	}
