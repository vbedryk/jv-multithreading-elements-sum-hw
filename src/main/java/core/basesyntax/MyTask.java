package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private final int startPoint;
    private final int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if ((finishPoint - startPoint) > 10) {
            List<RecursiveTask<Long>> tasks = new ArrayList<>(createSubTasks());
            for (RecursiveTask<Long> task : tasks) {
                task.fork();
            }
            long result = 0;
            for (RecursiveTask<Long> subTask : tasks) {
                result += subTask.join();
            }
            return result;
        } else {
            long result = 0;
            for (int i = startPoint; i < finishPoint; i++) {
                result += i;
            }
            return result;
        }
    }

    private List<RecursiveTask<Long>> createSubTasks() {
        List<RecursiveTask<Long>> subTasks = new ArrayList<>();
        RecursiveTask<Long> first = new MyTask(startPoint, findCenter(startPoint, finishPoint));
        RecursiveTask<Long> second = new MyTask(findCenter(startPoint, finishPoint), finishPoint);
        subTasks.add(first);
        subTasks.add(second);
        return subTasks;
    }

    private Integer findCenter(Integer start, Integer finish) {
        return start + (finish - start) / 2;
    }

}
