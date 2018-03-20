package com.irinnovative.onepagesigninsignup;

import android.widget.ImageView;

/**
 * Created by Manveer's HP on 3/20/2018.
 */

public class DataModel {

    public String name;
    boolean checked;
    int img;

    DataModel(String name, boolean checked,int img) {
        this.name = name;
        this.checked = checked;
        this.img=img;
    }
}
