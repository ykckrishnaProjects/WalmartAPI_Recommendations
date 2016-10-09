import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;

public class APIMain {

	public static void main(String args[]) throws JSONException {
	
	System.out.println("Enter the product to be searched:");
	Scanner s=new Scanner(System.in);
	String product=s.next();
	int item_id=getItemId(product);
	if(item_id==0) {
		System.out.println("Product not found");
		System.exit(0);
	}
	else {
	System.out.println("First Item of the product: "+item_id);
	}
	
	RecommendationsClient recom_client=new RecommendationsClient(item_id);
	List<Recommendations> recom_list=recom_client.getRecom_list();
	if(recom_list.size()==0) {
		System.out.println("No Recommendations found");
		System.exit(0);
	}
	System.out.println("Total Number of Recommendation: "+recom_list.size());
	for(Recommendations recom:recom_list) {
		ReviewsClient revClient=new ReviewsClient(recom.getProduct_id());
		recom.setReviews(revClient.getReview_score());
	}
	Collections.sort(recom_list, new Comparator<Recommendations>() {

		@Override
		public int compare(Recommendations o1, Recommendations o2) {
			 if (o1.getReviews() < o2.getReviews()) return 1;
		        if (o1.getReviews() > o2.getReviews()) return -1;
		        return 0;
		}
		
		
	});
	
	for(Recommendations r:recom_list) {
		System.out.println("Product Recommended:"+r.getProduct()+"; Avg Rating:"+r.getReviews());
	}
	
	}

	private static int getItemId(String product) throws JSONException {
		// TODO Auto-generated method stub
		SearchClient searchObj=new SearchClient(product);
		return searchObj.getItem_id();
	}
}
