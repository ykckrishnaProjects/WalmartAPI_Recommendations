import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchClient {

	private String product;
	private int item_id;
	
	public SearchClient(String product) {
		super();
		this.product = product;
	
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getItem_id() throws JSONException {
		
		
		try {

			URL url = new URL("http://api.walmartlabs.com/v1/search?apiKey=6k2unbss8ha9h6unzdg95uw9&query="+this.product);
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
		//	System.out.println("Output from Search API Server .... \n");
			while ((output = br.readLine()) != null) {
			//	System.out.println(output);
				outputBuffer.append(output);
			}
			
			this.item_id=GetFirstItem(outputBuffer.toString());

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return item_id;
	}
	
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	
	private int GetFirstItem(String output) throws JSONException {
		
		JSONObject obj = new JSONObject(output);
		String productName = obj.getString("query");
		int numItems=obj.getInt("numItems");
		if(numItems==0) return 0;
	//	System.out.println("Product Name:"+productName);
		JSONArray jarr = obj.getJSONArray("items");
	//	System.out.println(jarr.length());
		int item_id=0;
		
		for(int i=0;i<jarr.length();i++) {
			JSONObject itemObj  = jarr.getJSONObject(i);
		//	System.out.println(itemObj.getInt("itemId"));
			item_id= itemObj.getInt("itemId");
			break;
		}
		return item_id;
	}
	
}
