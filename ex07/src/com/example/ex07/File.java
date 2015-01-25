package com.example.ex07;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class File extends ActionBarActivity {
	
	public static int MODE = MODE_PRIVATE;
	public static final String PREFERENCE_NAME = "file";
	
	private AutoCompleteTextView nameAutoCompleteTextView;
	private String[] nameStrings;
	
	private EditText file_value;
	private Button btn_save;
	private Button btn_read;
	private Button btn_delete;
	
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file);
        
        // SharedPreferences
        sharedPreferences = getPreferences(MODE);
        editor = sharedPreferences.edit();
        
        btn_save = (Button)findViewById(R.id.save);
        btn_read = (Button)findViewById(R.id.read);
        btn_delete = (Button)findViewById(R.id.delete);
        
        file_value = (EditText)findViewById(R.id.file_value);
        
        
        nameAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.file_name);
        initFileName();
        
        btn_read.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					readFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        });
        
        btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveFile();
			}
        });
        
        btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteFile();
				
			}
        	
        });
    }
    
    private void initFileName() {
    	nameStrings = this.fileList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,nameStrings);   
        nameAutoCompleteTextView.setAdapter(adapter);
    }
    
    private void saveFile() {
    	String strFileText = file_value.getText().toString();
    	String strFileName = nameAutoCompleteTextView.getText().toString();
    	FileUtils mFileUtils = new FileUtils();
    	mFileUtils.saveContent(this, strFileName, strFileText);
    	
    	initFileName();
    	file_value.setText("");
        nameAutoCompleteTextView.setText("");
    }
    
    private void readFile() throws IOException {
    	String strFileName = nameAutoCompleteTextView.getText().toString();
    	FileUtils mFileUtils = new FileUtils();
    	String fileContent = mFileUtils.readContent(this, strFileName);
    	file_value.setText(fileContent);
    	
    	initFileName();
    }
    
    private void deleteFile() {
    	String strFileName = nameAutoCompleteTextView.getText().toString();
    	FileUtils mFileUtils = new FileUtils();
    	mFileUtils.deleteFile(this, strFileName);
    	
    	initFileName();
    	file_value.setText("");
        nameAutoCompleteTextView.setText("");
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
