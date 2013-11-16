package com.lancefluger.adapters;
/**
 * 
 * MyFacebookAdapter -- Apache license
 * 
 */

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.lancefluger.R;

/**
 * This class represents a custom adapter for connecting to a listview 
 */
public class MyFacebookAdapter extends BaseAdapter {

	private Activity activity;
	private Collection<GraphUser> data;
	private static LayoutInflater inflater = null;

	/**
	 * MyFacebookAdapter constructor
	 * @param activity
	 * @param data
	 */
	public MyFacebookAdapter(Activity activity, Collection<GraphUser> data) {
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/**
	 * getCount
	 * @return int size
	 */
	public int getCount() {
		if (data == null)
			return 0;

		return data.size();
	}

	/**
	 * getItem
	 * @param int position
	 * @return Object item
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * getItemId
	 * @param int position
	 * @return long id
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * getView
	 * @param int position
	 * @param View convertView
	 * @param ViewGroup parent
	 * @return View theView
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);

		TextView friendName = (TextView) vi.findViewById(R.id.friendName); // title
		TextView wins = (TextView) vi.findViewById(R.id.wins); // artist name
		ProfilePictureView profilePicture = (ProfilePictureView) vi
				.findViewById(R.id.friendProfilePicture); // duration

		if (position >= data.size()) {
			return null;
		}

		GraphUser[] userArray = data.toArray(new GraphUser[0]);
		friendName.setText(userArray[position].getName());
		wins.setText("Wins: 0");

		profilePicture.setProfileId(userArray[position].getId());
		// TBD_1: Cache image

		return vi;
	}
}