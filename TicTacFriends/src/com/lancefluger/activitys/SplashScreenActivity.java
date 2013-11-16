package com.lancefluger.activitys;
/**
 * 
 * StartActivity -- Apache license
 * 
 */

import com.lancefluger.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * This class represents a custom splash screen that is shown for so many seconds 
 */
public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_layout);
		// TBD_0: Move facebook authentication into splashscreen activity

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {

					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				return null;
			}

			// After done sleeping proceed to next activity
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				SplashScreenActivity.this.startActivity(new Intent(
						SplashScreenActivity.this,
						FriendPickerSampleActivity.class));
				finish();
			}

		}.execute();

	}

}
