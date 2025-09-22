package main.java.dk.propertymanagement.api.app;

import java.util.Scanner;

import main.java.dk.propertymanagement.api.apartment.Apartment;
import main.java.dk.propertymanagement.api.apartment.ApartmentInput;

public class RunApp {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Will you enter a house? ");
            String answer = scanner.nextLine();
            
            if (answer.equals("Y")) {
                Apartment apartment = ApartmentInput.makeHouse();
                apartment.printHouse();
            }
            
            scanner.close();
        }
}
