package main;

import data_classes.Athlete;
import file_tools.AddContent;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList <Athlete> athletes = new ArrayList<>();

    public static void readFile() {
        Athlete objectAthlete;

        try {
            FileInputStream file = new FileInputStream("D:\\files\\binaries\\athletes.bin");
            ObjectInputStream read = new ObjectInputStream(file);

            while (true) {
                try{
                    objectAthlete = (Athlete) read.readObject();
                    athletes.add(objectAthlete);
                }
                catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            read.close();

        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile() {
        try {
            FileOutputStream file = new FileOutputStream("D:\\files\\binaries\\athletes.bin");
            ObjectOutputStream write = new ObjectOutputStream(file);

            if (athletes.size() > 0) {
                for (Athlete athelete : athletes) {
                    write.writeObject(athelete);
                }

                write.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: "+e);
        } catch (IOException e) {
            System.out.println("Error: "+e);
        }
    }

    public static void addContent(String name, int age, double weight, double height) {
        try {
            FileOutputStream file = new FileOutputStream("D:\\files\\binaries\\athletes.bin",true);
            AddContent write = new AddContent(file);

            Athlete athlete = new Athlete(name,age,weight,height);

            athletes.add(athlete);

            write.writeObject(athlete);
            write.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showContent() {
        int counter = 1;
        for (Athlete athlete : athletes) {
            System.out.println("\nAthlete Nº"+counter);
            System.out.println("Name: "+athlete.getName());
            System.out.println("Age: "+athlete.getAge());
            System.out.println("Weight: "+athlete.getWeight());
            System.out.println("Height: "+athlete.getHeight());
            counter++;
        }
    }

    private static void menu() throws FileNotFoundException {
        File file = new File("D:\\files\\binaries\\athletes.bin");
        Scanner sc = new Scanner(System.in);
        String name;
        int age;
        double weight;
        double height;
        int option = 1;

        while (option != 0) {
            System.out.println("\n1. Add content");
            System.out.println("2. Read content");
            System.out.println("0. Exit");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("\nIntroduce name:");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.println("Introduce age:");
                    age = sc.nextInt();
                    System.out.println("Introduce weight:");
                    weight = sc.nextDouble();
                    System.out.println("Introduce height:");
                    height = sc.nextDouble();


                    if (file.exists()){
                        addContent(name,age,weight,height);

                    }
                    else{
                        createFile();
                        addContent(name,age,weight,height);
                    }
                    break;
                case 2:
                    if (file.exists()){
                        showContent();
                    }
                    else{
                        System.out.println("No athletes registered");
                    }
                    break;
                case 0:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Incorrect option");
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        menu();
    }
}