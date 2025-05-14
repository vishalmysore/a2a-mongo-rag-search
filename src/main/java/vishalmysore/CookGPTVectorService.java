package vishalmysore;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CookGPTVectorService {

    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void init() {
        //add some sample data you want to search this can be your recipes or any other data or can be added from file
        List<Document> documents = List.of(
                new Document("Grandma’s Italian Walnut Cake looks simple and spartan, but the flavors are bold and complex, with the fragrance of toasted walnuts, citrus zest, and rum. It’s a single-layer, round cake decorated with a simple sprinkling of powdered sugar. Grandma often had this walnut cake in the freezer, ready to serve guests stopping by.", Map.of("meta1", "meta1")),
                new Document("This lasagna is based on my spinach artichoke lasagna on the blog and the roasted eggplant lasagna in my cookbook. I love those less-conventional vegetarian lasagnas."),
                new Document("Thai Fried Rice Recipe - It’s a very straightforward recipe, all cooked up in a single wok or large skillet in a matter of 5 minutes or less.\n" +
                        "\n" +
                        "A unique technique used in Thai cooking is the way the egg is cooked – the other ingredients are pushed to the side, then the egg is scrambled on the other side before tossing through the remaining ingredients", Map.of("meta2", "meta2")),
                new Document("If you think of an “Indian snack”, probably the first image that pops into mind is the humble Samosa.\n" +
                        "\n" +
                        "These little fried parcels of flaky-yet-tender pastry, stuffed to the brim with spiced potatoes and other ingredients, are pretty much my idea of the world’s greatest savoury snack.", Map.of("meta2", "meta2")),
                new Document("Paneer makhani (also called paneer butter masala) is an Indian dish of paneer, originating in New Delhi, in which the gravy is prepared usually with butter (makhan), tomatoes and cashews.[7] Spices such as red chili powder and garam masala are also used to prepare this gravy.\n" +
                        "\n" +
                        "A survey found that paneer butter masala was one of the top five foods ordered in India.", Map.of("meta2", "meta2"))
        );
        vectorStore.add(documents);



    }

    public void addRecipe(String data) {
        vectorStore.add(List.of(new Document(data)));
    }


    public List<Document> getSimilarDocuments(String query) {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(1).build());
        //you can do additional processing here if needed
        return results;
    }
}
