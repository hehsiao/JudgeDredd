package com.google.gwt.judgedredd.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class UserPanel extends Composite
{

	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);
	private VerticalPanel vp;
	
	public UserPanel(){
		vp = new VerticalPanel();
		initWidget(vp);
		int[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
		
		crimeService.getCrimesByMonth(months, new AsyncCallback<ClientCrime[]>() 
				{
			public void onFailure(Throwable error) 
			{
//				System.out.println("Failed");
			}
			public void onSuccess(ClientCrime[] crimes) 
			{
//				System.out.println("Retrieving Crimes");
				displayResults(crimes);
			}
				});
	}
	/**
	 * 
	 */
	public void displayResults(ClientCrime[] crimes) {
		final List<ClientCrime> CRIMES = Arrays.asList(crimes);
		
		// Create a CellTable.
		final CellTable<ClientCrime> table = new CellTable<ClientCrime>();
		// Display 3 rows in one page
		table.setPageSize(10);
		

		// Add a text column to show the name.
		TextColumn<ClientCrime> typeColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getType();
			}
		};
		table.addColumn(typeColumn, "Crime Type");

		// Add a date column to show the birthday.
		TextColumn<ClientCrime> monthColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getCrimeMonth()+"";
			}
		};
		table.addColumn(monthColumn, "Month");

		// Add a text column to show the address.
		TextColumn<ClientCrime> addressColumn = new TextColumn<ClientCrime>() {
			@Override
			public String getValue(ClientCrime object) {
				return object.getLocation();
			}
		};
		table.addColumn(addressColumn, "Location");

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
		provider.addDataDisplay(table);
		provider.updateRowCount(CRIMES.size(), true);

		SimplePager pager = new SimplePager();
		pager.setDisplay(table);
		
		vp.add(table);
		vp.add(pager);
	}
}	// end UserPanel
