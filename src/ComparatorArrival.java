import java.util.Comparator;

public class ComparatorArrival implements Comparator<Option> {
	public ComparatorArrival() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Option p1, Option p2) {
		if (p1.next != null && p2.next != null) {
			return p1.next.getArrTime()-p2.next.getArrTime();
		}
		return (p1.getArrTime()-p2.getArrTime());
	}
}
