import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ecs100.UI;

public class WellingtonTrain_Main {
	HashMap<String, Station> statMap = new HashMap<>();
	HashMap<String, TrainLine> lineMap = new HashMap<>();
	HashMap<Integer, Double> fareMap = new HashMap<>();
	Station s1;
	Station s2;
	int myTime = getSystemTime();
	String priority = "arrival";
	String function = "enquiry";

	public WellingtonTrain_Main() {
		loadData();
		refresh();
		selectOne();
	}

	private int getSystemTime() {
		LocalTime sysTime = LocalTime.now();
		int sysTimeInt = Integer.parseInt(sysTime.format(DateTimeFormatter.ofPattern("HHmm")));
		return sysTimeInt;
	}

	private void refresh() {
		UI.clearText();
		UI.clearGraphics();
		UI.setLineWidth(1);
		UI.setColor(new Color(217, 217, 217));
		UI.fillRect(0, 0, 1280, 1080);
		UI.drawImage("img/img-wellington_train/map.png", 0, 0);
		refreshButton();
		refreashFunction();
		// 获取当前系统时间
		LocalTime sysTime = LocalTime.now();
		String sysTimeString = sysTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		String myTimeString = (myTime/100)+":"+String.format("%02d", (myTime%100));
//		String shwoTime = sysTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		UI.setFontSize(20);
		UI.drawString("Time: "+sysTimeString, 1150, 50);
		if (myTime != 0)
			UI.drawString(myTimeString, 285, 240);
		UI.setColor(Color.black);
		UI.drawRect(0, 0, 1280, 1080);
	}

	private void refreshButton() {
		UI.setColor(new Color(217, 217, 217));
		UI.fillRect(800, 25, 225, 30);
		if (priority != "") {
			UI.setColor(Color.black);
			UI.setFontSize(12);
			// UI.drawRect(1040, 32, 15, 15);
			UI.drawLine(1040, 32, 1055, 47);// 叉叉
			UI.drawLine(1040, 47, 1055, 32);
			UI.setColor(new Color(54, 116, 157));
			if (priority.equals("arrival")) {
				UI.fillRect(800, 25, 75, 30);
				UI.setColor(new Color(217, 217, 217));
				UI.drawString("Earliest", 820, 45);
			} else if (priority.equals("stops")) {
				UI.fillRect(875, 25, 75, 30);
				UI.setColor(new Color(217, 217, 217));
				UI.drawString("Least", 985, 45);
			} else if (priority.equals("price")) {
				UI.fillRect(950, 25, 75, 30);
				UI.setColor(new Color(217, 217, 217));
				UI.drawString("Cheapest", 960, 45);
			}
		}
		UI.setColor(Color.black);
		UI.setFontSize(12);
		UI.drawRect(800, 25, 75, 30);
		UI.drawRect(875, 25, 75, 30);
		UI.drawRect(950, 25, 75, 30);
		UI.drawString("Earliest", 820, 45);
		UI.drawString("Least", 895, 45);
		UI.drawString("Cheapest", 960, 45);
		UI.drawImage("img/img-wellington_train/enquiry.png", 90, 145, 65, 65);
		UI.drawImage("img/img-wellington_train/travel.png", 180, 145, 65, 65);
		UI.drawImage("img/img-wellington_train/alarm.png", 270, 140, 75, 75);
	}

	private void refreashFunction() {
		UI.setColor(Color.white);
		UI.fillRect(90, 145, 160, 70);
		UI.setColor(new Color(54, 116, 157));
		if (function.equals("enquiry"))
			UI.fillOval(90, 145, 65, 65);
		if (function.equals("travel"))
			UI.fillOval(180, 145, 65, 65);
		if (myTime != 0)
			UI.fillOval(280, 160, 50, 50);
		UI.drawImage("img/img-wellington_train/enquiry.png", 90, 145, 65, 65);
		UI.drawImage("img/img-wellington_train/travel.png", 180, 145, 65, 65);
		UI.drawImage("img/img-wellington_train/alarm.png", 270, 140, 75, 75);
	}
	/*	 public void smokeTest() {
		 System.out.println(statMap.get("Ngaio").trainLines.size());
		 System.out.println(lineMap.get("Johnsonville_Wellington").stations.size());
		 System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2));
		 System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2).lineName);
		 System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2).timeTable.get(0));
		 }*/

