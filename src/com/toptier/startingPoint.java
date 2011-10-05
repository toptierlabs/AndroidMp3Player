package com.toptier;




import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class startingPoint extends Activity implements OnItemClickListener {
	
	String musicas[];
	Properties properties;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView lv = (ListView) findViewById(R.id.lvMusicas);
        
        
        Resources resources = this.getResources();
        AssetManager assetManager = resources.getAssets();

        // Read from the /assets directory
        try {
            InputStream inputStream = assetManager.open("mp3files.properties");
            properties = new Properties();
            properties.load(inputStream);
            System.out.println("The properties are now loaded");
            System.out.println("properties: " + properties);
            musicas = new String[properties.size()];
            Enumeration<String> en = (Enumeration<String>) properties.propertyNames();
            int i = 0;
            while (en.hasMoreElements())
            {
            	musicas[i] = en.nextElement();
            	i++;
            }

        } catch (Exception e) {
            System.err.println("Failed to open microlog property file");
            e.printStackTrace();
        }
        
        
        
        lv.setAdapter(new ArrayAdapter<String>(startingPoint.this,
				android.R.layout.simple_list_item_1, musicas));
        lv.setOnItemClickListener(this);
    
    }
    

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {

		try {
			Bundle basket = new Bundle();
			basket.putString("mp3", properties.getProperty(musicas[position]));
			Intent ourIntent = new Intent(startingPoint.this, Class.forName("com.toptier.Mp3Player"));
			ourIntent.putExtras(basket);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}