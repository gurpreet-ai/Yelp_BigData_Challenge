package module1;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class YelpDataSet extends PApplet 
{
	// You can ignore this.  It's to keep eclipse from reporting a warning
	private static final long serialVersionUID = 1L;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// IF YOU ARE WORKING OFFLINE: Change the value of this variable to true
	private static final boolean offline = false;
	
	UnfoldingMap map;
	
	/** The width and height of the window */
	private static final int WIDTH = 950;
	private static final int HEIGHT = 650;
	
	/** map width, height, and margins */
	private static final int mapWidth = 700;
	private static final int mapHeight = 500;
	
	private static final int mapMarginTop = 50;
	private static final int mapMarginLeft = 225;
	
	public void setup() {
		size(YelpDataSet.WIDTH, YelpDataSet.HEIGHT, P2D);  
		this.background(0, 256, 0);
		
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		
		int zoomLevel = 10;
		
		if (offline) {
			provider = new MBTilesMapProvider(mbTilesString);
			zoomLevel = 3;
		}
		
		System.out.println(zoomLevel);
		
		this.map = new UnfoldingMap(
				this, 	
				YelpDataSet.mapMarginLeft,
				YelpDataSet.mapMarginTop,
				YelpDataSet.mapWidth,
				YelpDataSet.mapHeight,
				provider);
		
		this.map.zoomAndPanTo(zoomLevel, new Location(33.4484f, -112.0740f));
		MapUtils.createDefaultEventDispatcher(this, this.map);
		
		Scanner scan;
		try {
//			scan = new Scanner(new File("data/phoenix.txt"));
			scan = new Scanner(new File("data/phoenix_bars.txt"));
			while(scan.hasNextLine()) {
		        String line = scan.nextLine();
		        String[] businessInfo = line.split(",");
		        Location location = new Location(Float.parseFloat(businessInfo[0]), Float.parseFloat(businessInfo[1])); 
		        SimplePointMarker marker = new SimplePointMarker(location);
		        int color;
		        if (Float.parseFloat(businessInfo[2]) < 10) {
		        	color = color(150,150,150);
					marker.setColor(color);
					marker.setRadius(3);
		        } else if (Float.parseFloat(businessInfo[2]) < 20) {
		        	color = color(253,141,60);
					marker.setColor(color);
					marker.setRadius(7);
		        } else if (Float.parseFloat(businessInfo[2]) < 30) {
		        	color = color(49,163,84);
					marker.setColor(color);
					marker.setRadius(12);
		        } else if (Float.parseFloat(businessInfo[2]) < 40) {
		        	color = color(215,181,216);
					marker.setColor(color);
					marker.setRadius(18);
		        } else if (Float.parseFloat(businessInfo[2]) < 50) {
		        	color = color(136,86,167);
					marker.setColor(color);
					marker.setRadius(25);
		        }
		        map.addMarkers(marker);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	/** Draw the Applet window.  */
	public void draw() {
		this.map.draw();
		addKey();
	}
	
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);
        rect(25, 50, 175, 350);
        
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Score Key", 50, 75);
     
        fill(color(136,86,167));
        ellipse(50, 125, 25, 25);
        
        fill(color(215,181,216)); 
        ellipse(50, 175, 18, 18);
        
        fill(color(49,163,84));
        ellipse(50, 225, 12, 12);
        
        fill(color(253,141,60));
        ellipse(50, 275, 7, 7);
        
        fill(color(150,150,150));
        ellipse(50, 325, 3, 3);

        fill(0, 0, 0);
        text("score < 50 (> 40)", 75, 125);
        text("score < 40 (> 30)", 75, 175);
        text("score < 30 (> 20)", 75, 225);
        text("score < 20 (> 10)", 75, 275);
        text("score < 10 (>  0)", 75, 325);
	}
}
