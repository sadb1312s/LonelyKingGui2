package sample;

import static javafx.scene.paint.Color.RED;
import static sample.Main.*;
import static sample.Main.cellSize;
import static sample.Main.gc;

public class GraphicsThread extends Thread{
    GraphicsThread(){
    }

    @Override
    public void run(){
        for (int i=1;i>0;i++) {
            gc.clearRect(0,0,700,10);
            gc.fillRect(i, 1, 10, 10);
            gc.setFill(RED);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
