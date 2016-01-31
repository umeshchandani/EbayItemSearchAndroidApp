package com.example.ebay_item_search;

import org.json.JSONException;
import org.json.JSONObject;

public class Items {
	
	
	public class BasicInfo{
		public String title;
		public String category_name;
		public String condition;
		public String buying_format;
		public String top_rated;
		public String gallery_url;
		public String view_item_url;
	    public String shipping_price;
		public String supersize_url;
		public String location;
		public String converted_price;
		
		public BasicInfo(JSONObject basic_info){
			try {
				title = basic_info.getString("title");
				category_name = basic_info.getString("categoryName");
				condition = basic_info.getString("conditionDisplayName");
				buying_format = basic_info.getString("listingType");
				top_rated = basic_info.getString("topRatedListing");
				gallery_url = basic_info.getString("galleryURL");
				view_item_url = basic_info.getString("viewItemURL");
				shipping_price = basic_info.getString("shippingServiceCost");
				supersize_url = basic_info.getString("pictureURLSuperSize");
				location = basic_info.getString("location");
				converted_price = basic_info.getString("convertedCurrentPrice");
				

				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public class SellerInfo{

        public String seller_user_name;
        public String feedback_score;
        public String positive_feedback_percent;
        public String feedback_rating_star;
        public String top_rated_seller;
        public String seller_Store_name;


        
        public  SellerInfo(JSONObject seller_info){
        	
        	try {
				seller_user_name = seller_info.getString("sellerUserName");
		      	feedback_score = seller_info.getString("feedbackScore");
	        	positive_feedback_percent = seller_info.getString("positiveFeedbackPercent");
	        	feedback_rating_star = seller_info.getString("feedbackRatingStar");
	        	top_rated_seller = seller_info.getString("topRatedSeller");
	        	seller_Store_name = seller_info.getString("sellerStoreName");
	        
	        	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
        
        }

	}
	
	public class ShippingInfo{
		
		   public String shipping_type;
	       public String ship_to_locations;
	       public String expedited_shipping;
	       public String one_day_shipping_available;
	       public String returns_accepted;
	       public String handling_time;
	        
	       public  ShippingInfo(JSONObject shipping_info){
	        	
	        	try {
		
				      shipping_type = shipping_info.getString("shippingType");
				      ship_to_locations = shipping_info.getString("shipToLocations");
				      expedited_shipping = shipping_info.getString("expeditedShipping");
				      one_day_shipping_available = shipping_info.getString("oneDayShippingAvailable");
				      returns_accepted = shipping_info.getString("returnsAccepted");
				      handling_time = shipping_info.getString("handlingTime");
				      
	        		} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	        		}
	        
	        }

	}
	
	public BasicInfo basic_info;
	public SellerInfo seller_info;
	public ShippingInfo shipping_info;
	
	
	public Items(JSONObject item)
	{
		try {
			this.basic_info = new BasicInfo(item.getJSONObject("basicInfo"));
			this.seller_info = new SellerInfo(item.getJSONObject("sellerInfo"));
			this.shipping_info = new ShippingInfo(item.getJSONObject("shippingInfo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}


