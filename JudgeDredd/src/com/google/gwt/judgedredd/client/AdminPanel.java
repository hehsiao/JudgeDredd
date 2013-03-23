package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FlexTable;

public class AdminPanel extends Composite 
{
	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);
	private FlexTable approveFlexTable;
	private FlowPanel flowpanel;

	public AdminPanel() 
	{
		flowpanel = new FlowPanel();
		initWidget(flowpanel);
		flowpanel.setSize("100%", "100%");

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		
		final Button closeButton = new Button("Close");
		final Button btnParseData = new Button("Parse Data");
		
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		final TextBox URL_Field = new TextBox();
		URL_Field.setText("http://www.henrychsiao.com/crime_2010.csv");
		URL_Field.setFocus(true);
		URL_Field.selectAll();
		flowpanel.add(URL_Field);
		URL_Field.setWidth("250px");

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
				String url = URL_Field.getText();
				crimeService.addReport(url, new AsyncCallback<int[]>() 
						{
					public void onFailure(Throwable error) 
					{
						System.out.println("Failed");
					}
					public void onSuccess(int[] monthlyCrimes) 
					{
						displayCrimes(monthlyCrimes);
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

		flowpanel.add(btnParseData);



	}

	private void displayCrimes(int[] monthlyCrimes) {

		approveFlexTable = new FlexTable();
		flowpanel.add(approveFlexTable);

		// Create table for crime data.
		approveFlexTable.setText(0, 0, "Month");
		approveFlexTable.setText(0, 1, "Crimes");
		approveFlexTable.setText(0, 2, "Approve");

		// Add styles to elements in the crime list table.
		approveFlexTable.setCellPadding(6);
		approveFlexTable.getRowFormatter().addStyleName(0, "approveCrimeListHeader");
		approveFlexTable.addStyleName("approveCrimeList");
		approveFlexTable.getCellFormatter().addStyleName(0, 1, "approveCrimeListNumericColumn");
		approveFlexTable.getCellFormatter().addStyleName(0, 2, "approveCrimeListNumericColumn");

		int totalCrimes = 0;

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
			displayCrime(monthString, monthlyCrimes[i]);
		}

		flowpanel.add(new Label("Total Crime Count Imported: " + totalCrimes));
	}

	private void displayCrime(final String month, int crimeCount) {
		// Add the crime to the table.
		int row = approveFlexTable.getRowCount();

		approveFlexTable.setText(row, 0, month);
		approveFlexTable.setText(row, 1, ""+crimeCount);
		approveFlexTable.setWidget(row, 2, new Label());
		approveFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		approveFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");

		//Add a button to remove this crime from the table.
		Button removecrimeButton = new Button("Approve");
		removecrimeButton.addStyleDependentName("remove");
		removecrimeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//removecrime(symbol);
			}
		});
		approveFlexTable.setWidget(row, 2, removecrimeButton);

	}
}