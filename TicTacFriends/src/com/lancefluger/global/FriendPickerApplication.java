package com.lancefluger.global;
/**
 * Based on Facebook's Sample FriendPickerApplication -- Facebook Apache license 
 */


import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.model.GraphUser;

import java.util.Collection;

// Facebook documentation
// We use a custom Application class to store our minimal state data (which users have been selected).
// A real-world application will likely require a more robust data model.
public class FriendPickerApplication extends Application {
    private Collection<GraphUser> selectedUsers;

    public Collection<GraphUser> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(Collection<GraphUser> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    
}
