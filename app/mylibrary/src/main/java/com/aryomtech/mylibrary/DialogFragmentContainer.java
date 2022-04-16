package com.aryomtech.mylibrary;

import android.app.DialogFragment;

public class DialogFragmentContainer extends DialogContainer {

    DialogFragmentContainer(DialogFragment dialog) {
        super(dialog.getDialog());
    }

}