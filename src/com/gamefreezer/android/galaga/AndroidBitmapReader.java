package com.gamefreezer.android.galaga;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.gamefreezer.galaga.AbstractBitmap;
import com.gamefreezer.galaga.AbstractBitmapReader;

public class AndroidBitmapReader extends AbstractBitmapReader {

    private Context context;

    public AndroidBitmapReader(Context context) {
	this.context = context;
    }

    @Override
    public AbstractBitmap read(String name) {
	final String truncated = name.substring(0, name.lastIndexOf('.'));
	int resID = context.getResources().getIdentifier(truncated, "drawable",
		context.getPackageName());

	return new AndroidBitmap(BitmapFactory.decodeResource(context
		.getResources(), resID));
    }
}
