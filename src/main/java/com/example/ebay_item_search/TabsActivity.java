package com.example.ebay_item_search;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.ebay_item_search.Items.BasicInfo;
import com.example.ebay_item_search.Items.SellerInfo;
import com.example.ebay_item_search.Items.ShippingInfo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class TabsActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    public static String items,priceStr, locationStr, imageURLStr ;
    JSONObject item_json = null;
    Items item_Selected;
    static BasicInfo basic_info_selected;
    static SellerInfo seller_info_selected;
    static ShippingInfo shipping_info_selected;
   
    int position;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.item_detail);
        
        
        
        //init fb
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
			
			@Override
			public void onSuccess(Result result) {
				// TODO Auto-generated method stub
				if(result.getPostId() == null)
				{
					Toast.makeText(getApplicationContext(),"Post Cancelled", Toast.LENGTH_LONG).show();
				}
				else
				Toast.makeText(getApplicationContext(),"Posted Story" + " ID:" +  result.getPostId(), Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onError(FacebookException error) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Posted Cancelled", Toast.LENGTH_LONG).show();
				
			}
		});

        items= bundle.getString("ebay_object");
        position = bundle.getInt("position");
        try {
			item_json = new JSONObject(items);
			item_Selected =  new Items(item_json.getJSONObject("item"+(position+1)));
			basic_info_selected = item_Selected.basic_info;
			shipping_info_selected = item_Selected.shipping_info;
			seller_info_selected = item_Selected.seller_info;

			ImageView center_Image = (ImageView) findViewById(R.id.imageView1);
			if(!item_Selected.basic_info.supersize_url.equals(""))
			{
				new DownloadImageTask(center_Image).execute(item_Selected.basic_info.supersize_url);
				imageURLStr = item_Selected.basic_info.supersize_url; 
			}
			else
			{
				new DownloadImageTask(center_Image).execute(item_Selected.basic_info.gallery_url);
				imageURLStr = item_Selected.basic_info.gallery_url; 
			}
				
			TextView title = (TextView) findViewById(R.id.title);
			title.setText(item_Selected.basic_info.title);
			
			TextView price = (TextView) findViewById(R.id.price);
			priceStr = "Price $ " + item_Selected.basic_info.converted_price;
			priceStr += "(";
			if(item_Selected.basic_info.shipping_price.equals("0")|| item_Selected.basic_info.shipping_price.equals("0.0") || item_Selected.basic_info.shipping_price.equals("")  )
			priceStr += "FREE SHIPPING";
			else
			priceStr += "$" +item_Selected.basic_info.shipping_price + " for shipping";
			
			priceStr += ")";
			
			price.setText(priceStr);
			
			TextView location = (TextView) findViewById(R.id.location);
			locationStr = "Location:" + item_Selected.basic_info.location;
			location.setText(item_Selected.basic_info.location);
			
			if(item_Selected.seller_info.top_rated_seller.equals("true"))
			{
				ImageView top_rated_image= (ImageView) findViewById(R.id.imageView2);
				top_rated_image.setBackgroundResource(R.drawable.item_top_rated);
			}
		
				
		
			Button icon = (Button) findViewById(R.id.imageButton1);
	 		icon.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {
	 		
	 				 startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(item_Selected.basic_info.view_item_url)));
	 			}
	 		});
	 		
	 		ImageButton facebook = (ImageButton) findViewById(R.id.facebook);
	 		facebook.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View v) {
	 		
	 				if (ShareDialog.canShow(ShareLinkContent.class)) {
	 				    ShareLinkContent linkContent = new ShareLinkContent.Builder()
	 				            .setContentTitle(item_Selected.basic_info.title)
	 				            .setContentDescription(priceStr + locationStr)
	 				            .setImageUrl(Uri.parse(imageURLStr))
	 				            .setContentUrl(Uri.parse(item_Selected.basic_info.view_item_url))
	 				            .build();

	 				    shareDialog.show(linkContent);
	 				}
	 			}
	 		});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        ImageButton facebook = (ImageButton) findViewById(R.id.facebook);
        facebook.setBackgroundResource(R.drawable.fb);
       /* new DownloadImageTask(facebook).execute("http://cs-server.usc.edu:45678/hw/hw8/fb.png");*/
        
        
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        


        Bundle newbundle = new Bundle();
        newbundle.putString("edttext", "From Activity");
        
       /* 
    
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.basic_info, R.drawable.abc_btn_rating_star_off_mtrl_alpha)),
                BasicInfoTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.seller_info,  R.drawable.abc_btn_rating_star_off_mtrl_alpha)),
                SellerInfoTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.shipping_info,  R.drawable.abc_btn_rating_star_off_mtrl_alpha)),
                ShippingInfoTab.class, null);*/
       mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Basic Info",
                      getResources().getDrawable( R.drawable.green_check1)),
                BasicInfoTab.class, newbundle);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Seller Info",
                        getResources().getDrawable(R.drawable.button_image)),
                SellerInfoTab.class, newbundle);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Shipping Info",
                        getResources().getDrawable(R.drawable.button_image)),
                ShippingInfoTab.class, newbundle);
        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#CEDEDE")); //unselected
        }
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#47A3FF")); //selected
    
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
           public void onTabChanged(String arg0) {
            Log.i("******Clickin Tab number ... ", "" + mTabHost.getCurrentTab());
            for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) {
                mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#CEDEDE")); //unselected
            }
            mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#47A3FF")); //selected


           }     
        });  
   



          }
    
   /* private void setupTab(final View view, final String tag, Intent intent) {
        View tabview = createTabView(mTabHost.getContext(), tag);
        TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
        mTabHost.addTab(tabSpec);
    }

   /* private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.test, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);      
        tv.setText(text);
        return view;
    }
    */
    private View getTabIndicator(Context context, int title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.test, null);
      
        
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setBackgroundResource(icon);
        
        tv.setText(title);
        return view;
    }
    

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public static BasicInfo getBasicInfo(){
    	return basic_info_selected;
    }
    
    public static SellerInfo getSellerInfo(){
    	return seller_info_selected;
    }
    
    public static ShippingInfo getShippingInfo(){
    	return shipping_info_selected;
    }
    
    
}