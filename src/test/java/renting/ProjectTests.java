package renting;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import renting.controller.CompanyController;
import renting.exception.AfterReturnDateException;
import renting.exception.RentingException;
import renting.model.Bike;
import renting.model.Company;
import renting.model.RentalBike;
import renting.service.CompanyService;
import renting.utils.ProjectPropertiesSingleton;
import renting.utils.Utils;

public class ProjectTests {
	static ProjectPropertiesSingleton properties;
	static Company company;
	static CompanyService companyService = new CompanyService();

	@BeforeClass
	public static void setUp() throws IOException, RentingException {
		properties = ProjectPropertiesSingleton.getInstance();
		company = companyService.getCompany("company");
	}

	@Test
	public void calculeReturnTime() throws RentingException{
		calculateReturnTime(8,1,"rentalByHour");
		calculateReturnTime(8,24,"rentalByDay");
		calculateReturnTime(8,24*7,"rentalByWeek");
	}

	public void calculateReturnTime(int timeUnits, int denominator, String mode) throws RentingException{
		RentalBike rentalBike = new RentalBike(new Bike(),null,timeUnits,mode);
		long diff = rentalBike.getReturnDate().getTime() - rentalBike.getRentalDate().getTime();
		long differenceInHours = diff / (1000 * 60 * 60 * denominator);
		assertTrue(differenceInHours==timeUnits);
	}

	@Test
	public void calculatePrice() throws RentingException{
		calculatePrice(8,"rentalByHour",40);
		calculatePrice(8,"rentalByDay",160);
		calculatePrice(8,"rentalByWeek",480);
	}

	public void calculatePrice(int timeUnits, String mode, int price) throws RentingException {
		RentalBike rentalBike = new RentalBike(new Bike(),null,timeUnits,mode);
		assertTrue(price==rentalBike.getPrice());	
	}

	@Test
	public void rentBike() throws RentingException, IOException{
		int bikes = company.getBikes().size();
		RentalBike rentalBike = CompanyController.rentBike(company,"rentalByHour", 7);
		double price = rentalBike.getPrice();
		assertTrue(company.getBikes().size()==bikes-1);
		assertTrue(price==35);
	}

	@Test
	public void rentAndReturnBike() throws RentingException, IOException{
		int bikes = company.getBikes().size();
		RentalBike rentalBike = CompanyController.rentBike(company,"rentalByHour", 7);
		Bike rentedBike = rentalBike.getBike();
		CompanyController.returnRentedBike(company,rentedBike, 35);
		assertTrue(company.getBikes().size()==bikes);
	}

	@Test(expected = AfterReturnDateException.class)
	public void rentAndReturnBikeLate() throws RentingException, IOException{
		RentalBike rentalBike = CompanyController.rentBike(company, "rentalByHour", 7);
		Bike rentedBike = rentalBike.getBike();
		Date returnDate = Utils.addHoursToDate(new Date(), 300);
		CompanyController.returnRentedBike(company, rentedBike, returnDate, 35);
	}

	@Test
	public void rentAndReturnBikeWithNotEnoughMoney() throws RentingException, IOException{
		RentalBike rentalBike = CompanyController.rentBike(company,"rentalByHour", 7);
		Bike rentedBike = rentalBike.getBike();
		try {
			CompanyController.returnRentedBike(company,rentedBike, 30);
		} catch (Exception e){
			assertTrue(e.getMessage().startsWith("Money delivered"));
		}
	}
	
	@Test
	public void familyRental() throws RentingException{		
		int bikes = company.getBikes().size();
		int timeUnits = 8;
		int amountOfBikesRented = 3;
		double expectedPrice = timeUnits*amountOfBikesRented*5*(1-0.3);
		List<RentalBike> rentalBikes = CompanyController.familyRental(company,"rentalByHour", 8, amountOfBikesRented);
		assertTrue(company.getBikes().size()==bikes-3);
		double price = 0;
		for(RentalBike rentalBike:rentalBikes){
			price+=rentalBike.getPrice();
		}
		assertTrue(price==expectedPrice);
	}
	
	@Test
	public void familyRentalNotEnoughStock() throws RentingException{		
		try{
			CompanyController.familyRental(company,"rentalByHour", 8, 26);
		} catch (RentingException e){
			assertTrue(e.getMessage().equals("There are not enough bikes to fill the requirement"));
		}
	}
		
	@Test
	public void familyRentalWrongNumber() throws RentingException{		
		try{
			CompanyController.familyRental(company,"rentalByHour", 8, 2);
		} catch (RentingException e){
			assertTrue(e.getMessage().equals("Family rental promo cannot be aplied to 2 bikes"));
		}
	}

}
