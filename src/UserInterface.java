import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ecs100.UI;

public class UserInterface {
	HashMap<String, Station> statMap = new HashMap<>();
	HashMap<String, TrainLine> lineMap = new HashMap<>();
	HashMap<Integer, Double> fareMap = new HashMap<>();
	Station s1;
	Station s2;
	int myTime = 835;
	String priority = "";
	String function="enquiry";

	public UserInterface() {
		loadData();
		refresh();
		selectOne();
//		UI.addButton("Check Information", this::selectOne);
//		UI.addButton("Plan Travel", this::selectTwo);
//		travelDirectly();
//		findRoute(s1, s2);
	}

	private void refresh() {
		UI.clearText();
		UI.clearGraphics();
		UI.drawImage("img/map.png", 0, 0);
		UI.setLineWidth(1);
		UI.drawRect(0, 0, 1280, 1080);
		refreshButton();
		refreashFunction();
	}

	private void refreshButton() {
		UI.setLineWidth(2);
		UI.setColor(Color.white);
		UI.fillRect(800, 25, 225, 30);
		if (priority != "") {
			UI.setColor(Color.black);
			UI.setFontSize(12);
			// UI.drawRect(1040, 32, 15, 15);
			UI.drawLine(1040, 32, 1055, 47);
			UI.drawLine(1040, 47, 1055, 32);
			UI.setColor(new Color(54, 116, 157));
			if (priority.equals("arrival")) {
				UI.fillRect(800, 25, 75, 30);
				UI.setColor(Color.white);
				UI.drawString("Earlist", 820, 45);
			} else if (priority.equals("distance")) {
				UI.fillRect(875, 25, 75, 30);
				UI.setColor(Color.white);
				UI.drawString("Shortest", 820, 45);
			} else if (priority.equals("price")) {
				UI.fillRect(950, 25, 75, 30);
				UI.setColor(Color.white);
				UI.drawString("Cheapest", 960, 45);
			}
		}
		UI.setColor(Color.black);
		UI.setFontSize(12);
		UI.drawRect(800, 25, 75, 30);
		UI.drawRect(875, 25, 75, 30);
		UI.drawRect(950, 25, 75, 30);
		UI.drawString("Earlist", 820, 45);
		UI.drawString("Shortest", 890, 45);
		UI.drawString("Cheapest", 960, 45);
		UI.drawImage("img/enquiry.png", 90, 145, 65, 65);
		UI.drawImage("img/travel.png", 180, 145, 65, 65);
	}
	
	private void refreashFunction(){
		UI.setColor(Color.white);
		UI.fillRect(90, 145, 160, 70);
		UI.setColor(new Color(54, 116, 157));
		if(function.equals("enquiry"))
			UI.fillOval(90, 145, 65, 65);
		if(function.equals("travel"))
			UI.fillOval(180, 145, 65, 65);
		UI.drawImage("img/enquiry.png", 90, 145, 65, 65);
		UI.drawImage("img/travel.png", 180, 145, 65, 65);
	}
	// public void smokeTest() {
	// System.out.println(statMap.get("Ngaio").trainLines.size());
	// System.out.println(lineMap.get("Johnsonville_Wellington").stations.size());
	// System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2));
	// System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2).lineName);
	// System.out.println(lineMap.get("Johnsonville_Wellington").trainServices.get(2).timeTable.get(0));
	// }

	private void checkButton(double x, double y) {
		if (x > 90 && x < 155 && y > 145 && y < 210) {
			//checkInfo();
			function="enquiry";
			refreashFunction();
			selectOne();
		} else if (x > 180 && x < 245 && y > 145 && y < 210) {
			function="travel";
			refreashFunction();
			selectTwo();
		} else if (x > 800 && x < 875 && y > 25 && y < 55) {
			priority = "arrival";
			refreshButton();
		} else if (x > 875 && x < 950 && y > 25 && y < 55) {
			priority = "distance";
			refreshButton();
		} else if (x > 950 && x < 1025 && y > 25 && y < 55) {
			priority = "price";
			refreshButton();
		} else if (x > 1040 && x < 1055 && y > 32 && y < 47) {
			priority = "";
			refreshButton();
			UI.setColor(Color.white);
			UI.fillRect(1040, 32, 16, 16);
		}
	}

