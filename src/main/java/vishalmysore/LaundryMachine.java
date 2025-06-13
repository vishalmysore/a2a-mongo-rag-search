package vishalmysore;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.detect.ActionCallback;
import com.t4a.detect.ActionState;
import lombok.extern.java.Log;

@Log
@Agent(groupName = "laundry", groupDescription = "actions related to laundry")
public class LaundryMachine {
    private ActionCallback callback;

    private boolean isRunning = false;
    private int temperature = 30;
    private String cycleType = "normal";
    @Action(description = "Start the washing machine")
    public String startWashing() {
        log.info("Washing started");
        return "Washing machine started";
    }

    @Action(description = "get quote for laundry")
    public Integer getQuoteForLaundry(String typeOfCloth, String weight) {
        log.info("Quote for laundry: " + typeOfCloth + ", " + weight);
        if(callback!= null) {
            callback.sendtStatus("Quote for laundry: " + typeOfCloth + ", " + weight+" is "+100, ActionState.COMPLETED);
        }
        return 100;
    }

    @Action(description = "Stop the washing machine")
    public String stopWashing() {
        isRunning = false;
        log.info("Washing stopped");
        if(callback != null) {
            callback.sendtStatus("Washing machine stopped", ActionState.COMPLETED);
        }
        return "Washing machine stopped";
    }

    @Action(description = "Set washing temperature")
    public void setTemperature(int temp) {
        this.temperature = temp;
        log.info("Temperature set to: " + temp);
        if(callback != null) {
            callback.sendtStatus("Temperature set to " + temp + " degrees", ActionState.COMPLETED);
        }
    }

    @Action(description = "Select washing cycle type")
    public void setCycleType(String cycle) {
        this.cycleType = cycle;
        log.info("Cycle type set to: " + cycle);
        if(callback != null) {
            callback.sendtStatus("Cycle type changed to " + cycle, ActionState.COMPLETED);
        }
    }

    @Action(description = "Get machine status")
    public String getStatus() {
        String status = String.format("Machine is %s, Temperature: %d°C, Cycle: %s",
                isRunning ? "running" : "stopped", temperature, cycleType);
        log.info("Status checked: " + status);
        return status;
    }

}
