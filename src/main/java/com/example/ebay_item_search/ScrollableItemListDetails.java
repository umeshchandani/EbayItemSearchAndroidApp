package com.example.ebay_item_search;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ScrollableItemListDetails extends ListActivity {
	
	    private String[] stringArray ;
	    private ArrayAdapter ebayItemArrayAdapter;
	    public static String ebay_object, keyword;
	    JSONObject item_json = null;
	    Items [] item_array;
	    
	 	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.scrollable_list);
	      Bundle bundle = getIntent().getExtras();
	      ebay_object = bundle.getString("ebay_object");
	      keyword =  bundle.getString("searchedString");
	      stringArray = new String[5];
	      item_array = new Items[5];
	      
	      
	      
	      
	        for(int i=0; i < stringArray.length; i++){
	        	 try {
					item_json = new JSONObject(ebay_object);
					
					String result_str = " Results for " + "'" +keyword + "'";
					TextView result = (TextView)findViewById(R.id.results);
					result.setText(result_str);
					Log.d("One", "**************************Array error"+ ebay_object);
					item_array[i] =  new Items(item_json.getJSONObject("item"+(i+1)));
					Log.d("One", "**************************Array" + item_array[i].basic_info.title);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d("One", "**************************Array error");
					e.printStackTrace();
				}
	        	 
	        }
	      
	       ebayItemArrayAdapter = new EbayListAdapter(this, item_array);
	      setListAdapter(ebayItemArrayAdapter);
	      
	    }
	 	
	 	@Override
	 	 protected void onListItemClick(ListView l, View v, int position, long id) {
	 		final int pos= position;
	 		TextView title = (TextView) v.findViewById(R.id.title);
	 		title.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {
	 				Intent intent = new Intent(getApplicationContext(), TabsActivity.class);
	 				intent.putExtra("ebay_object",ebay_object);
	 				intent.putExtra("position", pos);
	 				startActivity(intent);
	 			}
	 		});
	 		
	 		ImageView icon = (ImageView) v.findViewById(R.id.icon);
	 		icon.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {
	 				Log.d("One", "**************************"+ item_array[pos].basic_info.view_item_url);
	 				 startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(item_array[pos].basic_info.view_item_url)));
	 			}
	 		});
	 		
	 		
	 		
	 	 }
	 	
	

}
