package com.app1;

import com.example.intent_componentname.R;
import com.example.intent_componentname.R.layout;
import com.example.intent_componentname.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class MyActivity extends Activity {

	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		
		
		Intent intent=this.getIntent();
		//ComponentName cn=intent.getComponent();
		//String packagename=cn.getPackageName();
		//String classname=cn.getClassName();
		String action=intent.getAction();
		tv=(TextView)findViewById(R.id.textView1);
		//tv.setText("packagename: "+packagename+"\n"+"classname: "+classname);
		tv.setText(action);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

}
