package com.stev.smart_community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.stev.smart_community.community.CommunityPage;
import com.stev.smart_community.home.HomePage;
import com.stev.smart_community.me.MePage;
import com.stev.smart_community.server.ServerPage;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private HomePage homePage;
	private CommunityPage communityPage;
	private	ServerPage serverPage;
	private MePage mePage;
	
	private ImageView ivHome;
	private ImageView ivCommunity;
	private ImageView ivServer;
	private ImageView ivMe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView(){
		ivHome = (ImageView) findViewById(R.id.iv_home);
		ivServer = (ImageView) findViewById(R.id.iv_server);
		ivCommunity = (ImageView) findViewById(R.id.iv_community);
		ivMe = (ImageView) findViewById(R.id.iv_me);
		
		ivHome.setOnClickListener(this);
		ivServer.setOnClickListener(this);
		ivCommunity.setOnClickListener(this);
		ivMe.setOnClickListener(this);
		
		if (homePage == null) {
			homePage = new HomePage();
			addFragment(homePage);
			showFragment(homePage);
		} else {
			showFragment(homePage);
		}
		ivHome.setImageResource(R.drawable.bt_home_press);
	}
	
	@Override
	public void onClick(View v) {
		ivHome.setImageResource(R.drawable.bt_home_normal);
		ivServer.setImageResource(R.drawable.bt_server_normal);
		ivCommunity.setImageResource(R.drawable.bt_community_normal);
		ivMe.setImageResource(R.drawable.bt_me_normal);
		
		switch (v.getId()) {
		case R.id.iv_home:
			if (homePage == null) {
				homePage = new HomePage();
				addFragment(homePage);
				showFragment(homePage);
			} else {
				if (homePage.isHidden()) {
					showFragment(homePage);
				}
			}
			ivHome.setImageResource(R.drawable.bt_home_press);
			break;
		case R.id.iv_server:
			if (serverPage == null) {
				serverPage = new ServerPage();
				if (!serverPage.isHidden()) {
					addFragment(serverPage);
					showFragment(serverPage);
				}
			} else {
				if (serverPage.isHidden()) {
					showFragment(serverPage);
				}
			}
			ivServer.setImageResource(R.drawable.bt_server_pressed);
			break;
		case R.id.iv_community:
			if (communityPage != null) {
				removeFragment(communityPage);
				communityPage = null;
			}
			communityPage = new CommunityPage();
			addFragment(communityPage);
			showFragment(communityPage);
			ivCommunity.setImageResource(R.drawable.bt_community_pressed);
			break;
		case R.id.iv_me:
			if (mePage == null) {
				mePage = new MePage();
				if (!mePage.isHidden()) {
					addFragment(mePage);
					showFragment(mePage);
				}
			} else {
				if (mePage.isHidden()) {
					showFragment(mePage);
				}
			}
			ivMe.setImageResource(R.drawable.bt_me_pressed);
			break;
		}
	}

	public void addFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.main_rl_layout, fragment);
		fragmentTransaction.commit();
	}

	public void removeFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}

	public void showFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
		if (homePage != null) {
			fragmentTransaction.hide(homePage);
		}
		if (serverPage != null) {
			fragmentTransaction.hide(serverPage);
		}
		if (communityPage != null) {
			fragmentTransaction.hide(communityPage);
		}
		if (mePage != null) {
			fragmentTransaction.hide(mePage);
		}

		fragmentTransaction.show(fragment);
		fragmentTransaction.commitAllowingStateLoss();

	}

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
}
