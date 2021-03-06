package com.gamefreezer.applet.galaga;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.gamefreezer.galaga.AbstractBitmap;
import com.gamefreezer.galaga.AbstractBitmapReader;

public class AppletBitmapReader extends AbstractBitmapReader {

    private String imgDir = "imgs/";

    @Override
    public AbstractBitmap read(String name) {
	AppletBitmap bitmap = new AppletBitmap(loadImage(name));
	return bitmap;
    }

    private BufferedImage loadImage(String name) {
	URL urlObject = getUrlObject(name);
	assert urlObject != null : "url cannot be null";
	BufferedImage image = null;
	image = readImageFromUrlObject(urlObject);
	assert image != null : "image cannot be null";
	return image;
    }

    private BufferedImage readImageFromUrlObject(URL urlObject) {
	BufferedImage image = null;
	try {
	    image = ImageIO.read(urlObject);
	} catch (IOException e) {
	    throw (new IllegalArgumentException(urlObject + " " + e));
	}
	return image;
    }

    private URL getUrlObject(String name) {
	URL urlObject = null;
	try {
	    urlObject = new URL(this.getClass().getResource(imgDir + name)
		    .toString());
	} catch (MalformedURLException e) {
	    throw (new IllegalArgumentException(name + " " + e));
	} catch (NullPointerException e2) {
	    throw (new IllegalArgumentException(name + " " + e2));
	}
	return urlObject;
    }
}
