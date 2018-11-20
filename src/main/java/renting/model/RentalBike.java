package renting.model;

import java.util.Date;

import renting.exception.RentingException;
import renting.service.RentalModeService;
import renting.utils.Utils;

public class RentalBike {

	private Bike bike;
	private Date rentalDate;	
	private Integer timeUnits;
	private String rentalPeriod;
	private double discount;
	private RentalModeService rentalModeService = new RentalModeService();
	private RentalMode rentalMode;
	
	public RentalBike(Bike bike, Date rentalDate, Integer timeUnits, String rentalPeriod) throws RentingException {
		super();
		this.bike = bike;
		this.rentalDate = rentalDate!=null?rentalDate:new Date();
		this.timeUnits = timeUnits;
		this.rentalMode = rentalModeService.getRentalMode(rentalPeriod);
		this.discount = 0;
	}
	public void setDiscount(double discount){
		this.discount = discount;
	}
	public Bike getBike() {
		return bike;
	}
	public void setBike(Bike bike) {
		this.bike = bike;
	}
	public Date getRentalDate() {
		return rentalDate;
	}
	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}
	public Integer getTimeUnits() {
		return timeUnits;
	}
	public void setTimeUnits(Integer timeUnits) {
		this.timeUnits = timeUnits;
	}
	public String getRentalPeriod() {
		return rentalPeriod;
	}
	public void setRentalPeriod(String rentalPeriod) {
		this.rentalPeriod = rentalPeriod;
	}
	public double getPrice() throws RentingException{
		Integer price = rentalMode.getPrice()*timeUnits;
		return price-price*discount;
	}
	public Date getReturnDate() throws RentingException {
		Integer timePeriodInHours = rentalMode.getRentalPeriodInHours()*timeUnits;
		return Utils.addHoursToDate(rentalDate,timePeriodInHours);
	}

	
}