	private void checkButton(double x, double y) {
		if (x > 90 && x < 155 && y > 145 && y < 210) {
			// checkInfo();
			function = "enquiry";
			refreashFunction();
			selectOne();
		} else if (x > 180 && x < 245 && y > 145 && y < 210) {
			function = "travel";
			refreashFunction();
			selectTwo();
		} else if (x > 270 && x < 345 && y > 140 && y < 215) {
			myTime = UI.askInt("your set off time?(hhmm)");
			refresh();
		} else if (x > 800 && x < 875 && y > 25 && y < 55) {
			priority = "arrival";
			refreshButton();
		} else if (x > 875 && x < 950 && y > 25 && y < 55) {
			priority = "stops";
			refreshButton();
		} else if (x > 950 && x < 1025 && y > 25 && y < 55) {
			priority = "price";
			refreshButton();
		} else if (x > 1040 && x < 1055 && y > 32 && y < 47) {
			priority = "";
			refreshButton();
			UI.setColor(new Color(217, 217, 217));
			UI.fillRect(1040, 32, 16, 16);
		}
	}

	public void loadData() {
		try {
			Scanner sc_fare = new Scanner(new File("data/data-wellington_train/fares.txt"));
			sc_fare.nextLine();
			while (sc_fare.hasNext()) {
				fareMap.put(sc_fare.nextInt(), Double.valueOf(sc_fare.nextLine().toString()));
			}
			Scanner sc_ast = new Scanner(new File("data/data-wellington_train/stations.txt"));
			while (sc_ast.hasNext()) {
				String sName = sc_ast.next();
				statMap.put(sName, new Station(sName, sc_ast.nextInt(), sc_ast.nextDouble(), sc_ast.nextDouble(),
						sc_ast.nextDouble()));
			}
			sc_ast.close();
			// load the TrainLine name
			Scanner sc_tl = new Scanner(new File("data/data-wellington_train/train-lines.txt"));
			while (sc_tl.hasNext()) {
				String tlName = sc_tl.next();
				double x = sc_tl.nextDouble();
				double y = sc_tl.nextDouble();
				ArrayList<Station> stationsList = new ArrayList<>();
				ArrayList<TrainService> trainServices = new ArrayList<>();
				// load the stations into ArrayList according to this TrainLine's stations.txt
				Scanner sc_stat = new Scanner(new File("data/data-wellington_train/"+tlName+"-stations.txt"));
				while (sc_stat.hasNext()) {
					stationsList.add(statMap.get(sc_stat.nextLine()));
				}
				sc_stat.close();
				// load the services into ArrayList according to this TrainLine's services.txt
				Scanner sc_serv = new Scanner(new File("data/data-wellington_train/"+tlName+"-services.txt"));
				while (sc_serv.hasNext()) {
					ArrayList<Integer> timeTable = new ArrayList<>();
					int tlID = 0;
					for (int i = 0; i < stationsList.size(); i++) {
						int time = sc_serv.nextInt();
						timeTable.add(time);
						if (tlID == 0 && time != -1) {
							tlID = time;
						}
					}
					trainServices.add(new TrainService(tlName+tlID, timeTable));
				}
				lineMap.put(tlName, new TrainLine(tlName, stationsList, trainServices, x, y));
			}
			sc_tl.close();
			for (Station st : statMap.values()) {
				for (TrainLine tl : lineMap.values()) {
					if (tl.getStations().contains(st)) {
						st.addTrainLine(tl);
					}
				}
			}
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
	}

	/**
	 * setup the mouse listener and call the checkInfo() method, which response with only one single click
	 */
	public void selectOne() {
		refresh();
		UI.setMouseListener(this::checkInfo);
	}

	public void checkInfo(String action, double x, double y) {
		if (action.equals("clicked")) {
			UI.println(x+" "+y);
			checkButton(x, y);
			for (Station s : statMap.values()) {
				if (s.insideOf(x, y)) {
					refresh();
					s.highLight("img/img-wellington_train/arrow (2).png");
					s.printInfo();
				}
			}
			for (TrainLine t : lineMap.values()) {
				if (t.insideOf(x, y)) {
					refresh();
					t.highLight();
					t.printInfo();
				}
			}
		}
	}

	public void selectTwo() {
		refresh();
		UI.setMouseListener(this::planTravel);
	}

	public void planTravel(String action, double x, double y) {
		if (action.equals("clicked")) {
			checkButton(x, y);
			Station find = null;
			for (Station s : statMap.values()) {
				if (s.insideOf(x, y)) {
					if (s1 == null && s2 == null) {
						refresh();
						s.highLight("img/img-wellington_train/go.png");
					} else s.highLight("img/img-wellington_train/stop.png");
					find = s;
				}
			}
			if (statMap.containsValue(find) && s1 == null) {
				s1 = find;
				return;
			}
			if (statMap.containsValue(find) && s1 != null && s2 == null) {
				s2 = find;
				findRoute(s1, s2);
				s1 = null;
				s2 = null;
			}
		}
	}

	public Option travelDirectly(Station s1, Station s2, TrainLine tl, int myTime) {
		int indexFrom = tl.getStations().indexOf(s1);
		int indexTo = tl.getStations().indexOf(s2);
		if (indexFrom < indexTo) {// 保证方向正确
			for (TrainService ts : tl.getTrainServices()) {
				if (ts.getTimes().get(indexFrom) > myTime && ts.getTimes().get(indexTo) > 0) {// 保证时间来得及&&该站停靠
					int departTime = ts.getTimes().get(indexFrom);
					int arriveTime = ts.getTimes().get(indexTo);
					int zoneGo = Math.abs(s1.getZone()-s2.getZone())+1;
					Option option = new Option(ts.toString(), tl, s1, s2, departTime, arriveTime, indexTo-indexFrom,
							zoneGo, fareMap.get(zoneGo));
					return option;
				}
			}
		}
		return null;
	}

	public void findRoute(Station s1, Station s2) {
		/*	UI.println("test");
			String input1 = "Mana";
			String input2 = "Tawa";
			s1 = statMap.get(input1);
			s2 = statMap.get(input2);*/
		ArrayList<Option> optList = new ArrayList<>();
		for (TrainLine tl : s1.getTrainLines()) {// 在经过s1的TrainLine集合里遍历
			if (tl.getStations().contains(s2)) {// 找到包含s2的TrainLine
				int indexFrom = tl.getStations().indexOf(s1);
				int indexTo = tl.getStations().indexOf(s2);
				if (indexFrom < indexTo) {// 保证方向正确
					for (TrainService ts : tl.getTrainServices()) {
						if (ts.getTimes().get(indexFrom) > myTime && ts.getTimes().get(indexTo) != -1) {
							// 保证时间来得及&&班次会停靠
							int departTime = ts.getTimes().get(indexFrom);
							int arriveTime = ts.getTimes().get(indexTo);
							int zoneGo = Math.abs(s1.getZone()-s2.getZone())+1;
							Option option = new Option(ts.toString(), tl, s1, s2, departTime, arriveTime,
									indexTo-indexFrom, zoneGo, fareMap.get(zoneGo));
							optList.add(option);
//							optionPrint(optList);
							break;
						}
					}
				}
			}
//			 return;  //如果return在这里的话，单步运行有结果，但是整体运行没结果（有的有，有的没有）
		}
		if (optList.size() == 0)
			findTransfer();
		else optionPrint(optList);
	}

	public void findTransfer() {
		ArrayList<Option> optList = new ArrayList<>();
		UI.printf("no direct route between %s and %s\n", s1.getName(), s2.getName());
		UI.println("transfer route:");
		UI.setColor(Color.white);
		UI.setFontSize(18);
		UI.drawString("No direct train between "+s1.getName()+" and "+s2.getName(), 800, 1060);
		for (TrainLine tls1 : s1.getTrainLines()) {
			for (Station st : tls1.getStations()) {// 寻找换乘点
				int indexFrom = tls1.getStations().indexOf(s1);
				int indexTran1 = tls1.getStations().indexOf(st);
				if (st.getTrainLines().size() > 2 && indexTran1 > indexFrom) {
					for (TrainLine tls2 : st.getTrainLines()) {
						int indexTran2 = tls2.getStations().indexOf(st);
						int indexTo = tls2.getStations().indexOf(s2);
						// ↓检查目标换乘点是否是走回头路。判断依据为是否靠近或远离惠灵顿。
						//  如果两段路线先靠近再远离，或先远离再靠近，代表产生u-turn，在此情况下距离向量乘积小于0
						double uTurn = (st.getDistance()-s1.getDistance())*(s2.getDistance()-st.getDistance());
						// ↓检查在目标换乘点和起点之间有无其他换乘点
						double inMiddle = 0;
						for (Station s : tls1.getStations()) {
							int indexPos = (tls1.getStations().indexOf(s)-indexFrom)
									*(indexTran1-tls1.getStations().indexOf(s));
							// ↓利用站点的index来检查tls1线路上每个站点，是否位于起点站和目的换乘站中间，如果换乘站在中间，则indexPo>0
							for (TrainLine t : s.getTrainLines()) {
								if (t.getStations().contains(s2)) {
									if (s.getTrainLines().size() > 2 && indexPos > 0)
										// 如果存在一个换乘站s位于起点和目的换乘站st之间，则返回1（可以是任何一个正数）
										inMiddle = 1;
								}
							}
						}
						//解释uTurn*inMiddle >= 0：inMiddle的初始值为0，所以只要在目标换乘站之间没有其他换乘站，则不论是否产生uTurn，该条件均成立，目标换乘站可用
						//如果起点和目标换乘站之间存在其他换乘站，此时inMiddle=1，所以uTurn必须>0（没有产生uTurn），条件才成立，该换乘站才可用
						//比如从Melling到Ava，Ngauranga换乘站不可用，因为Melling到Ngauranga之间有Petone，所以inMiddle=1, 又产生了uTurn（<0），这种情况就能被成功过滤掉
						if (tls2.getStations().contains(s2) && indexTran2 < indexTo && uTurn*inMiddle >= 0) {
							// st是换乘点，tls2是换乘线路
							Option op1 = travelDirectly(s1, st, tls1, myTime);
							if (op1 != null) {
								Option op2 = travelDirectly(st, s2, tls2, op1.getArrTime());
								if (op2 != null) {
									Option combineOpt = new Option(op1, op2);
									optList.add(combineOpt);
								}
							}
						}
					}
				}
			}
		}
		if (optList.size() == 0) {
			UI.setColor(Color.white);
			UI.setFontSize(18);
			UI.drawString("No train found at this time", 800, 200);
		} else optionPrint(optList);
	}

	public void optionPrint(ArrayList<Option> optList) {
		if (priority.equals("arrival")) {
			optList.sort(new ComparatorArrival());
		} else if (priority.equals("stops")) {
			optList.sort(new ComparatorStops());
		} else if (priority.equals("price")) {
			optList.sort(new ComparatorPrice());
		}
		for (int i = 0; i < optList.size(); i++) {
			UI.printf("\n-------------Option %d------------\n", i+1);
			optList.get(i).printInfo(priority);
		}
		optList.get(0).drawInfo(priority, 1);
	}

	public static void main(String[] args) {
		new WellingtonTrain_Main();
	}
}
