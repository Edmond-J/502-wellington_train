import java.util.Comparator;

public class ComparatorStops implements Comparator<Option> {
	public ComparatorStops() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Option p1, Option p2) {
		return p1.getStops()-p2.getStops();
	}
}
