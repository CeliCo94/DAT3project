package main.java.dk.propertymanagement.api.apartment;

public class Apartment {

    // adress
    private String city;
    private String street;
    private int houseNumber;

    // description
    private int noOfRooms;
    private int houseSize;
    private String houseDescription = "";

    Apartment(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public void addDescription(int noOfRooms, int houseSize, String houseDescription) {
        this.noOfRooms = noOfRooms;
        this.houseSize = houseSize;
        this.houseDescription = houseDescription;
    }

    public void printHouse() {
        System.out.println("This is a lovely house located at " + this.street + " number " + this.houseNumber + " in " + this.city);

        if ((noOfRooms == 0 || houseSize == 0) && houseDescription == "") System.out.println("The house is so lovely that you should check it out yourself");
        else { 
            System.out.println("Here is a little information about the house");
            if (!(noOfRooms == 0)) System.out.println("Number of rooms: " + noOfRooms);
            if (!(houseSize == 0)) System.out.println("The size of the house: " + houseSize);
            if (!(houseDescription == "")) System.out.println(houseDescription);
        }
    }

}
