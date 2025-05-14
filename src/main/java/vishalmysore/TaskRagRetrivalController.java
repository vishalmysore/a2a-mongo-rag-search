package vishalmysore;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class TaskRagRetrivalController {




        @Autowired
        private A2ARagService vectorService;

        @GetMapping("/getTask")
        public List<Document> getTask(@RequestParam("taskText") String taskText) {

            return vectorService.getSimilarDocuments(taskText);
        }
}
