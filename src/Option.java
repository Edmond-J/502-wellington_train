import java.awt.Color;

import ecs100.UI;

public class Option {
//	private TrainLine trainline;
//	private TrainService timetable;
	private String trainID;
	private TrainLine line;
	private Station depSt;
	private Station arrSt;
	private int depTime;
	private int arrTime;
	private int stops;
	private int zoneGo;
	private double price;
	public Option next;

	public Option(Option op1, Option op2) {
		this.trainID = op1.getTrainID();
		this.line = op1.getLine();
		this.depSt = op1.getDepSt();
		this.arrSt = op1.getArrSt();
		this.depTime = op1.getDepTime();
		this.arrTime = op1.getArrTime();
		this.stops = op1.stops;
		this.zoneGo = op1.zoneGo;
		this.price = op1.price;
		this.next = op2;
	}

	public Option(String trainID, TrainLine line, Station ds, Station as, int depTime, int arrTime, int stops,
			int zoneGo, double price) {
		this.trainID = trainID;
		this.line = line;
		this.depSt = ds;
		this.arrSt = as;
		this.depTime = depTime;
		this.arrTime = arrTime;
		this.stops = stops;
		this.zoneGo = zoneGo;
		this.price = price;
	}

	public void printInfo(String priority) {
		String time1 = (depTime/100)+":"+String.format("%02d", (depTime%100));
		String time2 = (arrTime/100)+":"+String.format("%02d", (arrTime%100));
		UI.printf(
				"%s\n%s\n ▼|departure time: %s\n ▼|arrival time: %s\n ▼|zone travelled: %d\n ▼|price: $%.2f\n ▼|stops: %d\n%s\n",
				trainID, depSt.getName(), time1, time2, zoneGo, price, stops, arrSt.getName());
		if (next != null) {
			int nextMinute = (next.depTime/100)*60+(next.depTime%100);
			int arrMinute = (arrTime/100)*60+(arrTime%100);
			UI.printf("  🔄Transfer Duration: %d min\n", nextMinute-arrMinute);
			next.printInfo(priority);
			if (priority.equals("arrival")) {
				String time = (next.getArrTime()/100)+":"+String.format("%02d", (next.getArrTime()%100));
				UI.println("Arrival Time: "+time);
			} else if (priority.equals("distance")) {
				UI.printf("Total Distance(km): %.2f", getDistance());
			} else if (priority.equals("price")) {
				UI.printf("Total Price: %.2f\n", getPrice());
			}
		}
	}

	public void drawInfo(String priority, int i) {
//		int i = 1+((next == null) ? 1 : 0);
		String time1 = (depTime/100)+":"+String.format("%02d", (depTime%100));
		String time2 = (arrTime/100)+":"+String.format("%02d", (arrTime%100));
		int depMinute = (depTime/100)*60+(depTime%100);
		int arrMinute = (arrTime/100)*60+(arrTime%100);
		UI.setFontSize(24);
		UI.setColor(new Color(54, 116, 157));
		UI.drawString(depSt.getName(), 800, 240*i-120);
		UI.drawString(arrSt.getName(), 1100, 240*i-120);
		UI.drawLine(960, 240*i-120, 1080, 240*i-120);
		UI.drawLine(1080, 240*i-120, 1070, 240*i-130);
		UI.setFontSize(16);
		UI.drawString((arrMinute-depMinute)+" min", 1000, 240*i-125);
		UI.drawString(trainID, 820, 240*i-90);
		UI.drawString("departure time: "+time1, 820, 240*i-60);
		UI.drawString("arrival time: "+time2, 820, 240*i-30);
		UI.drawString("zone travelled: "+zoneGo, 820, 240*i);
		UI.drawString("price: $ "+price, 820, 240*i+30);
		UI.drawString("stops: "+stops, 820, 240*i+60);
		UI.drawLine(805, 240*i-100, 805, 240*i+60);
		if (next != null) {
			UI.drawImage("img/transfer.png", arrSt.getX()-25, arrSt.getY()-10, 30, 30);
			next.drawInfo(priority, i+1);
			int nextMinute = (next.depTime/100)*60+(next.depTime%100);
			UI.setColor(new Color(240, 134, 80));
			UI.drawString("Transfer Duration: "+(nextMinute-arrMinute)+"min", 820, 240*i+90);
			if (priority.equals("arrival")) {
				String time = (next.getArrTime()/100)+":"+String.format("%02d", (next.getArrTime()%100));
				UI.drawString("Arrival Time: "+time, 820, 570);
			} else if (priority.equals("stops")) {
				UI.drawString("Total Stops: "+getStops(), 820, 570);
			} else if (priority.equals("price")) {
				UI.drawString("Total Price: "+getPrice()+" $", 820, 570);
			}
		}
	}

	public Station getArrSt() {
		return arrSt;
	}

	public int getArrTime() {
		return arrTime;
	}

	public Station getDepSt() {
		return depSt;
	}

	public int getDepTime() {
		return depTime;
	}

	public double getDistance() {
		if (next != null) {
			return Math.abs(depSt.getDistance()-arrSt.getDistance())
					+Math.abs(next.depSt.getDistance()-next.arrSt.getDistance());
		}
		return Math.abs(depSt.getDistance()-arrSt.getDistance());
	}

	public TrainLine getLine() {
		return line;
	}

	public Option getNext() {
		return next;
	}

	public double getPrice() {
		if (next != null) {
			return price+next.getPrice();
		}
		return price;
	}

	public int getStops() {
		if (next != null) {
			return stops+next.getStops();
		}
		return stops;
	}

	public String getTrainID() {
		return trainID;
	}

	public int getZoneGo() {
		return zoneGo;
	}
}
