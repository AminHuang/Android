package com.example.ex07;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	
	Context tmpcontext;
	
	public static int MODE = MODE_PRIVATE;
	public static final String PREFERENCE_NAME = "userInfo";
	
	private Button login;
	private EditText user;
	private EditText password;
	private CheckBox rem_pw;
	
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tmpcontext = this;
        
        login = (Button)findViewById(R.id.login);
        user = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        rem_pw = (CheckBox)findViewById(R.id.remember);
        
        
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        editor = sharedPreferences.edit();
        int first = sharedPreferences.getInt("time",0);
        
        
    	rem_pw.setChecked(true);
        
        if (first == 0) {
        	editor.putString("USER_NAME", "android");
        	editor.putString("PASSWORD", "android");
        	editor.commit();
        }
        editor.putInt("time", ++first).commit();
        
        Boolean check = sharedPreferences.getBoolean("ISCHECK",true);
        
        if (check) {
        	user.setText(sharedPreferences.getString("USER_NAME", "android"));
        	password.setText(sharedPreferences.getString("PASSWORD", "android"));
        	
        } else {
        	user.setText("");
        	password.setText("");
        	editor.putBoolean("ISCHECK", true).commit();
        }
        
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (rem_pw.isChecked()) {
					editor.putBoolean("ISCHECK", true).commit();
					System.out.println("checkbox is changing to true");
				} else {
					editor.putBoolean("ISCHECK", false).commit();
					System.out.println("checkbox is changing to false");
				}
				
			}	
        });
        
        login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName = user.getText().toString();
				String psd = password.getText().toString();
				String realuser = sharedPreferences.getString("USER_NAME", "");
				String realpsd = sharedPreferences.getString("PASSWORD", "");
				if (userName.equals(realuser) && psd.equals(realpsd)) {
					startActivity(new Intent(MainActivity.this,File.class));
					finish();
				} else {
					Toast.makeText(tmpcontext, "µÇÂ½Ê§°Ü", Toast.LENGTH_SHORT).show();
				}
				
				
			}
        });
        
        
        
        
        
        
        
        
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
}
