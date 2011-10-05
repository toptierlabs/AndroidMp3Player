package com.toptier;



import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class Mp3Player extends Activity implements
SeekBar.OnSeekBarChangeListener, OnClickListener, OnCompletionListener{
	private SeekBar seekBar;
	private MediaPlayer mediaPlayer;
	private ImageButton bPlayPause;
	private String mp3Name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		
		Bundle gotBasket = getIntent().getExtras();
		mp3Name = gotBasket.getString("mp3");
		
		bPlayPause = (ImageButton) findViewById(R.id.ibPlayPause);
		seekBar = (SeekBar) findViewById(R.id.sbProgress);
		
		bPlayPause.setOnClickListener(this);
		
		seekBar.setEnabled(false);
		
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mediaPlayer.stop();
		finish();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("Resume", "Resume");
	}



	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		 if(fromUser){
			 mediaPlayer.seekTo(progress);
		 }
	}
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	public void onClick(View v) {
		if (v.getId() == R.id.ibPlayPause) {
			if (mediaPlayer == null)
			{
				Resources resources = getResources();
				int id = resources.getIdentifier( "com.toptier:raw/" + mp3Name, "raw", this.getPackageName());
				mediaPlayer = MediaPlayer.create(Mp3Player.this, id);
				mediaPlayer.setOnCompletionListener(this);
				seekBar.setOnSeekBarChangeListener(this);
				seekBar.setEnabled(true);
			}
			if (!mediaPlayer.isPlaying())
			{
				bPlayPause.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player_pause));

				
				mediaPlayer.start(); // no need to call prepare(); create() does that for you
				
				seekBar.setVisibility(ProgressBar.VISIBLE);
				seekBar.setProgress(mediaPlayer.getCurrentPosition());
				seekBar.setMax(mediaPlayer.getDuration() - 10);
				
				
				new Thread(new Runnable(){

					public void run() {
						int currentPosition = 0;
				        int total = mediaPlayer.getDuration();
				        seekBar.setMax(total - 10);
				        while (mediaPlayer != null && currentPosition < total) {
				            try {
				                Thread.sleep(300);
				                currentPosition = mediaPlayer.getCurrentPosition();
				            } catch (InterruptedException e) {
				                return;
				            } catch (Exception e) {
				                return;
				            }
				            seekBar.setProgress(currentPosition);
				        }
						
					}
					
				}

						).start();
			}
			else
			{
				bPlayPause.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player_play));
				mediaPlayer.pause();
				
			}
		}
		
	}
	public void onCompletion(MediaPlayer mp) {
		seekBar.setProgress(0);
		mediaPlayer.seekTo(0);
		bPlayPause.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player_play));
	}

}



















