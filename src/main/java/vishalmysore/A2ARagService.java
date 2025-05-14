package vishalmysore;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishalmysore.a2a.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class A2ARagService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    ObjectStringSummarizer summarizer;

    public void addTask(Object task) {
        String summary = summarizer.summarize(task.toString());
        log.info("Adding task to vector store: " + summary);
        vectorStore.add(List.of(new Document(task.toString())));
    }


    public List<Document> getSimilarDocuments(String taskText) {

        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query(taskText).topK(1).build());
        //you can do additional processing here if needed
        return results;
    }


    /**
     * If you want to take new action based on the task, you can do it here
     * @param method
     * @param params
     */
    public void ragStorage(String method, Object params) {

        switch (method) {
            case "tasks/send":
                TaskSendParams sendParams = new ObjectMapper().convertValue(params, TaskSendParams.class);
                addTask(sendParams);
                break;
            case "tasks/get":
                TaskQueryParams queryParams = new ObjectMapper().convertValue(params, TaskQueryParams.class);
                addTask(queryParams);
                break;
            case "tasks/sendSubscribe":
                TaskSendSubscribeParams sendSubscribeParams = new ObjectMapper().convertValue(params, TaskSendSubscribeParams.class);
                addTask(sendSubscribeParams);
                break;
            case "tasks/cancel":
                TaskCancelParams cancelParams = new ObjectMapper().convertValue(params, TaskCancelParams.class);
                addTask(cancelParams);
                break;
            case "tasks/setPushNotification":
                TaskSetPushNotificationParams setPushParams = new ObjectMapper().convertValue(params, TaskSetPushNotificationParams.class);
                addTask(setPushParams);
                break;
            case "tasks/getPushNotification":
                TaskGetPushNotificationParams getPushParams = new ObjectMapper().convertValue(params, TaskGetPushNotificationParams.class);
                addTask(getPushParams);
                break;
            case "tasks/resubscribe":
                TaskResubscriptionParams resubParams = new ObjectMapper().convertValue(params, TaskResubscriptionParams.class);
                addTask(resubParams);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Method not found: " + method);
        }
    }

    }
