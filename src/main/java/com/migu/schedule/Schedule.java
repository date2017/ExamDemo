package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.RegisterInfo;
import com.migu.schedule.info.TaskInfo;
import com.sun.org.apache.regexp.internal.RE;
import javafx.concurrent.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
*类名和方法不能修改
 */
public class Schedule {
    //注册中心信息
    private RegisterInfo registerInfo = null;


    public int init() {
        synchronized (this){
            if(registerInfo == null)
                registerInfo = new RegisterInfo();
            else{
                registerInfo.getNodeTaskMap().clear();
                registerInfo.getSuspendTaskMap().clear();
                registerInfo.getRunningTaskMap().clear();
            }
            return ReturnCodeKeys.E001;
        }
    }


    public int registerNode(int nodeId) {
        synchronized (this){
            if(nodeId <=0 )
                return ReturnCodeKeys.E004;
            if(registerInfo.getNodeTaskMap().containsKey(nodeId))
                return ReturnCodeKeys.E005;
            registerInfo.getNodeTaskMap().put(nodeId, new ArrayList<Integer>());
            return ReturnCodeKeys.E003;
        }
    }

    public int unregisterNode(int nodeId) {
        synchronized (this){
            if(nodeId <= 0)
                return ReturnCodeKeys.E004;
            if(!registerInfo.getNodeTaskMap().containsKey(nodeId))
                return ReturnCodeKeys.E007;

            ArrayList<Integer> taskList = registerInfo.getNodeTaskMap().get(nodeId);
            if(taskList.isEmpty()){
                registerInfo.getNodeTaskMap().remove(nodeId);//直接删除服务节点
                return ReturnCodeKeys.E006;
            }
            //如果该服务节点正运行任务， 将运行的任务移到任务挂起队列中
           for(Integer taskId : taskList){
               TaskInfo taskInfo = registerInfo.getRunningTaskMap().get(taskId);
               if(taskInfo != null)
                 registerInfo.getSuspendTaskMap().put(taskId, taskInfo);
               registerInfo.getRunningTaskMap().remove(taskId);
           }
            registerInfo.getNodeTaskMap().remove(nodeId);//直接删除服务节点
            return ReturnCodeKeys.E006;
        }
    }


    public int addTask(int taskId, int consumption) {
        synchronized (this){
            if(taskId <= 0 || consumption <= 0)
                return ReturnCodeKeys.E009;
            if(registerInfo.getRunningTaskMap().get(taskId) != null ||
                    registerInfo.getSuspendTaskMap().get(taskId) != null)
                return ReturnCodeKeys.E010;
            TaskInfo taskInfo = new TaskInfo(0, taskId, consumption);
            registerInfo.getSuspendTaskMap().put(taskId, taskInfo);
            return ReturnCodeKeys.E008;
        }
    }


    public int deleteTask(int taskId) {
        synchronized (this){
            if(taskId <= 0)
                return ReturnCodeKeys.E009;
            Boolean isTaskExist =(registerInfo.getSuspendTaskMap().get(taskId) != null ||  registerInfo.getRunningTaskMap().get(taskId) != null);
            if(!isTaskExist)
                return ReturnCodeKeys.E012;

            //将在挂起队列中的任务删除
            TaskInfo taskInfo = registerInfo.getSuspendTaskMap().get(taskId);
            if(taskInfo != null){
                registerInfo.getSuspendTaskMap().remove(taskId);
                return ReturnCodeKeys.E011;
            }
            //从在运行节点中删除此任务
            taskInfo = registerInfo.getRunningTaskMap().get(taskId);
            if(taskInfo != null){
                ArrayList<Integer> taskIdList = registerInfo.getNodeTaskMap().get(taskId);
                Iterator<Integer> taskIter = taskIdList.iterator();
                while(taskIter.hasNext()){
                    if(taskIter.next().equals(taskId)){
                        taskIter.remove();
                    }
                }
                registerInfo.getNodeTaskMap().put(taskId, taskIdList);
                registerInfo.getRunningTaskMap().remove(taskId);
                taskInfo = null;
            }
            return ReturnCodeKeys.E011;
        }
    }


    public int scheduleTask(int threshold) {
        synchronized (this){
            if(threshold <= 0)
                return ReturnCodeKeys.E002;
            // 如果当前有挂起的任务，则进行任务调度
            if(registerInfo.getSuspendTaskMap().size() > 0)
            {
                RegisterInfo tempRregisterInfo = registerInfo;
                Map<Integer/*nodeId*/,ArrayList<Integer/*taskId*/>> nodeTaskMap = new LinkedHashMap<Integer, ArrayList<Integer>>();
                Map<Integer/*nodeId*/,TaskInfo> suspendTaskMap =  new LinkedHashMap<Integer, TaskInfo>();
                Map<Integer/*nodeId*/,TaskInfo> runningTaskMap = new LinkedHashMap<Integer, TaskInfo>();
               // Map<Integer/*nodeId*/, Integer/*耗时*/>
                //采用
                return ReturnCodeKeys.E013;
            }

            //if(registerInfo.getRunningTaskMap().size() > 0){

            //}
            //没有挂起的任务，进行最佳调度策略
            return ReturnCodeKeys.E013;
        }
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        synchronized (this) {
            if(null == tasks || tasks.isEmpty())
                return ReturnCodeKeys.E016;
            tasks.clear();
            //获取挂起的任务列表
            Set<Integer> suspendIdSet = registerInfo.getRunningTaskMap().keySet();
            ArrayList<Integer> suspendIdList = new ArrayList<Integer>();
            if(!suspendIdSet.isEmpty()) {
                suspendIdList.addAll(suspendIdSet);
                Collections.sort(suspendIdList);
            }
            for(Integer taskId : suspendIdList){
                TaskInfo taskInfo = registerInfo.getSuspendTaskMap().get(taskId);
                if(null != taskInfo) {
                    taskInfo.setNodeId(-1);
                    tasks.add(taskInfo);
                }
            }
            // 获取在运行的任务列表
            Set<Integer> runningIdSet = registerInfo.getRunningTaskMap().keySet();
            ArrayList<Integer> runningIdList = new ArrayList<Integer>();
            if(!runningIdSet.isEmpty()) {
                runningIdList.addAll(runningIdList);
                Collections.sort(runningIdList);
            }
            for(Integer taskId : runningIdList){
                TaskInfo taskInfo = registerInfo.getRunningTaskMap().get(taskId);
                if(null != taskInfo)
                    tasks.add(taskInfo);
            }
            return ReturnCodeKeys.E015;
        }
    }

}
