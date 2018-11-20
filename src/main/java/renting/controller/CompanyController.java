package renting.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import renting.exception.AfterReturnDateException;
import renting.exception.RentingException;
import renting.model.Bike;
import renting.model.Company;
import renting.model.RentalBike;
import renting.service.CompanyService;

public class CompanyController {
	
	static CompanyService companyService = new CompanyService();

	public static RentalBike rentBike(String company, String rentalMode,Integer timeUnits) throws RentingException {
		return rentBike(company, rentalMode,null,timeUnits);
	}
	
	public static RentalBike rentBike(Company company, String rentalMode,Integer timeUnits) throws RentingException {
		return rentBike(company, rentalMode,null,timeUnits);
	}

	public static RentalBike rentBike(String companyName, String rentalMode, Date rentalDate,Integer timeUnits) throws RentingException {
		Company company = companyService.getCompany(companyName);
		return rentBike(company, rentalMode, rentalDate, timeUnits);
	}

	public static RentalBike rentBike(Company company, String rentalMode, Date rentalDate, Integer timeUnits) throws RentingException {
		Bike bikeToRent= company.getBikeToRent();
		RentalBike rentalBike = new RentalBike(bikeToRent,rentalDate,timeUnits,rentalMode);
		company.addRentalBike(rentalBike);
		company.removeBike(bikeToRent);
		return rentalBike;
	}

	public static void returnRentedBike(String companyName, Bike bike, double money) throws RentingException,AfterReturnDateException{
		returnRentedBike(companyName,bike,null,money);
	}

	public static void returnRentedBike(String companyName,Bike bike, Date returnDate,double money) throws RentingException,AfterReturnDateException{
		Company company = companyService.getCompany(companyName);
		returnRentedBike(company, bike, returnDate, money);
	}
	
	public static void returnRentedBike(Company company, Bike bike, double money) throws RentingException, AfterReturnDateException {
		returnRentedBike(company, bike, null, money);
	}

	public static void returnRentedBike(Company company, Bike bike, Date returnDate, double money) throws RentingException, AfterReturnDateException {
		RentalBike rentalBike = company.getRentalBikeFromBike(bike);
		returnDate = returnDate!=null?returnDate:new Date();
		returnRentedBikeValidations(returnDate, money, rentalBike);
		company.removeRentalBike(rentalBike);
		company.addBike(bike);
	}

	private static void returnRentedBikeValidations(Date returnDate, double money, RentalBike rentalBike)
			throws RentingException, AfterReturnDateException {
		if(money != rentalBike.getPrice()){
			throw new RentingException("Money delivered ("+money+"$) does not match expected amount ("+rentalBike.getPrice()+"$).");
		}
		if(returnDate.after(rentalBike.getReturnDate())){
			throw new AfterReturnDateException("Delivery date ("+returnDate+") is after expected date ("+rentalBike.getReturnDate()+").");
		}
	}

	public List<RentalBike> familyRental(String companyName, String rentalMode,Date rentalDate, Integer timeUnits, Integer amountOfBikes) throws RentingException{
		Company company = companyService.getCompany(companyName);
		return familyRental(company, rentalMode, rentalDate, timeUnits, amountOfBikes);
	}
		
	public List<RentalBike> familyRental(String companyName, String rentalMode,Integer timeUnits, Integer amountOfBikes) throws RentingException{
		return familyRental(companyName, rentalMode, null, timeUnits, amountOfBikes);
	}	

	public static List<RentalBike> familyRental(Company company, String rentalMode, Integer timeUnits,Integer amountOfBikes) throws RentingException {
		return familyRental(company, rentalMode, null, timeUnits, amountOfBikes);
	}	
	
	public static List<RentalBike> familyRental(Company company, String rentalMode, Date rentalDate, Integer timeUnits,Integer amountOfBikes) throws RentingException {
		familyRentalValidations(company,amountOfBikes);
		List<RentalBike> rentalBikes = new ArrayList<RentalBike>();
		for(Integer i=0;i<amountOfBikes;i++){
			RentalBike rentalBike = rentBike(company,rentalMode,rentalDate, timeUnits);
			rentalBike.setDiscount(company.getDiscount());
			rentalBikes.add(rentalBike);
		}
		return rentalBikes;
	}

	private static void familyRentalValidations(Company company, Integer amountOfBikes) throws RentingException {
		if(company.getBikes().size()<amountOfBikes){
			throw new RentingException ("There are not enough bikes to fill the requirement");
		}
		if(!company.getAmountsOfBikesInPromo().contains(amountOfBikes)){
			throw new RentingException("Family rental promo cannot be aplied to "+amountOfBikes+" bikes");
		}
	}
}
