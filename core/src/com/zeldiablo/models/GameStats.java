package com.zeldiablo.models;

import com.badlogic.gdx.utils.Timer;

public class GameStats {

    private int score;
    private Timer timer;
    protected Timer.Task timeTask;
    private int time;

    public GameStats(){
        this.score = 0;
        this.time = 0;
        this.timer = new Timer();
        this.timer.start();
        this.timeTask = new Timer.Task() {
            @Override
            public void run() {
                time++;
                System.out.println(time);
            }
        };
        timer.scheduleTask(timeTask,1.0f,1.0f);
    }

    public void startTimer(){
        timer.start();
    }

    public  void stopTimer(){
        timer.stop();
    }

    /**
     * @return
     */
    public int getTime(){
        return this.time;
    }

    /**
     * @return
     */
    public int getScore(){
        return this.score;
    }

    public void increaseScore(){
        this.score += 10;
    }
}
