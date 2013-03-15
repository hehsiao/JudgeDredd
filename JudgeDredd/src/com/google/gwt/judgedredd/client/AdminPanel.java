package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;


public class AdminPanel extends Composite {
	static AdminPanel APanel = new AdminPanel();
	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);

	public AdminPanel() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("700px", "700px");
		
		FlexTable flexTable_AdminReview = new FlexTable();
		flexTable_AdminReview.setStyleName("BackgroundColor-Black");
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
		
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		final TextBox urlField = new TextBox();
		urlField.setText("http://www.henrychsiao.com/crime_2010.csv");
		urlField.setFocus(true);
		urlField.selectAll();
		
		final Button btnParseData = new Button("Parse Data");
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				btnParseData.setEnabled(true);
				btnParseData.setFocus(true);
			}
		});

		
		btnParseData.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				btnParseData.setEnabled(false);
				String url = urlField.getText();
				crimeService.addReport(url, new AsyncCallback<int[]>() 
				{
					public void onFailure(Throwable error) 
					{
				         System.out.println("Failed");
				    }
				    public void onSuccess(int[] monthlyCrimes) 
				    {
				    	popUpDisplay(monthlyCrimes);
				    }
					private void popUpDisplay(int[] monthlyCrimes) {
						// TODO Auto-generated method stub
				    	// TODO: notify admin that the data is stored in UI
				    	
						System.out.println("Success");		
						int totalCrimes = 0;
				    	String result = "";
						for(int i = 0; i < monthlyCrimes.length; i++){

							totalCrimes += monthlyCrimes[i]; 
					        String monthString;
					        switch (i+1) {
					            case 1:  monthString = "January";	break;
					            case 2:  monthString = "February";	break;
					            case 3:  monthString = "March";		break;
					            case 4:  monthString = "April";		break;
					            case 5:  monthString = "May";		break;
					            case 6:  monthString = "June";		break;
					            case 7:  monthString = "July";		break;
					            case 8:  monthString = "August";	break;
					            case 9:  monthString = "September";	break;
					            case 10: monthString = "October";	break;
					            case 11: monthString = "November";	break;
					            case 12: monthString = "December";	break;
					            default: monthString = "Invalid month";	break;
					        }
					        result +=  monthlyCrimes[i] + " crimes occured in " + monthString + ".<br>";
//							System.out.println(monthString + " has " + monthlyCrimes[i] + " crimes.");
						}
				    	result += "<br>Total Crimes recorded: " + totalCrimes + ".<br><br>";
				    	
						dialogBox.setText("Parsing Complete");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				    
				});
		    }
		});
		
		flexTable_AdminReview.setWidget(1, 1, urlField);
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
				int[] monthsSelected = new int[12];
				int monthsSelectedCount = 0;
				for (int index = 0; index < listBox_Approval.getItemCount(); index++)
				{
					if (listBox_Approval.isItemSelected(index))
					{
						listBox_Removal.addItem(listBox_Approval.getItemText(index));
						
						listBox_Approval.removeItem(index);
						// TEST CODES
						// TODO: NOT FUNCTIONING ALL PROPERLY BECAUSE OF HOW REMOVE INDEX WORKS.
						monthsSelected[monthsSelectedCount++] = index+1;
						
						index--;
					}	// end if
				}	// end for loop
				
				// TODO: TEST CODES
				// NEEDS TO BE CHANGED TO APPROVEDCRIMES INSTEAD
				crimeService.getCrimesByMonth( monthsSelected , new AsyncCallback<ClientCrime[]> () {
					public void onFailure(Throwable error) {
						System.out.println("Failed");
					}
					public void onSuccess(ClientCrime[] crimeArray) {
						ClientCrime[] monthlyCrimes = crimeArray;
						// TODO: Display list on UI
					}
				});
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
				
				// TODO: integrate selections of month and replace static "3"
				
				int targetMonths[] = new int[3];
				targetMonths[0] = 2;
				targetMonths[1] = 7;
				targetMonths[2] = 12;
				
				crimeService.getCrimesByMonth( targetMonths , new AsyncCallback<ClientCrime[]> () {
					public void onFailure(Throwable error) {
						System.out.println("Failed");
					}
					public void onSuccess(ClientCrime[] crimeArray) {
						ClientCrime[] monthlyCrimes = crimeArray;
						// TODO: Display list on UI
					}
				});
			}
				
			});

			
		}


	public static AdminPanel get() {
	    return APanel;
	  }
	}
