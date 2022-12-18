package org.example;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;


public class Main {
    public static void main(String[] args) {
        // задача 1
//        jsonToSql();

        // задача 3
//        jsonToString();

        // задача 2
//        int [] arrya = {1, 3, 8, 0, 15, -7, 10, 6, 3, -4};
//        sortBubble(arrya);

        //задача 4
        task3Calc();

    }

    public static void jsonToSql(){
        String jsonString = " {\"name\":\"Ivanov\", \"country\":\"Russia\", \"city\":\"Moscow\", \"age\":\"null\"}";
        StringBuilder sql = new StringBuilder();
        sql.append( "select * from students where ");

        JSONObject obj = new JSONObject(jsonString);

        for (String i : obj.keySet()) {
            if (!obj.getString(i).equals("null")){
                if(sql.toString().endsWith("where "))
                    sql.append(i).append(" = ").append(obj.getString(i));
                else {
                    sql.append(" and ").append(i).append(" = ").append(obj.getString(i));
                }
            }
        }

        System.out.println(sql);
    }
   /*
    Написать метод(ы), который распарсит json и, используя StringBuilder, создаст строки вида:
    */
    public static  void jsonToString()  {
        try {
            //FileReader reader = new FileReader("Students.json");

            // считываем весь файл в строку
            List<String> lines = Files.readAllLines(Paths.get("Students.json"));
            String reader = String.join(System.lineSeparator(), lines);

            // парсинг json
            JSONObject obj = new JSONObject(reader);

            JSONArray stud = (JSONArray) obj.get("успеваемость");

            // берем каждое значение из массива json отдельно
            Iterator i = stud.iterator();
            while(i.hasNext()){
                JSONObject oneStud = (JSONObject) i.next();
                System.out.println("Студент "+ oneStud.get("фамилия") +
                        " получил " + oneStud.get("оценка") +
                        " по предмету "  + oneStud.get("предмет"));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /*Реализуйте алгоритм сортировки пузырьком числового массива,
    результат после каждой итерации запишите в лог-файл*/
    public static void  sortBubble(int [] arr){
        try(FileWriter writer = new FileWriter("BubbleSort.txt", false))
        {
            writer.write( "Массив :\n");
            writer.write(Arrays.toString(arr) + "\n");
            writer.write( "Сортировка пузырьком :\n");
            int n = arr.length;
            int temp = 0;
            for (int k = n-1 ; k >0 ; k--) {
                for (int i = 0; i < k; i++) {
                    if(arr[i] > arr[i+1]){
                        temp = arr[i];
                        arr[i] = arr[i+1];
                        arr[i+1] = temp;
                    }
                }
                writer.write(Arrays.toString(arr) + "\n");
            }
            writer.flush();
            System.out.println("готово!");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

/*калькулитор и логирование */
    public static  void task3Calc(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите первое число: ");
        double num1 = in.nextDouble();
        System.out.println("Введите операцию: ");
        char op = in.next().charAt(0);
        System.out.println("Введите второе число: ");
        double num2 = in.nextDouble();
        double rez = 0;
        boolean err = false;
        switch (op){
            case '+' : rez = num1 + num2;
                break;
            case '-' : rez = num1 - num2;
                break;
            case '/' : rez = num1 / num2;
                break;
            case '*' : rez = num1 * num2;
                break;
            default:
                System.out.println("что-то пошло не так ");
                err = true;
                break;
        }
        if (!err) System.out.printf("%.2f %c %.2f = %.2f \n", num1, op , num2, rez );

        // вывод в файл
        try {
            FileWriter writer = new FileWriter("CaclLog.txt", true);
            writer.write( "\nВведено выражение :" +" " + Double.toString(num1)+" "
                    + Character.toString(op)+" " + Double.toString(num2) + "\n");
            if (err) writer.write("Вывод: что-то пошло не так");
            else writer.write("Выведен результат: " + Double.toString(rez) + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}