// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN502 assignment.
// You may not distribute it in any other way without permission.
// Code for SWEN502, Assignment W2

import java.awt.Color;
import java.util.*;

import ecs100.UI;

/**
 * TrainLine
 * Information about a Train Line.
 * Note, we treat the outbound train line as a different from the inbound line.
 * This means that the Johnsonville-Wellington line is a different train line from 
 * the Wellington-Johnsonville line.
 * Although they have the same stations, the stations will be in opposite orders.
 *
 * A TrainLine contains 
 * - the name of the TrainLine (originating station - terminal station, eg Wellington-Melling)
 * - The list of stations on the line
 * - a list of TrainServices running on the line (eg the 10:00 am service from Upper-Hutt to Wellington)
 *   (in order of time - services earlier in the list are always earlier times (at any station) than later services  )
 */
public class TrainLine {
	private double x;
	private double y;
	private String lName;
	private ArrayList<Station> stations = new ArrayList<>();           // list of stations on the line
	private ArrayList<TrainService> trainServices = new ArrayList<>(); // set of TrainServices running on the line

	// Constructor
	public TrainLine(String name) {
		this.lName = name;
	}

	public TrainLine(String name, ArrayList<Station> stat, ArrayList<TrainService> tr, double x, double y) {
		this.x = x;
		this.y = y;
		this.lName = name;
		this.stations = stat;
		this.trainServices = tr;
	}

	public void printInfo() {
		UI.setFontSize(24);
		UI.setColor(new Color(54, 116, 157));
		UI.drawString("Line Name: "+lName, 800, 120);
		UI.setFontSize(16);
		UI.drawString("Total Stations: "+stations.size(), 800, 150);
		int i = 1;
		for (Station st : stations) {
			UI.drawString(i+"."+st.getName(), 800, 150+30*i);
			i++;
		}
	}
//	public void printInfo() {
//		UI.clearText();
//		UI.printf("Line Name: %s\n", lName);
//		UI.println("Stations: "+stations.size());
//		for (Station st : stations) {
//			UI.println(" "+st.getName());
//		}
//	}

	public void highLight() {
		UI.drawImage("img/arrow (2).png", x-20, y, 30, 30);
	}

	public boolean insideOf(double x2, double y2) {
		if (x2 > x && x2 < x+200 && y2 > y && y2 < y+30)
			return true;
		else return false;
	}

	// Methods to add values to the TrainLine
	/**
	 * Add a TrainService to the set of TrainServices for this line
	 */
	public void addTrainService(TrainService train) {
		trainServices.add(train);
	}

	/**
	 * Add a Station to the list of Stations on this line
	 */
	public void addStation(Station station) {
		stations.add(station);
	}

	// Getters
	public String getName() {
		return lName;
	}

	public List<Station> getStations() {
		return Collections.unmodifiableList(stations); // an unmodifiable version of the list of stations
	}

	public List<TrainService> getTrainServices() {
		return Collections.unmodifiableList(trainServices); // an unmodifiable version of the list of trainServices
	}

	/**
	 * String contains name of the train line name plus number of stations and number of services
	 */
	public String toString() {
		return (lName+" ("+stations.size()+" stations, "+trainServices.size()+" services)");
	}
}
