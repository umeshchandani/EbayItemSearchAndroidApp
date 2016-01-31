package com.example.ebay_item_search;

import com.example.ebay_item_search.Items.SellerInfo;
import com.example.ebay_item_search.Items.ShippingInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ShippingInfoTab extends Fragment {
	ShippingInfo selected_shippingInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selected_shippingInfo = TabsActivity.getShippingInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	System.out.println("Here");
        View v = inflater.inflate(R.layout.shipping_info_tab, container, false);
        
        TextView shippingType = (TextView) v.findViewById(R.id.shippingTypeText);
        
        if(!selected_shippingInfo.shipping_type.isEmpty())
        {
        	shippingType.setText(selected_shippingInfo.shipping_type);
        }
        else
        	shippingType.setText("N/A");
        
       
        TextView handlingTime = (TextView) v.findViewById(R.id.handlingTimeText);
        if(!selected_shippingInfo.handling_time.isEmpty())
        {
        	handlingTime.setText(selected_shippingInfo.handling_time);
        }
        else
        	handlingTime.setText("N/A");
        
        
        TextView shipping_locations = (TextView) v.findViewById(R.id.shippingLocationText);
        
        if(!selected_shippingInfo.ship_to_locations.isEmpty())
        {
        	 shipping_locations.setText(selected_shippingInfo.ship_to_locations);
        }
        else
        	shipping_locations.setText("N/A");
        

        ImageView expeditedShipping = (ImageView) v.findViewById(R.id.expeditedShippingText);
        if(selected_shippingInfo.expedited_shipping.compareTo("true") == 0)
        {
        	expeditedShipping.setBackgroundResource(R.drawable.green_check2);
        }
        else
        	expeditedShipping.setBackgroundResource(R.drawable.red_cross_tick);
        //expeditedShipping.setText(selected_shippingInfo.expedited_shipping);
        
        ImageView oneDayShipping = (ImageView) v.findViewById(R.id.oneDayShippingText);
       // oneDayShipping.setText(selected_shippingInfo.one_day_shipping_available);
        if(selected_shippingInfo.one_day_shipping_available.compareTo("true") == 0)
        {
        	oneDayShipping.setBackgroundResource(R.drawable.green_check2);
        }
        else
        	oneDayShipping.setBackgroundResource(R.drawable.red_cross_tick);
        
        ImageView returnsAccepted = (ImageView) v.findViewById(R.id.returnsAcceptedText);
        if(selected_shippingInfo.one_day_shipping_available.compareTo("true") == 0)
        {
        	returnsAccepted.setBackgroundResource(R.drawable.green_check2);
        }
        else
        	returnsAccepted.setBackgroundResource(R.drawable.red_cross_tick);
        
        
       // returnsAccepted.setText(selected_shippingInfo.returns_accepted);

        return v;
    }
}