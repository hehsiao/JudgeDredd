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


public class UserPanel extends Composite
{

	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);
	private VerticalPanel searchOptionsPanel = new VerticalPanel();
	private VerticalPanel resultPanel = new VerticalPanel();

	
	public UserPanel(){
		initWidget(searchOptionsPanel);

		
		final Button submitButton = new Button("Show All Crimes");
		// Add a handler to close the DialogBox
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
				crimeService.getCrimesByMonth(months, new AsyncCallback<ClientCrime[]>() 
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

		final ListBox crimeTypesBox = new ListBox();
		crimeTypesBox.addItem("Commercial BE");
		crimeTypesBox.addItem("Mischief Over $5000");
		crimeTypesBox.addItem("Mischief Under $5000");
		crimeTypesBox.addItem("Theft From Auto Over $5000");
		crimeTypesBox.addItem("Theft From Auto Under $5000");
		crimeTypesBox.addItem("Theft Of Auto Under $5000");
		crimeTypesBox.addItem("Theft Of Auto Over $5000");

		crimeTypesBox.setVisibleItemCount(1);
		// Create and add new Label to Horizontal Panel  
		searchOptionsPanel.add(new Label("Search for Crime Type:"));
		// Add SuggestBox to Horizontal Panel
		searchOptionsPanel.add(crimeTypesBox);

		final Button typeSubmitButton = new Button("Show Crimes for Type");
		// Add a handler to close the DialogBox
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
		searchOptionsPanel.add(submitButton);
		
	}

	/**
	 * Display the requested crimes in a cell Table
	 */
	public VerticalPanel displayResults(ClientCrime[] crimes) {
		resultPanel.clear();
		final List<ClientCrime> CRIMES = Arrays.asList(crimes);

		// Create a CellTable.
		final CellTable<ClientCrime> crimeTable = new CellTable<ClientCrime>();
		// Display 3 rows in one page
		crimeTable.setPageSize(10);


		// Add a text column to show the name.
		TextColumn<ClientCrime> typeColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getType();
			}
		};
		crimeTable.addColumn(typeColumn, "Crime Type");

		// Add a date column to show the birthday.
		TextColumn<ClientCrime> monthColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getCrimeMonth()+"";
			}
		};
		crimeTable.addColumn(monthColumn, "Month");

		// Add a text column to show the address.
		TextColumn<ClientCrime> addressColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getLocation();
			}
		};
		crimeTable.addColumn(addressColumn, "Location");

		// Associate an async data provider to the table
		// XXX: Use AsyncCallback in the method onRangeChanged
		// to actaully get the data from the server side
		AsyncDataProvider<ClientCrime> provider = new AsyncDataProvider<ClientCrime>() {
			@Override
			protected void onRangeChanged(HasData<ClientCrime> display) {
				int start = display.getVisibleRange().getStart();
				int end = start + display.getVisibleRange().getLength();
				end = end >= CRIMES.size() ? CRIMES.size() : end;
				List<ClientCrime> sub = CRIMES.subList(start, end);
				updateRowData(start, sub);
			}
		};
		provider.addDataDisplay(crimeTable);
		provider.updateRowCount(CRIMES.size(), true);

		SimplePager crimeTablePager = new SimplePager();
		crimeTablePager.setDisplay(crimeTable);
		resultPanel.add(crimeTable);
		resultPanel.add(crimeTablePager);
		return resultPanel;
	}
}	// end UserPanel
