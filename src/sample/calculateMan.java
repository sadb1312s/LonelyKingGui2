package sample;

import javafx.concurrent.Task;

import static sample.Main.*;

public class calculateMan extends Task {
    int number;
    int start;
    int stop;
    int N;
    int Count;
    int j=0;
    int Max;

    calculateMan(int start, int N, int number, int stop) {
        //System.out.println("Поток " + number + " Создание");
        //System.out.println("Граница потока "+number+" c "+start+" по "+stop+" включительно ");
        this.start = start;
        this.N = N;
        this.number = number;
        this.stop = stop;

    }

    @Override
    public Integer call() {

        if(ThreadVariant==1){
            Max=N;
        }
        if(ThreadVariant==2){
            Max=N/8;
        }

        for (int i = start; i <=stop; i++) {
            j++;
            Count=1;
            for (int j = 0; j < N + 1; j++) {
                if (stepX[i] == stepX[j] && stepY[i] == stepY[j]) {
                    if (i != j) {
                        Count++;

                    }
                }
            }
            count[i] = Count;
            if (count[i] > 1) {
                CountStringTest++;
                list.add("Король был поле c координатами: X=" + stepX[i] + " Y=" + stepY[i] + " " + Count +
                        " раз(а)" + "\n");
            }
            this.updateProgress(j, Max);
            //System.out.println("Поток "+number+" "+i);
        }
        StopFlag++;
        //System.out.println("Поток "+number+"  стоп");
        return 1;
    }
}
