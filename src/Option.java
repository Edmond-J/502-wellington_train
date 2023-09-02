import ecs100.UI;

public class Option {
//	private TrainLine trainline;
//	private TrainService timetable;
	private String trainID;
	private TrainLine line;
	public Station depSt;
	public Station arrSt;
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
			int arriveMinute = (arrTime/100)*60+(arrTime%100);
			UI.printf("  🔄Transfer Time: %d min\n", nextMinute-arriveMinute);
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
			return price+next.price;
		}
		return price;
	}

	public int getStops() {
		return stops;
	}

	public String getTrainID() {
		return trainID;
	}

	public int getZoneGo() {
		return zoneGo;
	}

	public void setArrSt(Station arrSt) {
		this.arrSt = arrSt;
	}

	public void setArrTime(int arrTime) {
		this.arrTime = arrTime;
	}

	public void setDepSt(Station depSt) {
		this.depSt = depSt;
	}

	public void setDepTime(int depTime) {
		this.depTime = depTime;
	}

	public void setLine(TrainLine line) {
		this.line = line;
	}
//	public void setNext(Option n) {
//		if (next == null)
//			next = n;
//	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public void setTrainID(String trainID) {
		this.trainID = trainID;
	}

	public void setZoneGo(int zoneGo) {
		this.zoneGo = zoneGo;
	}
}
