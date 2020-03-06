package com.adalbert.JSOZ.view.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.IntBinaryOperator;

import javax.swing.ImageIcon;

public class PlaceholderRenderer {
	
	public static IntBinaryOperator simpleFunction = (x,y) -> {
		int red = (int)(((double) x / ViewFinals.LOGO_WIDTH) * 255);
		int green = (int)(((double) y / ViewFinals.LOGO_HEIGHT) * 255);
		return (new Color(red, green, 0)).getRGB();
	};

	public static ImageIcon getIcon(IntBinaryOperator op) {
		BufferedImage image = new BufferedImage(ViewFinals.LOGO_WIDTH, ViewFinals.LOGO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < ViewFinals.LOGO_HEIGHT; y++)
			for (int x = 0; x < ViewFinals.LOGO_WIDTH; x++)
				image.setRGB(x, y, op.applyAsInt(x, y));
		return new ImageIcon(image);
	}
	
	public static ImageIcon getIcon() {
		BufferedImage image = new BufferedImage(ViewFinals.LOGO_WIDTH, ViewFinals.LOGO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		return new ImageIcon(image);
	}
	
}
