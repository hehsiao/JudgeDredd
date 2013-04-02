package com.google.gwt.judgedredd.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.judgedredd.client.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;


public class UserPanel extends Composite
{
	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);
	private VerticalPanel searchOptionsPanel = new VerticalPanel();
	private VerticalPanel resultPanel = new VerticalPanel();

	private Map theMap = new Map();
	private String[] CRIME_TYPES = {"Commercial BE", "Mischief Over $5000", "Mischief Under $5000", 
			"Theft From Auto Over $5000","Theft From Auto Under $5000","Theft Of Auto Under $5000", "Theft Of Auto Over $5000"};
	private String[] MONTHS = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	private String[] YEARS = {"2010", "2011"};

	private HTML hFacebook;
	private HTML hTwitter;

	public UserPanel(){
		RootPanel.get("map").add(theMap);
		initWidget(searchOptionsPanel);
		hFacebook = new HTML("&nbsp;", true);
		hTwitter = new HTML("&nbsp;", true);

		socialSetup();

		RootPanel.get("fb_like").add(hFacebook);

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
				crimeService.getAllCrimes(true, new AsyncCallback<ClientCrime[]>() 
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
		theMap.clearCrimePoints();
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

			// add Date Column
			TextColumn<ClientCrime> monthColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getCrimeMonth()+"/"+object.getCrimeYear();
				}
			};
			crimeTable.addColumn(monthColumn, "Date");

			// Add a text column to show the address.
			TextColumn<ClientCrime> addressColumn = new TextColumn<ClientCrime>() {
				@Override
				public String getValue(ClientCrime object) {
					return object.getLocation();
				}
			};
			crimeTable.addColumn(addressColumn, "Location");

			// Add Twitter Column to Tweet the Crime
			final SafeHtmlCell twitterCell = new SafeHtmlCell();

			Column<ClientCrime, SafeHtml> twitterColumn = new Column<ClientCrime, SafeHtml>(twitterCell) {

				@Override
				public SafeHtml getValue(ClientCrime object) {
					SafeHtmlBuilder sb = new SafeHtmlBuilder();
					sb.appendHtmlConstant("<a href=\"https://twitter.com/intent/tweet?button_hashtag=TwitterStories&text=TESTING TWEET\"><img src=\"images/twitter.png\" height=\"42\" width=\"42\"></a>");
					return sb.toSafeHtml();
				}
			};
			
			crimeTable.addColumn(twitterColumn, "Tweet Crime");

			// Dominic Use this a template to implement the legend
			final SafeHtmlCell imageCell = new SafeHtmlCell();

			Column<ClientCrime, SafeHtml> imageColumn = new Column<ClientCrime, SafeHtml>(imageCell) {

				@Override
				public SafeHtml getValue(ClientCrime object) {
					SafeHtmlBuilder sb = new SafeHtmlBuilder();
					sb.appendHtmlConstant("<img src=\"images/car.jpg\" alt=\"car\" height=\"42\" width=\"42\">");
					return sb.toSafeHtml();
				}
			};

			crimeTable.addColumn(imageColumn, "Legend");

			AsyncDataProvider<ClientCrime> provider = new AsyncDataProvider<ClientCrime>() {
				@Override
				protected void onRangeChanged(HasData<ClientCrime> display) {
					int start = display.getVisibleRange().getStart();
					int end = start + display.getVisibleRange().getLength();
					end = end >= CRIMES.size() ? CRIMES.size() : end;
					List<ClientCrime> sub = CRIMES.subList(start, end);
					updateRowData(start, sub);
					updateMapPoints(start, sub);
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

	protected void updateMapPoints(int start, List<ClientCrime> sub) {
		theMap.clearCrimePoints();
		// TODO Auto-generated method stub
		for(ClientCrime crime : sub){
			theMap.addCrimePoint(crime);
		}

	}

	private void socialSetup() 
	{
		setupFacebookScript();
		setupTwitterScript();
		drawTwitterButton();
		drawFacebookButton();
	}

	private void drawTwitterButton()
	{
		String s = "<a href=\"https://twitter.com/intent/tweet?button_hashtag=Dredd&text=The Law? I am the Law.\" class=\"twitter-hashtag-button\" data-lang=\"en\" >Tweet #Dredd<img src=\"twitter.png\"></a>";

		getHTwitter().setHTML(s);
	}

	private void setupTwitterScript()
	{
		Document doc = Document.get();
		ScriptElement script = doc.createScriptElement();
		script.setSrc("http://platform.twitter.com/widgets.js");
		script.setType("text/javascript");
		script.setLang("javascript");
		doc.getBody().appendChild(script);
	}

	private void drawFacebookButton() {
		String s = "<fb:like " +
				"href=\"http://judgedredd.appspot.com\" " +
				"layout=\"button_count\" " +
				"show_faces=\"true\" " +
				"show_faces=\"false\" " +
				"width=\"50\">" +
				"</fb:like>";

		getHFacebook().setHTML(s);
	}

	private void setupFacebookScript() {
		Document doc = Document.get();
		ScriptElement script = doc.createScriptElement();
		script.setSrc("http://connect.facebook.net/en_US/all.js#xfbml=1");
		script.setType("text/javascript");
		script.setLang("javascript");
		doc.getBody().appendChild(script);
	}

	public HTML getHTwitter() {
		return hTwitter;
	}

	public HTML getHFacebook() {
		return hFacebook;
	}
}	// end UserPanel
