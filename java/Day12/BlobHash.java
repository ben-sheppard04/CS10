import java.sql.Blob;

public class BlobHash extends Blob {
	
	@Override
	public boolean equals(Object otherPoint) {
		Blob p = (Blob)otherBLob; //cast as Point
		if (x == p.x && y == p.y && r == p.r)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		final int a=37;
		int sum = a * a * (int)x;
		sum += a * (int)y;
		sum += (int)r;
		return sum;
	}
}