	public void loadData() {
		try {
			Scanner sc_fare = new Scanner(new File("data/fares.txt"));
			sc_fare.nextLine();
			while (sc_fare.hasNext()) {
				fareMap.put(sc_fare.nextInt(), Double.valueOf(sc_fare.nextLine().toString()));
			}
			Scanner sc_ast = new Scanner(new File("data/stations.txt"));
			while (sc_ast.hasNext()) {
				String sName = sc_ast.next();
				statMap.put(sName, new Station(sName, sc_ast.nextInt(), sc_ast.nextDouble(), sc_ast.nextDouble(),
						sc_ast.nextDouble()));
			}
			sc_ast.close();
			// load the TrainLine name
			Scanner sc_tl = new Scanner(new File("data/train-lines.txt"));
			while (sc_tl.hasNext()) {
				String tlName = sc_tl.next();
				double x = sc_tl.nextDouble();
				double y = sc_tl.nextDouble();
				ArrayList<Station> stationsList = new ArrayList<>();
				ArrayList<TrainService> trainServices = new ArrayList<>();
				// load the stations into ArrayList according to this TrainLine's stations.txt
				Scanner sc_stat = new Scanner(new File("data/"+tlName+"-stations.txt"));
				while (sc_stat.hasNext()) {
					stationsList.add(statMap.get(sc_stat.nextLine()));
				}
				sc_stat.close();
				// load the services into ArrayList according to this TrainLine's services.txt
				Scanner sc_serv = new Scanner(new File("data/"+tlName+"-services.txt"));
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
					trainServices.add(new TrainService(tlName, tlName+tlID, timeTable));
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
	public void selectOne() {
		refresh();
		UI.setMouseListener(this::checkInfo);
	}

	public void checkInfo(String action, double x, double y) {
		if (action.equals("clicked")) {
			UI.println(x+" "+y);
			checkButton(x, y);
			String find = null;
			for (Station s : statMap.values()) {
				if (s.insideOf(x, y)) {
					refresh();
					s.highLight("img/arrow (1).png");
					find = s.getName();
				}
			}
//			for (Entry<String, Station> e : statMap.entrySet()) {
//				if (e.getValue().insideOf(x, y)) {
//					refresh();
//					e.getValue().highLight("img/arrow (1).png");
//					find = e.getKey();
//				}
//			}
			for (TrainLine t : lineMap.values()) {
				if (t.insideOf(x, y)) {
					refresh();
					t.highLight();
					find = t.getName();
				}
			}
//			for (Entry<String, TrainLine> e : lineMap.entrySet()) {
//				if (e.getValue().insideOf(x, y)) {
//					refresh();
//					e.getValue().highLight();
//					find = e.getKey();
//				}
//			}
			if (statMap.containsKey(find)) {
				statMap.get(find).printInfo();
			}
			if (lineMap.containsKey(find)) {
				lineMap.get(find).printInfo();
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
			String find = null;
			for (Station s : statMap.values()) {
				if (s.insideOf(x, y)) {
					if (s1 == null && s2 == null) {
						refresh();
						s.highLight("img/arrow (1).png");
					} else s.highLight("img/arrow (5).png");
					find = s.getName();
				}
			}
			if (statMap.containsKey(find) && s1 == null) {
				s1 = statMap.get(find);
				return;
			}
			if (statMap.containsKey(find) && s1 != null && s2 == null) {
				s2 = statMap.get(find);
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
				if (ts.getTimes().get(indexFrom) > myTime) {// 保证时间来得及
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
//		UI.println("test");
//		String input1 = "Mana";
//		String input2 = "Tawa";
//		s1 = statMap.get(input1);
//		s2 = statMap.get(input2);
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
							// optionPrint(optList);
							break;
						}
					}
				}
			}
			// return;如果return在这里的话，单步运行有结果，但是整体运行没结果（有的有，有的没有）
		}
		if (optList.size() == 0)
			findTransfer();
		optionPrint(optList);
	}

	public void findTransfer() {
//		ArrayList<Option> optList1 = new ArrayList<>();
//		ArrayList<Option> optList2 = new ArrayList<>();
		ArrayList<Option> optList = new ArrayList<>();
		UI.printf("no direct route between %s and %s\n", s1.getName(), s2.getName());
		UI.println("transfer route:");
		for (TrainLine tls1 : s1.getTrainLines()) {
			for (Station st : tls1.getStations()) {// 寻找换乘点
				int indexFrom = tls1.getStations().indexOf(s1);
				int indexTran1 = tls1.getStations().indexOf(st);
				if (st.getTrainLines().size() > 2 && indexTran1 > indexFrom) {
					for (TrainLine tls2 : st.getTrainLines()) {
						int indexTran2 = tls2.getStations().indexOf(st);
						int indexTo = tls2.getStations().indexOf(s2);
						// 会发生走回头路的情况
						double uTurn = (st.getDistance()-s1.getDistance())*(s2.getDistance()-st.getDistance());
						double inMiddle = 0;
						for (Station s : tls1.getStations()) {
							int indexPos = (tls1.getStations().indexOf(s)-indexFrom)
									*(indexTran1-tls1.getStations().indexOf(s));
							// 如果换乘站在中间，则indexPo>0
							for (TrainLine t : s.getTrainLines()) {
								if (t.getStations().contains(s2)) {
									if (s.getTrainLines().size() > 2 && indexPos > 0)
										// 如果存在一个换乘站s位于起点和目的换乘站st之间，则返回1
										inMiddle = 1;
								}
							}
						}
						if (tls2.getStations().contains(s2) && indexTran2 < indexTo && uTurn*inMiddle >= 0) {
							// st是换乘点，tls2是换乘线路
							Option op1 = travelDirectly(s1, st, tls1, myTime);// optList=会被覆盖，必须用addAll
							// 再次找到第二段的选择的时候，第一段也会被重复加入
							Option op2 = travelDirectly(st, s2, tls2, op1.getArrTime());
							// 发生了交叉相乘
							Option comOpt = new Option(op1, op2);
							optList.add(comOpt);
						}
					}
				}
			}
		}
		optionPrint(optList);
	}

	public void optionPrint(ArrayList<Option> optList) {
		if (priority.equals("arrival")) {
			optList.sort(new ComparatorArrival());
		} else if (priority.equals("distance")) {
			optList.sort(new ComparatorDistance());
		} else if (priority.equals("price")) {
			optList.sort(new ComparatorPrice());
		}
		for (int i = 0; i < optList.size(); i++) {
			UI.printf("\n-------------Option %d------------\n", i+1);
			optList.get(i).printInfo(priority);
		}
	}

	public static void main(String[] args) {
		new UserInterface();
	}
}
