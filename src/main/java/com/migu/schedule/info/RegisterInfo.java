package com.migu.schedule.info;

import com.migu.schedule.Schedule;

import java.util.*;

public class RegisterInfo {


    //服务节点和任务队列映射关系表
    private Map<Integer/*nodeId*/,ArrayList<Integer/*taskId*/>> nodeTaskMap = null;
    private Map<Integer/*nodeId*/,TaskInfo> suspendTaskMap = null;
    private Map<Integer/*nodeId*/,TaskInfo> runningTaskMap = null;

    public RegisterInfo(){
        nodeTaskMap = new LinkedHashMap<Integer, ArrayList<Integer>>();
        suspendTaskMap = new LinkedHashMap<Integer, TaskInfo>();
        runningTaskMap = new LinkedHashMap<Integer, TaskInfo>();
    }


    public Map<Integer, TaskInfo> getSuspendTaskMap() {
        return suspendTaskMap;
    }

    public void setSuspendTaskMap(Map<Integer, TaskInfo> suspendTaskMap) {
        this.suspendTaskMap = suspendTaskMap;
    }

    public Map<Integer, TaskInfo> getRunningTaskMap() {
        return runningTaskMap;
    }

    public void setRunningTaskMap(Map<Integer, TaskInfo> runningTaskMap) {
        this.runningTaskMap = runningTaskMap;
    }



    public Map<Integer, ArrayList<Integer>> getNodeTaskMap() {
        return nodeTaskMap;
    }

    public void setNodeTaskMap(Map<Integer, ArrayList<Integer>> nodeTaskMap) {
        this.nodeTaskMap = nodeTaskMap;
    }
}
