package com.example.ebay_item_search;

import com.example.ebay_item_search.Items.BasicInfo;
import com.example.ebay_item_search.Items.SellerInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class SellerInfoTab extends Fragment {
	SellerInfo selected_sellerInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selected_sellerInfo = TabsActivity.getSellerInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    
        View v = inflater.inflate(R.layout.seller_info_tab, container, false);
        
        TextView userName = (TextView) v.findViewById(R.id.userNameText);
        if(!selected_sellerInfo.seller_user_name.isEmpty())
        {
        	 userName.setText(selected_sellerInfo.seller_user_name);
        }
        else
        	userName.setText("N/A");
       
        TextView feedbackScore = (TextView) v.findViewById(R.id.feedbackText);
        if(!selected_sellerInfo.feedback_score.isEmpty())
        {
        	feedbackScore.setText(selected_sellerInfo.feedback_score);
        }
        else
        	feedbackScore.setText("N/A");
        
        
        TextView positive_feedback_text = (TextView) v.findViewById(R.id.positiveFeedbackText);
        
        if(!selected_sellerInfo.positive_feedback_percent.isEmpty())
        {
        	positive_feedback_text.setText(selected_sellerInfo.positive_feedback_percent);
        }
        else
        	positive_feedback_text.setText("N/A");
        
        TextView feedbackRating = (TextView) v.findViewById(R.id.feedbackRatingText);
        if(!selected_sellerInfo.feedback_rating_star.isEmpty())
        {
        feedbackRating.setText(selected_sellerInfo.feedback_rating_star);
        }
        else
        {
        	 feedbackRating.setText("N/A");
        	
        }
        
        
        ImageView topRated = (ImageView) v.findViewById(R.id.topratedText);
       
        //topRated.setText(selected_sellerInfo.top_rated_seller);
        
        if(selected_sellerInfo.top_rated_seller.compareTo("true") == 0)
        {
        	topRated.setBackgroundResource(R.drawable.green_check1);
        }
        else
        	topRated.setBackgroundResource(R.drawable.red_cross_tick);
        
        
        TextView store = (TextView) v.findViewById(R.id.storeText);
        if(!selected_sellerInfo.seller_Store_name.isEmpty())
        {
        	store.setText(selected_sellerInfo.seller_Store_name);
        }
        else
        	store.setText("N/A");

        return v;
    }
}