package com.bgs.homeshare.ui.invites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvitationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InvitationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Loading Invitations");
    }

    public LiveData<String> getText() {
        return mText;
    }
}