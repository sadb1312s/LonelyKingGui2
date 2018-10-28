package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    //основные переменные
    static String str;
    static int N;
    static Canvas canvas = new Canvas( 700, 700 );
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static String ResultString="Небыло такого";

    static float cellSize;
    static int[] stepX;//step x and y
    static int[] stepY;
    static int[] directionArray;
    static int[] count;
    static String[] CountString;
    static calculateMan calculateManThread1;
    static calculateMan calculateManThread2;
    static calculateMan calculateManThread3;
    static calculateMan calculateManThread4;
    static calculateMan calculateManThread5;
    static calculateMan calculateManThread6;
    static calculateMan calculateManThread7;
    static calculateMan calculateManThread8;
    static calculateMan calculateManThread9;
    static GraphicsThread graphicsthread;
    static String[] resultStringTest;
    static int CountStringTest;
    static String allWay="";
    static int n=0;
    static int StopFlag;

    static ArrayList <String> list;
    static ArrayList <Integer> Waylist;

    static long start=0;
    static long finish=0;
    static long timeConsumedMillis=0;

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("KING WAY V1.3");
        primaryStage.setScene(new Scene(root, 1000, 770));

        primaryStage.getIcons().add(
                new Image(
                        Main.class.getResourceAsStream( "icon.png" )));


        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
