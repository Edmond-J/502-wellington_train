import java.util.Comparator;

public class ComparatorPrice implements Comparator<Option> {
	public ComparatorPrice() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Option p1, Option p2) {
		return (int)(p1.getPrice()-p2.getPrice());
	}
}
