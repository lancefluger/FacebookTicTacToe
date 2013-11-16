package com.lancefluger.activitys;

/**
 * 
 * TicTacGameActivity -- Apache license
 * 
 */
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.lancefluger.R;
import com.lancefluger.global.FriendPickerApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * This class represents a simple tictactoe game
 * 
 */
public class TicTacGameActivity extends Activity {

	private boolean xTurn = true;
	private char board[][] = new char[3][3];
	private DialogInterface.OnClickListener dialogClickListener;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game_layout);
		dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					// TBD_5: Add win totals, play again?
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					TicTacGameActivity.this.finish();
					break;
				default:
					break;
				}
			}
		};
		setupOnClickListeners();

	}

	/**
	 * This will add the OnClickListener to each button inside out TableLayout
	 */
	private void setupOnClickListeners() {
		TableLayout table = (TableLayout) findViewById(R.id.gameBoard);
		for (int y = 0; y < table.getChildCount(); y++) {
			if (table.getChildAt(y) instanceof TableRow) {
				TableRow R = (TableRow) table.getChildAt(y);
				for (int x = 0; x < R.getChildCount(); x++) {
					View V = R.getChildAt(x);
					V.setOnClickListener(new ClickSquare(x, y));

				}
			}
		}
	}

	/**
	 * isGameOver
	 * 
	 * @param char[][] board
	 * @param int size
	 * @param char player
	 * @return
	 */
	private boolean isGameOver(char[][] board, int size, char player) {
		for (int x = 0; x < size; x++) {
			int total = 0;
			for (int y = 0; y < size; y++) {
				if (board[x][y] == player) {
					total++;
				}
			}
			if (total >= size) {
				return true;
			}
		}

		for (int y = 0; y < size; y++) {
			int total = 0;
			for (int x = 0; x < size; x++) {
				if (board[x][y] == player) {
					total++;
				}
			}
			if (total >= size) {
				return true;
			}
		}

		int total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x == y && board[x][y] == player) {
					total++;
				}
			}
		}
		if (total >= size) {
			return true;
		}

		total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x + y == size - 1 && board[x][y] == player) {
					total++;
				}
			}
		}
		if (total >= size) {
			return true;
		}

		return false;
	}

	/**
	 * checkBoard
	 * 
	 * @return boolean
	 */
	private boolean checkBoard() {

		String winner = "none";
		AlertDialog.Builder builder;
		count++;
		FriendPickerApplication application = (FriendPickerApplication) getApplication();
		GraphUser[] userArray = application.getSelectedUsers().toArray(
				new GraphUser[0]);

		if (isGameOver(board, 3, 'X')) {
			winner = userArray[0].getName();
		} else if (isGameOver(board, 3, 'O')) {
			winner = userArray[1].getName();
		}

		/*
		 * if (count == 9) { builder = new AlertDialog.Builder(this);
		 * builder.setMessage("Draw") .setPositiveButton("Ok",
		 * dialogClickListener) .setNegativeButton("Quit",
		 * dialogClickListener).show(); }
		 */

		if (winner.equals("none")) {
			if (count == 9) {
				builder = new AlertDialog.Builder(this);
				builder.setMessage( "Draw -- No one won")
				.setPositiveButton("Ok", dialogClickListener)
				.setNegativeButton("Quit", dialogClickListener).show();
				return false;
			} else {
				return false;
			}
		} else {

			builder = new AlertDialog.Builder(this);
			builder.setMessage(winner + " won")
			.setPositiveButton("Ok", dialogClickListener)
			.setNegativeButton("Quit", dialogClickListener).show();

			return true;
		}
	}

	/**
	 * Private class for handing clicking on a square
	 */
	private class ClickSquare implements View.OnClickListener {

		private int x = 0;
		private int y = 0;

		/**
		 * ClickSquare
		 * 
		 * @param int x
		 * @param int y
		 */
		public ClickSquare(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void onClick(View view) {

			if (view instanceof ProfilePictureView) {
				ProfilePictureView profilePicture = (ProfilePictureView) view;
				board[x][y] = xTurn ? 'X' : 'O';

				FriendPickerApplication application = (FriendPickerApplication) getApplication();
				if (application.getSelectedUsers() == null)
					return;

				GraphUser[] userArray = application.getSelectedUsers().toArray(
						new GraphUser[0]);

				String profileId = "";
				if (xTurn) {
					profileId = userArray[0].getId();
				} else {
					profileId = userArray[1].getId();
				}

				// set profile picture using profileId
				// TBD_3: Use caching here so do not need to query network to
				// load picture
				profilePicture.setProfileId(profileId);
				profilePicture.setEnabled(false);
				xTurn = !xTurn;

				checkBoard();

			}
		}

	}
}
