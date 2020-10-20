package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class RecEngine {
	public static void main(String[] args) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File("ratings.dat"));
		UserSimilarity similarity = new EuclideanDistanceSimilarity(model);		

		//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(.0005, similarity, model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(15, similarity, model);
	
		UserBasedRecommender rec = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> user1Recs = rec.recommend(1, 10);
		for(RecommendedItem r : user1Recs) {
			System.out.println("Recommendation: Item #" + r.getItemID() + " score=" + r.getValue());
		}
	}
}
