package com.migu.schedule.info;

/**
 * 任务状态信息类，请勿修改。
 *
 * @author
 * @version
 */
public class TaskInfo
{
    private int taskId;
    private int nodeId;
    private int taskConsumption;
    public TaskInfo(int nodeId, int taskId, int taskConsumption){
        this.nodeId = nodeId;
        this.taskId = taskId;
        this.taskConsumption = taskConsumption;
    }
    public int getNodeId()
    {
        return nodeId;
    }
    public int getTaskId(){  return taskId; }
    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }
    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    public int getTaskConsumption() {
        return taskConsumption;
    }

    public void setTaskConsumption(int taskConsumption) {
        this.taskConsumption = taskConsumption;
    }

    @Override
    public String toString()
    {
        return "TaskInfo [taskId=" + taskId + ", nodeId=" + nodeId + "]";
    }
}
