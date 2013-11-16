package com.lancefluger.activitys;
/**
 * Based on Facebook's Sample FriendPickerSampleActivity -- Facebook Apache license 
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.facebook.AppEventsLogger;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Session;
import com.lancefluger.R;
import com.lancefluger.adapters.MyFacebookAdapter;
import com.lancefluger.global.FriendPickerApplication;

/*
 * This class is the main start activity where users can find friends, 
 * see friends selected, and choose to play a game
 */
public class FriendPickerSampleActivity extends FragmentActivity {
	private static final int PICK_FRIENDS_ACTIVITY = 1;
	private Button pickFriendsButton;
	private ListView friendListView;
	private MyFacebookAdapter myFacebookAdapter;
	private UiLifecycleHelper lifecycleHelper;
	boolean pickFriendsWhenSessionOpened;
	private Button playGameButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		pickFriendsButton = (Button) findViewById(R.id.pickFriendsButton);
		pickFriendsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				onClickPickFriends();
			}
		});

		playGameButton = (Button) findViewById(R.id.playFriendsButton);
		playGameButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				FriendPickerSampleActivity.this.startActivity(new Intent(
						FriendPickerSampleActivity.this,
						TicTacGameActivity.class));

			}
		});

		lifecycleHelper = new UiLifecycleHelper(this,
				new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						onSessionStateChanged(session, state, exception);
					}
				});
		lifecycleHelper.onCreate(savedInstanceState);

		ensureOpenSession();
		friendListView = (ListView) findViewById(R.id.list);

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Call the 'activateApp' method to log an app event for use in
		// analytics and advertising reporting. Do so in
		// the onResume methods of the primary Activities that an app may be
		// launched into.
		AppEventsLogger.activateApp(this);

		
		// Only play if there are 2 users
		FriendPickerApplication application = (FriendPickerApplication) getApplication();
		if (application.getSelectedUsers() == null) {
			playGameButton.setEnabled(false);
		} else {
			if (application.getSelectedUsers().size() < 2) {
				playGameButton.setEnabled(false);
			} else {
				playGameButton.setEnabled(true);
			}

		}

		// using custom adapter; passing application's user data collection
		// TBD_2: Bit heavy weight, optimize later
		myFacebookAdapter = new MyFacebookAdapter(this,
				application.getSelectedUsers());
		friendListView.setAdapter(myFacebookAdapter);

	}


	/*
	 * onActivityResult - Callback after done selecting friends; can be further cleaned or reused
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

		default:
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
			break;
		}
	}

	/*
	 * ensureOpenSession - Facebook's session check
	 */
	private boolean ensureOpenSession() {
		if (Session.getActiveSession() == null
				|| !Session.getActiveSession().isOpened()) {
			Session.openActiveSession(this, true, new Session.StatusCallback() {
				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					onSessionStateChanged(session, state, exception);
				}
			});
			return false;
		}
		return true;
	}

	/*
	 * onSessionStateChanged -- Facebook framework
	 */
	private void onSessionStateChanged(Session session, SessionState state,
			Exception exception) {
		if (pickFriendsWhenSessionOpened && state.isOpened()) {
			pickFriendsWhenSessionOpened = false;

			startPickFriendsActivity();
		}
	}

	/*
	 * onClickPickFriends -- Facebook framework
	 */
	private void onClickPickFriends() {
		startPickFriendsActivity();
	}

	
	/*
	 * startPickFriendsActivity -- Facebook's call into their picker activity
	 */
	private void startPickFriendsActivity() {
		if (ensureOpenSession()) {
			FriendPickerApplication application = (FriendPickerApplication) getApplication();
			application.setSelectedUsers(null);

			Intent intent = new Intent(this, PickFriendsActivity.class);
			// Note: The following line is optional, as multi-select behavior is
			// the default for
			// FriendPickerFragment. It is here to demonstrate how parameters
			// could be passed to the
			// friend picker if single-select functionality was desired, or if a
			// different user ID was
			// desired (for instance, to see friends of a friend).
			PickFriendsActivity.populateParameters(intent, null, true, true);
			startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
		} else {
			pickFriendsWhenSessionOpened = true;
		}
	}
}
