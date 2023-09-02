import java.util.Comparator;

public class ComparatorDistance implements Comparator<Option> {
	public ComparatorDistance() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Option p1, Option p2) {
		return (int)(p1.getDistance()-p2.getDistance());
	}
}
