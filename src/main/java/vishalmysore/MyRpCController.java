package vishalmysore;


import io.github.vishalmysore.a2a.domain.JsonRpcRequest;
import io.github.vishalmysore.a2a.server.A2ATaskController;
import io.github.vishalmysore.a2a.server.JsonRpcController;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Expose the Json-RPC endpoint for the tasks
 *  This will handle all the JSON RPC Requests for a2a such as
 *  tasks/send
 *  tasks/get
 *  tasks/sendSubscribe
 *  tasks/cancel
 *  tasks/setPushNotification etc
 * */
@RestController
@RequestMapping("/")
@Log
public class MyRpCController extends JsonRpcController {

    @Autowired
    private A2ARagService vectorService;

    @Override
    public void preProcessing(String method, Object params) {
        vectorService.ragStorage(method,params);
    }

    @Override
    public void postProcessing(String method, Object params) {
        super.postProcessing(method, params);
    }



    @PostMapping
    public Object handleRpc(@RequestBody JsonRpcRequest request) {
        log.info(request.toString());
        return super.handleRpc(request);
    }

    /**
     * OPTIONAL : Delegate the tasks to the task controller
     * @return
     */
    @Override
    public A2ATaskController getTaskController() {
        return new MyA2ATaskController();
    }
}
