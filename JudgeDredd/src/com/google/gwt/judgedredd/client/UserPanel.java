package com.google.gwt.judgedredd.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.judgedredd.client.Map;


public class UserPanel extends Composite
{

	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);
	private VerticalPanel searchOptionsPanel = new VerticalPanel();
	private VerticalPanel resultPanel = new VerticalPanel();
	private String[] CRIME_TYPES = {"Commercial BE", "Mischief Over $5000", "Mischief Under $5000", 
			"Theft From Auto Over $5000","Theft From Auto Under $5000","Theft Of Auto Under $5000", "Theft Of Auto Over $5000"};
	private String[] MONTHS = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	private String[] YEARS = {"2010", "2011"};

	public UserPanel(){
		initWidget(searchOptionsPanel);

		/**
		 * Crime Type Search Box
		 */
		searchOptionsPanel.add(new Label("Search for Crime Type:"));
		final ListBox crimeTypesBox = pullDownMenus(CRIME_TYPES);
		searchOptionsPanel.add(crimeTypesBox);
		final Button typeSubmitButton = new Button("Show Crimes for Type");
		typeSubmitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String crimeType = crimeTypesBox.getItemText(crimeTypesBox.getSelectedIndex());
				System.out.println("looking for crime type " + crimeType);
				crimeService.getCertainCrimeType(crimeType, new AsyncCallback<ClientCrime[]>() 
						{
					public void onFailure(Throwable error) 
					{
						Window.alert(error.getMessage());
					}
					public void onSuccess(ClientCrime[] crimes) 
					{
						searchOptionsPanel.add(displayResults(crimes));
					}
						});
			}
		});
		searchOptionsPanel.add(typeSubmitButton);

		// Crime Type Search End

		/**
		 * Month and Year Search Box
		 */
		searchOptionsPanel.add(new Label("Search by Month:"));
		final ListBox monthBox = pullDownMenus(MONTHS);

		// TODO:
		// 	EITHER HARDCODE OR GENERATE FROM QUERY
		final ListBox yearBox = pullDownMenus(YEARS);

		searchOptionsPanel.add(monthBox);
		searchOptionsPanel.add(yearBox);
		final Button dateSubmitButton = new Button("Show Crimes by Month");
		dateSubmitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String month = monthBox.getItemText(monthBox.getSelectedIndex());
				int targetYear = 2010;
				try
				{
				   targetYear = Integer.parseInt(yearBox.getItemText(yearBox.getSelectedIndex()));
				}
				catch (NumberFormatException nfe)
				{

				}
				System.out.println("looking for period " + month +" at Index: "+ monthBox.getSelectedIndex() + ", " + targetYear);
				int[] targetMonth = {monthBox.getSelectedIndex()+1};
				crimeService.getCrimesByMonth(targetMonth, targetYear, new AsyncCallback<ClientCrime[]>() 
						{
					public void onFailure(Throwable error) 
					{
						Window.alert(error.getMessage());
					}
					public void onSuccess(ClientCrime[] crimes) 
					{
						searchOptionsPanel.add(displayResults(crimes));
					}
						});
			}
		});
		searchOptionsPanel.add(dateSubmitButton);

		// Crime Type Search End

		
		/**
		 * Show All Crimes
		 */
		final Button showAllButton = new Button("Show All Crimes");
		// Add a handler to close the DialogBox
		showAllButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				crimeService.getAllCrimes(new AsyncCallback<ClientCrime[]>() 
						{
					public void onFailure(Throwable error) 
					{
						Window.alert(error.getMessage());
					}
					public void onSuccess(ClientCrime[] crimes) 
					{
						searchOptionsPanel.add(displayResults(crimes));
					}
						});
			}
		});
		searchOptionsPanel.add(showAllButton);
		// Show All Crime Ends

	}

	private ListBox pullDownMenus(String[] items){
		final ListBox listBox = new ListBox();
		for(String item : items){
			listBox.addItem(item);
		}
		listBox.setVisibleItemCount(1);
		// Add SuggestBox to Horizontal Panel
		return listBox;
	}

	/**
	 * Display the requested crimes in a cell Table
	 */
	public VerticalPanel displayResults(ClientCrime[] crimes) {
		resultPanel.clear();
		if(crimes.length == 0){
			resultPanel.add(new Label("No Crimes Found on Search Criteria"));
		}
		else{
			final List<ClientCrime> CRIMES = Arrays.asList(crimes);

			// Create a CellTable.
			final CellTable<ClientCrime> crimeTable = new CellTable<ClientCrime>();
			// Display 10 rows in one page
			crimeTable.setPageSize(10);


			// Add a text column to show the name.
			TextColumn<ClientCrime> typeColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getType();
				}
			};
			crimeTable.addColumn(typeColumn, "Crime Type");

			// add Month Column
			TextColumn<ClientCrime> monthColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getCrimeMonth()+"";
				}
			};
			crimeTable.addColumn(monthColumn, "Month");
			
			// Add a Year column 
			TextColumn<ClientCrime> yearColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getCrimeYear()+"";
				}
			};
			crimeTable.addColumn(yearColumn, "Year");
			// Add a text column to show the address.
			TextColumn<ClientCrime> addressColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getLocation();
				}
			};
			crimeTable.addColumn(addressColumn, "Location");

			AsyncDataProvider<ClientCrime> provider = new AsyncDataProvider<ClientCrime>() {
				@Override
				protected void onRangeChanged(HasData<ClientCrime> display) {
					int start = display.getVisibleRange().getStart();
					int end = start + display.getVisibleRange().getLength();
					end = end >= CRIMES.size() ? CRIMES.size() : end;
					List<ClientCrime> sub = CRIMES.subList(start, end);
					updateRowData(start, sub);
//					updateMapPoints(start, sub);
				}


			};
			provider.addDataDisplay(crimeTable);
			provider.updateRowCount(CRIMES.size(), true);
			SimplePager crimeTablePager = new SimplePager();
			crimeTablePager.setDisplay(crimeTable);
			resultPanel.add(crimeTable);
			resultPanel.add(crimeTablePager);

		}
		return resultPanel;
	}

//	protected void updateMapPoints(int start, List<ClientCrime> sub) {
//		// TODO Auto-generated method stub
//		for(ClientCrime c : sub){
//			addCrimePoint(c.getType(), c.getLatitude(), c.getLongitude());
//		}
//
//	}
}	// end UserPanel
