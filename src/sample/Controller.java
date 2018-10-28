package sample;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.*;
import static sample.Main.*;


public class Controller {


    //графика
    @FXML
    private TextField countStep;
    @FXML
    private StackPane KingWay;
    @FXML
    private Button InButton;
    @FXML
    private TextArea StepWay;
    @FXML
    private TextArea ResultField;
    @FXML
    private Button ResultButton;
    @FXML
    TextField TimeField;
    @FXML
    private Button Rand;
    @FXML
    private Button FileButton;
    @FXML
    private Button Keybord;
    @FXML
    private AnchorPane RandArea;
    @FXML
    private AnchorPane KeyArea;
    @FXML
    private TextField KeyIn;
    @FXML
    private Button START;
    @FXML
    private AnchorPane FileArea1;
    @FXML
    private Text BigText;
    @FXML
    private Button OnePotok;



    //инициализация графики
    @FXML
    void initialize() {
        //кнопки
        Waylist = new ArrayList <Integer>();
        //переменные
        KingWay.getChildren().add( canvas );
        //key in
        Keybord.setOnAction(event -> {
            BigText.setVisible(false);
            N=0;
            allWay="";
            Waylist.clear();

            StepWay.setText(allWay);
            RandArea.setVisible(false);
            KeyArea.setVisible(true);
            FileArea1.setVisible(false);
            setFocus();

        });
        START.setOnAction(event -> {
            N=n;
            n=0;
            run();
            Waylist.clear();
        });
        KeyIn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                Waylist.add(3);
                allWay += " left\n";
                StepWay.setText(allWay);
                n++;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                Waylist.add(4);
                allWay += " right\n";
                StepWay.setText(allWay);
                n++;
            }
            if (event.getCode() == KeyCode.UP) {
                Waylist.add(1);
                allWay += " up\n";
                StepWay.setText(allWay);
                n++;
            }
            if (event.getCode() == KeyCode.DOWN) {
                Waylist.add(2);
                allWay += " down\n";
                StepWay.setText(allWay);
                n++;
            }
        });
        //rand in
        Rand.setOnAction(event -> {
            BigText.setVisible(false);
            N=0;
            Waylist.clear();

            RandArea.setVisible(true);
            KeyArea.setVisible(false);
            FileArea1.setVisible(false);
        });
        InButton.setOnAction(e -> {
            N=0;
            Waylist.clear();

            Integer n = Integer.valueOf(countStep.getText());
            str=Integer.toString(n);
            N=Integer.parseInt(str);

            Random random = new Random();
            for(int i=1;i<N+1;i++){

                Waylist.add(1+random.nextInt(4));
            }

            run();


        });
        //file in
        FileButton.setOnAction(event -> {
            BigText.setVisible(false);
            N=0;
            Waylist.clear();
            RandArea.setVisible(false);
            KeyArea.setVisible(false);
            FileArea1.setVisible(true);

            //Проверка есть ли диретория
            Path path = Paths.get("C:\\InKingWayFile");

            if(Files.exists(path)){
                //System.out.println("Есть директория");
            }else{
                File file = new File("C:\\InKingWayFile");
                file.mkdir();
                //System.out.println("Создали католог");
            }

            //Проверка есть ли файл
            File file = new File("C:\\InKingWayFile\\InKingWayFile.txt");
            if (file.exists()) {
                //System.out.println("Файл существует.");
            } else {
                File file2 = new File("C:\\InKingWayFile\\InKingWayFile.txt");
                try {
                    file2.createNewFile();
                    //запись инструкции
                    String instruction ="//random step\n" +
                            "//1-up\n" +
                            "//2-down\n" +
                            "//3-left\n" +
                            "//4-right\\\n" +
                            "Vvedite shagi posle etoi stroki(udalite primer)\n" +
                            "1\n" +
                            "2\n" +
                            "3\n" +
                            "2\n" +
                            "2\n" +
                            "1\n" +
                            "3\n" +
                            "4\n" +
                            "4";

                    //"C:\\InKingWayFile\\InKingWayFile.txt"
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\InKingWayFile\\InKingWayFile.txt"));
                    writer.write(instruction);
                    writer.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("Файл создан.");
            }
            //read file
            BufferedReader r;
            String lineTemp;
            int i=0;
            try {
                r = new BufferedReader(new FileReader("C:\\InKingWayFile\\InKingWayFile.txt"));

                try {
                    while ((lineTemp = r.readLine()) != null) {
                        i++;
                        if(i>6) {
                            N++;
                            Waylist.add(Integer.parseInt(lineTemp));
                            //System.out.println(lineTemp);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            run();


        });
        //calculate
        ResultButton.setOnAction(event -> {
            //сколько был на каждом поле
            start = System.currentTimeMillis();
            try {
                FindCell();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        OnePotok.setOnAction(event -> {
            //сколько был на каждом поле
            start = System.currentTimeMillis();
            FindCellOneThread();
        });
    }
    //на основе заданных шагов вычисляет координаты каждой точки на которой был король
    private void run(){
        cellSize= (float) (700/((N+0.5)*2));
        n=0;
        allWay="";
        gc.clearRect(0,0,700,700);
        //time
        start=0;
        finish=0;
        timeConsumedMillis=0;


        stepX=new int[2*N];//step x and y
        stepY=new int[2*N];
        //Way Start
        stepX[0]=N;
        stepY[0]=N ;
        if(N>=300){
            cellSize=1;
        }

        directionArray= new int[N+1];
        //ограничение вывода шагов
        int x=0;
        if(N<100) x=N+1;
        if(N>=100) x=100;

        /*for(int i=0;i< x;i++) {*/

        //way text print
        int J=1;
        for(Integer object:Waylist){

            switch ( object) {
                case 1:
                    allWay += J+" up\n";
                    break;
                case 2:
                    allWay += J+" down\n";
                    break;
                case 3:
                    allWay += J+" left\n";
                    break;
                case 4:
                    allWay +=  J+" right\n";
                    break;
            }
            //System.out.println(J);
            J++;
            if (J == 1000) {
                allWay += "...\n";
                break;
            }

        }

        StepWay.setText(allWay);


        //x y считаем
        /*for(int i=1;i<N+1;i++){*/
        int I=1;
        for(Integer object:Waylist){
            //System.out.println(I);
            switch (object){
                case 1:
                    stepY[I]=stepY[I-1]-1;
                    stepX[I]=stepX[I-1];
                    break;
                case 2:
                    stepY[I]=stepY[I-1]+1;
                    stepX[I]=stepX[I-1];
                    break;
                case 3:
                    stepX[I]=stepX[I-1]-1;
                    stepY[I]=stepY[I-1];
                    break;
                case 4:
                    stepX[I]=stepX[I-1]+1;
                    stepY[I]=stepY[I-1];
                    break;


            }
            //System.out.println(object+ " x "+stepX[I]+" y "+ stepY[I]);
            if( stepX[I]<0||stepY[I]<0){
                    break;
            }
            I++;

        }
        if(cellSize>2){paint();}else {BigText.setVisible(true);}
    }
    //Рисование
    private void paint(){
        //System.out.println(N);
        gc.clearRect(0,0,700,700);

        for(int i=0;i<N+1;i++){
            gc.fillRect(stepX[i]*cellSize,stepY[i]*cellSize,cellSize,cellSize);
            gc.setFill(BLUE);

        }
        gc.fillRect(stepX[N]*cellSize,stepY[N]*cellSize,cellSize,cellSize);
        gc.setFill(RED);
        gc.fillRect(stepX[N]*cellSize,stepY[N]*cellSize,cellSize,cellSize);
        gc.setFill(RED);
        gc.fillRect(stepX[0]*cellSize,stepY[0]*cellSize,cellSize,cellSize);
        gc.setFill(GREEN);
        gc.fillRect(stepX[0]*cellSize,stepY[0]*cellSize,cellSize,cellSize);
        gc.setFill(GREEN);





        //сетка

        if(N<=20) {
            for (int i = 0; i < N * 2+1; i++) {
                //вертикальные
                for (int j = 0; j < N * 2+1; j++) {
                    gc.fillRect(i * cellSize - 1, j * cellSize, 1, cellSize);
                    gc.setFill(BLACK);
                    gc.fillRect(i * cellSize - 1, j * cellSize, 1, cellSize);
                    gc.setFill(BLACK);

                }
                gc.fillText(
                        Integer.toString(i),
                        3,
                        i * cellSize+10,
                        20
                );

            }
            //горизонтальные
            for (int i = 0; i < N * 2+1; i++) {
                for (int j = 0; j < N * 2+1; j++) {
                    gc.fillRect(j * cellSize, i * cellSize - 1, cellSize, 1);
                    gc.setFill(BLACK);
                    gc.fillRect(j * cellSize, i * cellSize - 1, cellSize, 1);
                    gc.setFill(BLACK);
                }
                gc.fillText(
                        Integer.toString(i),
                        i * cellSize+2,
                        10,
                        20
                );
            }
        }
    }
    //Поиск в 1 поток
    private void FindCellOneThread(){
        list = new ArrayList <String>();
        ResultString = "";
        count = new int[N + 1];
        CountString = new String[N + 1];
        CountStringTest=0;
        resultStringTest=null;


            //System.out.println("Считаем в один поток");
            //step 1
            for (int i = 0; i < N + 1; i++) {
                int Count = 1;
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
                }

                CountString[i] = ("Король был поле c координатами: X=" + stepX[i] + " Y=" + stepY[i] + " " + Count +
                        " раз(а)" + "\n");
                //System.out.println(i);
            }


            resultStringTest = new String[CountStringTest];


            //System.out.println("----Отсеиваем <2----");
            int j = 0;
            for (int i = 0; i < N; i++) {
                if (count[i] > 1) {
                    System.out.println(i+" "+" count "+count[i]+resultStringTest[j]+"="+CountString[i]);
                    resultStringTest[j] = CountString[i];

                    j++;
                }
            }
            //System.out.println("----Отсеиваем-Одинаковые строки----");
            Set<String> set = new HashSet<String>(Arrays.asList(resultStringTest));

            //System.out.println("HashSet contains: " + set);
            String[] array = new String[set.size()];
            set.toArray(array);

            //System.out.println("Array elements: ");
            for (String temp : array) {
                if (temp != null) {
                    ResultString += temp;
                }
            }


            finish = System.currentTimeMillis();
            timeConsumedMillis = finish - start;

            if (ResultString.equals("")) {
                ResultString = "Небыло такого";


                ResultField.setText(ResultString);


            }
            TimeField.setText(Long.toString(timeConsumedMillis) + " Милисекунд");
            ResultField.setText(ResultString);
            //System.out.println("finish");


    }
    //поиск в 8 потоков
    private void FindCell() throws InterruptedException {
        list = new ArrayList <String>();
        ResultString = "";
        count = new int[N + 1];
        CountString = new String[N + 1];
        CountStringTest=0;
        resultStringTest=null;


        int N2 = N;//чтобы ровно поделилось
        if (N2 != 0) {
            while (N2 % 8 != 0) {
                N2--;
            }
        }
        //Потоки
        calculateManThread1 = new calculateMan(0, N2, 1, N2 / 8);
        calculateManThread2 = new calculateMan((N2 / 8) + 1, N, 2, (2 * N2) / 8);
        calculateManThread3 = new calculateMan(((2 * N2) / 8) + 1, N2, 3, (3 * N2) / 8);
        calculateManThread4 = new calculateMan(((3 * N2) / 8) + 1, N2, 4, (4 * N2) / 8);
        calculateManThread5 = new calculateMan(((4 * N2) / 8) + 1, N2, 5, (5 * N2) / 8);
        calculateManThread6 = new calculateMan(((5 * N2) / 8) + 1, N2, 6, (6 * N2) / 8);
        calculateManThread7 = new calculateMan(((6 * N2) / 8) + 1, N2, 7, (7 * N2) / 8);
        calculateManThread8 = new calculateMan(((7 * N2) / 8) + 1, N2, 8, N2);
        calculateManThread8 = new calculateMan((N2) + 1, N2, 9, N);

        calculateManThread1.start();
        calculateManThread2.start();
        calculateManThread3.start();
        calculateManThread4.start();
        calculateManThread5.start();
        calculateManThread6.start();
        calculateManThread7.start();
        calculateManThread8.start();

        if(N2 % 8 != 0) {
            calculateManThread9.start();
        }
        //Ожидание остановки всех потоков
        calculateManThread1.join();
        calculateManThread2.join();
        calculateManThread3.join();
        calculateManThread4.join();
        calculateManThread5.join();
        calculateManThread6.join();
        calculateManThread7.join();
        calculateManThread8.join();

        if(N2 % 8 != 0) {
            calculateManThread9.join();
        }

        resultStringTest = list.toArray(new String[list.size()]);
        //Удаление одниковых строк для вывода
        Set<String> set = new HashSet<String>(Arrays.asList(resultStringTest));
        ResultString= String.valueOf(set);

        finish = System.currentTimeMillis();
        timeConsumedMillis = finish - start;

        if (ResultString.equals("")||ResultString.equals(null)||ResultString.equals("[]")) {
            ResultString = "Небыло такого";
            ResultField.setText(ResultString);
        }
        TimeField.setText(Long.toString(timeConsumedMillis) + " Милисекунд");
        ResultField.setText(ResultString);
        //System.out.println("finish");
        }

    private void setFocus(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                KeyIn.requestFocus();
            }
        });
    }
    }

