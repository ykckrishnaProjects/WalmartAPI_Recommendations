import java.util.Comparator;

public class Recommendations  {

	private int product_id;
	private double reviews;
	private String product;
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Recommendations(int product_id, String product) {
		super();
		this.product_id = product_id;
		this.reviews=0.0d;
		this.product=product;
		
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public double getReviews() {
		return reviews;
	}
	public void setReviews(double reviews) {
		this.reviews = reviews;
	}
	
	
	
}
