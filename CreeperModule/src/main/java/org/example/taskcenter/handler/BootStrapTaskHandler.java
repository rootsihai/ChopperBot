package org.example.taskcenter.handler;

import org.example.taskcenter.TaskCenter;
import org.example.taskcenter.request.BarrageReptileRequest;
import org.example.taskcenter.request.LiveReptileRequest;
import org.example.taskcenter.request.ReptileRequest;
import org.example.taskcenter.task.ReptileTask;

/**
 * @author Genius
 * @date 2023/07/28 17:37
 **/
public class BootStrapTaskHandler implements TaskHandler<ReptileRequest> {

    private LiveTaskHandler liveTaskHandler;

    public BootStrapTaskHandler() {
        this.liveTaskHandler = new LiveTaskHandler();
    }

    @Override
    public ReptileTask distribute(ReptileRequest request) {
        ReptileTask task = null;
        if(request instanceof LiveReptileRequest){
            task = liveTaskHandler.distribute((LiveReptileRequest) request);
        }else if(request instanceof BarrageReptileRequest){

        }
        return task;
    }

}
