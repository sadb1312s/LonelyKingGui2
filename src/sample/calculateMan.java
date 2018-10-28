package sample;

import javafx.concurrent.Task;

import static sample.Main.*;

public class calculateMan extends Task {
    int number;
    int start;
    int stop;
    int N;
    int Count;

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
        for (int i = start; i <=stop; i++) {
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

            //System.out.println("Поток "+number+" "+i);
        }
        StopFlag++;
        //System.out.println("Поток "+number+"  стоп");
        return 1;
    }
}
