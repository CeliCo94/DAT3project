package main.java.dk.propertymanagement.api.apartment;

import java.util.Scanner;

public class ApartmentInput {
    
    public static Apartment makeHouse() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        System.out.print("Enter Street: ");
        String street = scanner.nextLine();

        System.out.print("Enter number: ");
        String numberString  = scanner.nextLine();
        int number = Integer.parseInt(numberString);

        scanner.close();
        
        return new Apartment(city, street, number);
    }
}

