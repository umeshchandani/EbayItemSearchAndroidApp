package com.example.ebay_item_search;




import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {
	static boolean chk = false;
	static String ebay_keyword;
	static String ebay_price_from;
	static String ebay_price_to;
	static String ebay_sort_by;
	static String ebay_sort_val;
	static String entered_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_search);

		final TextView ebay_item_search = (TextView) findViewById(R.id.ebayTitle);
		ebay_item_search.setTextColor(Color.BLUE);
		final EditText keyword = (EditText)findViewById(R.id.editKeywordText);
		final EditText price_from = (EditText)findViewById(R.id.editPriceFromText);
		final EditText price_to = (EditText)findViewById(R.id.editPriceToText);
		final Spinner sorty_By_Spinner = (Spinner)findViewById(R.id.spinnerSortBy);

		final Button clearButton = (Button) findViewById(R.id.clearButton);
		final Button search = (Button) findViewById(R.id.searchButton);


		final TextView keyword_err = (TextView)findViewById(R.id.keyword_error);
		final TextView price_err = (TextView)findViewById(R.id.price_negative_error);
		final TextView price_max_min_err = (TextView)findViewById(R.id.price_min_max_error);
		final TextView no_result_err = (TextView)findViewById(R.id.no_results_error);


		keyword_err.setTextColor(Color.parseColor("#00000000"));
		price_err.setTextColor(Color.parseColor("#00000000"));
		price_max_min_err.setTextColor(Color.parseColor("#00000000"));
		no_result_err.setTextColor(Color.parseColor("#00000000"));

		Spinner spinner = (Spinner) findViewById(R.id.spinnerSortBy);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.sort_by_values, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				keyword_err.setTextColor(Color.parseColor("#00000000"));
				price_err.setTextColor(Color.parseColor("#00000000"));
				price_max_min_err.setTextColor(Color.parseColor("#00000000"));
				no_result_err.setTextColor(Color.parseColor("#00000000"));
				keyword.setText("");
				price_from.setText("");
				price_to.setText("");
				sorty_By_Spinner.setSelection(0);

				
			}
		
		});

		

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyword_err.setTextColor(Color.parseColor("#00000000"));
				price_err.setTextColor(Color.parseColor("#00000000"));
				price_max_min_err.setTextColor(Color.parseColor("#00000000"));
				no_result_err.setTextColor(Color.parseColor("#00000000"));
				Boolean flag = true;
				ebay_keyword = keyword.getText().toString();
				entered_text = ebay_keyword;
				ebay_price_from = price_from.getText().toString();
				ebay_price_to = price_to.getText().toString();
				ebay_sort_by= sorty_By_Spinner.getItemAtPosition(sorty_By_Spinner.getSelectedItemPosition()).toString();
				
				if(ebay_keyword.trim().equals(""))
					keyword_err.setTextColor(Color.parseColor("#FF0000"));
				if(ebay_sort_by.equals("Price: highest first"))
				{
					ebay_sort_val = "CurrentPriceHighest";
				}
				else if(ebay_sort_by.equals("Price + Shipping: highest first"))
				{
					ebay_sort_val ="PricePlusShippingHighest";
				}
				else if(ebay_sort_by.equals("Price + Shipping: lowest first"))
				{
					ebay_sort_val ="PricePlusShippingLowest";
				}
				else
					ebay_sort_val ="BestMatch";

				
				if(ebay_keyword.trim().equals("")){
					keyword_err.setTextColor(Color.parseColor("#FF0000"));
					flag = false;
				}
				Toast.makeText(getApplicationContext(), ebay_keyword+ebay_sort_val, Toast.LENGTH_LONG).show();
				if(!ebay_price_from.trim().equals(""))
					{
						if(Float.parseFloat(ebay_price_from.trim())< 0.0)
						{
							price_err.setTextColor(Color.parseColor("#FF0000"));
							flag = false;
						}
					}

					if(!ebay_price_from.trim().equals(""))
					{	 
						if(!ebay_price_to.trim().equals("") )
						{
							if(Float.parseFloat(ebay_price_to.trim()) < Float.parseFloat(ebay_price_from.trim()))
							{
								price_max_min_err.setTextColor(Color.parseColor("#FF0000"));
								flag = false;
							}
							else
							{
								if(Float.parseFloat(ebay_price_to.trim()) < 0.0 ){
									price_err.setTextColor(Color.parseColor("#00000000"));
									flag = false;
								}
							}

						}
					}



					if(flag)
					{
						String keyword_parts[] = ebay_keyword.split(" ");
						ebay_keyword="";
						for(int i=0;i<keyword_parts.length;i++)
						{
							ebay_keyword += keyword_parts[i];
							if(i!=keyword_parts.length-1)
								ebay_keyword += "+";
						}

						String sortBy_parts[] = ebay_sort_by.split(" ");
						ebay_sort_by="";
						for(int i=0;i<sortBy_parts.length;i++){
							ebay_sort_by += sortBy_parts[i];

						}


						Toast.makeText(getApplicationContext(), "Getting details from Ebay", Toast.LENGTH_SHORT).show();

						String msg = fetchDetailsFromEbay(ebay_keyword, ebay_sort_val, ebay_price_from, ebay_price_to);

						if(msg.equals("error")){
							no_result_err.setTextColor(Color.parseColor("#FF0000"));
						}

					}	
				}
			});

		}
		public String fetchDetailsFromEbay(String keyword, String sort_by, String ebay_price_from, String ebay_price_to){
			//Toast.makeText(getApplicationContext(), "In start of func", Toast.LENGTH_LONG).show();
			GetEbayItemDetails getDetails = new GetEbayItemDetails(keyword, sort_by, ebay_price_from, ebay_price_to);
			String msg = "error";

			try {
				String eBay_obj = getDetails.execute(getApplicationContext()).get();
				
				JSONObject jsonObj = new JSONObject(eBay_obj);

				System.out.println("ackkkkkkkkkkkkkkkkkkk" + jsonObj.getString("ack"));
				if(jsonObj.getString("ack").equals("No results found") ){
					msg = "error";
				}else if(jsonObj.getString("ack").equals("Success")) {
					msg = "ok";
					Toast.makeText(getApplicationContext(), jsonObj.getString("ack"), Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), ScrollableItemListDetails.class);
					intent.putExtra("ebay_object", eBay_obj);
					intent.putExtra("searchedString", entered_text);
					startActivity(intent);	
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("One", "*****************************ERRRRRRRRRR1");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("One", "*****************************ERRRRRRRRR2");
			} 
			Log.d("One", "*****************************five");
			return msg;
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}


		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//parent.getSelectedItem();
		}


		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}
	class GetEbayItemDetails extends AsyncTask<Context, String, String>{

		Context ctx;
		String keyword;
		String sort_by_val;
		String price_from;
		String price_to;
		String jsonObj = "empty";
		Activity activity;

		public GetEbayItemDetails(String keyword, String sort_by_val, String price_from, String price_to)
		{
			this.keyword = keyword;
			this.sort_by_val = sort_by_val;
			this.price_from = price_from;
			this.price_to = price_to;
		}




		@Override
		protected String doInBackground(Context... arg0){
			// TODO Auto-generated method stub

			Log.d("One", "*****************************async 1");
			ctx = arg0[0];
			try{
				String url = "http://ebayitemsearch-env.elasticbeanstalk.com/responseGet.php?key_words="+keyword+
						"&low_price_range="+price_from+ "&high_price_range="+price_to+ 
						"&sortOrder="+sort_by_val+ "&results_per_page=5&page_Number=1";
				Log.d("One", "*****************************async 2");
				HttpClient http = new DefaultHttpClient();
				Log.d("One", "*****************************async 3");
				HttpGet http_get = new HttpGet(url);
				Log.d("One", "*****************************async 4");
				HttpResponse http_resp = http.execute(http_get);
				Log.d("One", "*****************************async 5");
				HttpEntity http_ent = http_resp.getEntity();
				Log.d("One", "*****************************async 6");
				BufferedReader br = new BufferedReader(new InputStreamReader(http_ent.getContent()));
				jsonObj = br.readLine();
				Log.d("One", "*****************************async 7");

			}catch(Exception e){
				jsonObj = e.getMessage();
				Log.e("log_tag", "Error in http connection", e);
				Log.d("One", "*****************************async ERRRRRR");
			}
			//publishProgress(jsonObj);
			Log.d("One", "*****************************async 8");
			return jsonObj;
		}
	}

