import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendationsClient {

	private int item_id;
	private List<Recommendations> recom_list;
	
	public RecommendationsClient(int item_id) {
		super();
		this.item_id = item_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public List<Recommendations> getRecom_list() throws JSONException {
		recom_list=new ArrayList<Recommendations>();
		try {

			URL url = new URL("http://api.walmartlabs.com/v1/nbp?apiKey=6k2unbss8ha9h6unzdg95uw9&itemId="+this.item_id);
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
			
			GetRecommendations(outputBuffer.toString(),recom_list);

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return recom_list;
	}
	
	public void setRecom_list(List<Recommendations> recom_list) {
		this.recom_list = recom_list;
	}
	
	private void GetRecommendations(String output, List<Recommendations> recom_list) throws JSONException {
		// TODO Auto-generated method stub
		HashSet<Integer> remove_duplicates=new HashSet<Integer>();
		if(output.contains("errors")) return;
		JSONArray jsonarray = new JSONArray(output);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			if(jsonobject.getInt("itemId")==this.item_id) continue;
			if(remove_duplicates.contains(jsonobject.getInt("itemId"))) continue;
			else remove_duplicates.add(jsonobject.getInt("itemId"));
			Recommendations r=new Recommendations(jsonobject.getInt("itemId"),jsonobject.getString("name"));
			 recom_list.add(r);
		//	System.out.println("Recommendations Item:"+r.getProduct_id());
		}
	}
	
}
