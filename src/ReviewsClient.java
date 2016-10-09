import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsClient {

	
	private int product_id;
	private double review_score;
	
	public ReviewsClient(int product_id) {
		super();
		this.product_id = product_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public double getReview_score() throws JSONException {
		
		try {

			URL url = new URL("http://api.walmartlabs.com/v1/reviews/"+this.product_id+"?apiKey=6k2unbss8ha9h6unzdg95uw9&format=json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			
			StringBuilder outputBuffer = new StringBuilder();
			
			String output;
			
			while ((output = br.readLine()) != null) {
			//	System.out.println(output);
				outputBuffer.append(output);
			}
			
			this.review_score=GetReviewScore(outputBuffer.toString());

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		
		
		return review_score;
	}
	private double GetReviewScore(String output) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(output);
		//System.out.println(obj.getInt("itemId"));
		if(!CheckReviewsExists(output)) return 0.0d;
		JSONObject review_stats = (JSONObject) obj.get("reviewStatistics");
		double avg_rating=Double.parseDouble((String) review_stats.get("averageOverallRating"));
		int number_of_reviews=Integer.parseInt((String) review_stats.get("totalReviewCount"));
		return avg_rating*number_of_reviews;
	}
	private boolean CheckReviewsExists(String output) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject(output);
		JSONArray jarr = obj.getJSONArray("reviews");
		if(jarr.length()>0) return true;
		return false;
		}
	public void setReview_score(double review_score) {
		this.review_score = review_score;
	}
}
