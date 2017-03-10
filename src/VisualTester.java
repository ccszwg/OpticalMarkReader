import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class VisualTester extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int currentImageIndex = 0;
	int w = 1200;
	int h = 900;

	public void setup() {
		size(w, h);
		images = PDFHelper.getPImagesFromPdf("omrtest.pdf");
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(currentImageIndex);
			image(current_image, 0, 0);		
			textSize(100);
			int myColor = color( (int)(255*Math.abs(Math.cos(frameCount/100.0))), (int)(255*Math.abs(Math.sin(frameCount/100.0))),  (int)(255*Math.abs(Math.sin(frameCount/50.0))));
			fill(myColor);
			text(mouseX + ", " + mouseY, 100, 200);
			noFill();
			stroke(myColor);
			strokeWeight(1);
			int width = 194;
			int height = 37;
			int topCornerX = 122;
			int topCornerY = 459;
			int xIncrement = 283;
			int section = (int)(width/5.0);
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 12; j++){
					int rectY = topCornerY + (j*height);
					rect(topCornerX + (xIncrement*i), rectY, width, height);
					//System.out.println("Top corner of rectangle: " + (topCornerX + (xIncrement*i)) + ", " + rectY);
					for(int s = 1; s < 5; s++){
						line(topCornerX + (section*s) + (xIncrement*i), rectY, topCornerX + (section*s) + (xIncrement*i), rectY+height);
					}
				}
			}

		}
	}

	public void mouseReleased() {
		currentImageIndex = (currentImageIndex + 1) % images.size();			// increment current image
	}
}
