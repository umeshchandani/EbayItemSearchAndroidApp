package com.example.ebay_item_search;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EbayListAdapter extends ArrayAdapter {


	private LayoutInflater inflater;
	private Items[] items;

    @SuppressWarnings("unchecked")
	public EbayListAdapter(Activity activity, Items[] items){
       super(activity, R.layout.item_list, items);
       inflater = activity.getWindow().getLayoutInflater();
       this.items = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	

    		  View row=inflater.inflate(R.layout.item_list,parent,false);
    		  ImageView icon= (ImageView) row.findViewById(R.id.icon);
    		  //icon.setImageResource(R.drawable.messenger_bubble_large_blue);
    		  TextView title = (TextView) row.findViewById(R.id.title);
    		  

    		  new DownloadImageTask(icon).execute(items[position].basic_info.gallery_url);
    		  title.setText(items[position].basic_info.title);
    		  
    		  
  			TextView price = (TextView) row.findViewById(R.id.price);
  			String priceStr = "Price $ " + items[position].basic_info.converted_price;
  			priceStr += "(";
  			if(items[position].basic_info.shipping_price.equals("0")|| items[position].basic_info.shipping_price.equals("0.0") || items[position].basic_info.shipping_price.equals("")  )
  			priceStr += "FREE SHIPPING";
  			else
  			priceStr += "$" +items[position].basic_info.shipping_price + " for shipping";
  			
  			priceStr += ")";
  			
  			price.setText(priceStr);
    		  
    		  
    		  return row;

    }


}
