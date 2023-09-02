// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN502 assignment.
// You may not distribute it in any other way without permission.
// Code for SWEN502, Assignment W2

import java.util.*;

import ecs100.UI;

/**
 * Station
 * Information about an individual station:
 * - The name
 * - The fare zone it is in (1 - 14)
 * - The distance from the hub station (Wellington)
 * - The set of TrainLines that go through that station.
 * The constructor just takes the name, zone and distance;
 * TrainLines must then be added to the station, one by one.
 */
public class Station {
	String sName;
	int zone;          // fare zone
	double distance;   // distance from Wellington
	double x;          // coordinate in the map
	double y;
	HashSet<TrainLine> trainLines = new HashSet<>();

	public Station(String name, int zone, double dist, double x, double y) {
		this.sName = name;
		this.zone = zone;
		this.distance = dist;
		this.x = x;
		this.y = y;
	}

	/**
	 * Add a TrainLine to the station
	 */
	public void addTrainLine(TrainLine line) {
		trainLines.add(line);
	}

	public double getDistance() {
		return this.distance;
	}

	public String getName() {
		return this.sName;
	}

	public Set<TrainLine> getTrainLines() {
		return Collections.unmodifiableSet(trainLines); // Return an unmodifiable version of the set of train lines.
	}

	public int getZone() {
		return this.zone;
	}

	public void highLight(String icon) {
		UI.drawImage(icon, x-32, y-8, 30, 30);
	}

	public boolean insideOf(double x2, double y2) {
		if (x2 > x && x2 < x+sName.length()*10 && y2 > y && y2 < y+20)
			return true;
		else return false;
	}

//	public void printInfo() {
//		UI.clearText();
//		UI.printf("Station Name: %s\n Fare Zone: %s\n Distance from Wellington %.2f\n", sName, zone, distance);
//		UI.println("Train Lines: "+trainLines.size());
//		for (TrainLine tl : trainLines) {
//			UI.println(" "+tl.getName());
//		}
//	}
	public void printInfo() {
		UI.setFontSize(24);
		UI.drawString(sName, 800, 120);
		UI.setFontSize(16);
		UI.drawString("Fare Zone: "+zone, 800, 150);
		UI.drawString("Distance from Wellington: "+distance+" km", 800, 180);
		int i = 1;
		for (TrainLine tl : trainLines) {
			UI.drawString("Line "+i+": "+tl.getName(), 800, 180+30*i);
			i++;
		}
	}

	/**
	 * toString is the station name plus zone, plus number of train lines
	 */
	public String toString() {
		return sName+" (zone "+zone+", "+trainLines.size()+" lines)";
	}
}
