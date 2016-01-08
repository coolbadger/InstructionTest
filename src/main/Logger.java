package main;

/**
 * Created by Badger on 16/1/8.
 */
public class Logger implements LogListener {
    private LogListener logListener;

    //添加对日志的监听
    public void addListener(LogListener logListener) {
        this.logListener = logListener;
    }

    @Override
    public void info(String infoStr) {
        System.out.println(infoStr);
    }
}
