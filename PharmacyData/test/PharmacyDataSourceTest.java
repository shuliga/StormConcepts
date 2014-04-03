
import com.shl.poc.strom.commons.data.PharmacyDataSource;

/**
 * User: yshuliga
 * Date: 06.01.14 15:25
 */
public class PharmacyDataSourceTest {

	private static int COUNT = 100;


	public static void main(String[] args) {
		PharmacyDataSource dataSource = new PharmacyDataSource();
		for (int i = 1; i <= COUNT; i++){
			System.out.println(dataSource.getNextJson());
		}
	}
}
