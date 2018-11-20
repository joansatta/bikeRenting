package renting.model;

import java.util.ArrayList;
import java.util.List;

import renting.exception.RentingException;

public class Company {

	private List<Bike> bikes;
	private List<RentalBike> rentalBikes;
	private List<Integer> amountsOfBikesInPromo;
	private double discount;

	public Company(){}
	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void addBike(Bike bike){
		getBikes().add(bike);
	}

	public void removeBike(Bike bikeToRent) {
		getBikes().remove(bikeToRent);
	}

	public void addRentalBike(RentalBike bike){
		getRentalBikes().add(bike);
	}
	
	public void removeRentalBike(RentalBike bike) {
		getRentalBikes().remove(bike);
	}

	public List<Bike> getBikes() {
		return bikes;
	}

	public List<RentalBike> getRentalBikes() {
		return rentalBikes;
	}

	public void setBikes(Integer amountOfBikes) {
		this.bikes = new ArrayList<Bike>();
		this.rentalBikes = new ArrayList<RentalBike>();
		for(Integer i=0;i<amountOfBikes;i++){
			addBike(new Bike());
		}
	}
	
	public void setAmountsOfBikesInPromo(String amountsAux[]) {
		amountsOfBikesInPromo = new ArrayList<Integer>();
		for(String amount:amountsAux){
			amountsOfBikesInPromo.add(Integer.valueOf(amount));
		}
	}
	
	public Bike getBikeToRent() throws RentingException {
		if(bikes.isEmpty()){
			throw new RentingException("There are no available bikes for rent");
		}
		return bikes.get(bikes.size()-1);
	}

	public RentalBike getRentalBikeFromBike(Bike bike) throws RentingException {
		for(RentalBike rentalBike:rentalBikes){
			if(compareBikes(bike, rentalBike)){
				return rentalBike;
			}
		}
		throw new RentingException("Current bike is not rented");
	}
	
	public List<Integer> getAmountsOfBikesInPromo() {
		return amountsOfBikesInPromo;
	}

	private boolean compareBikes(Bike bike, RentalBike rentalBike) {
		return rentalBike.getBike()==bike;
	}


	
	
}
