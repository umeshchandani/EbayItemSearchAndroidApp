package com.example.ebay_item_search;

import com.example.ebay_item_search.Items.BasicInfo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasicInfoTab extends Fragment {
	BasicInfo selected_basicInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selected_basicInfo = TabsActivity.getBasicInfo();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	System.out.println("Here");


        View v = inflater.inflate(R.layout.basic_info_tab, container, false);
        TextView category = (TextView) v.findViewById(R.id.categoryNameText);
        if(!selected_basicInfo.category_name.isEmpty())
        {
        	category.setText(selected_basicInfo.category_name);
        }
        else
        	category.setText("N/A");

        TextView buying_format = (TextView) v.findViewById(R.id.buyingFormatText);
        if(!selected_basicInfo.buying_format.isEmpty())
        {
        		buying_format.setText(selected_basicInfo.buying_format);
        }
        else
        	buying_format.setText("N/A");

        TextView condition = (TextView) v.findViewById(R.id.conditionNameText);
        if(!selected_basicInfo.condition.isEmpty())
        {
        	condition.setText(selected_basicInfo.condition);
        }
        else
        	condition.setText("N/A");

        return v;
    }
}