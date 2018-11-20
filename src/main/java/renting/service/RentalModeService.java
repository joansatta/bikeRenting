package renting.service;

import java.io.IOException;

import renting.exception.RentingException;
import renting.model.RentalMode;
import renting.utils.ProjectPropertiesSingleton;

public class RentalModeService {

	private static ProjectPropertiesSingleton properties;

	public RentalModeService() {
		try {
			properties = ProjectPropertiesSingleton.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RentalMode getRentalMode(String rentalPeriod) throws RentingException {
		try {
			RentalMode rentalMode = new RentalMode();
			String line = GenericService.getLine(properties.getInput("pathToModel"),rentalPeriod);
			Integer price = Integer.valueOf(GenericService.parseLine(line,"priceByTimeUnit=",","));
			Integer rentalPeriodInHours = Integer.valueOf(GenericService.parseLine(line,"rentalPeriodInHours=",","));
			rentalMode.setPrice(price);
			rentalMode.setRentalPeriodInHours(rentalPeriodInHours);
			return rentalMode;
		} catch (Exception e) {
			throw new RentingException("Coult not instance or retrieve Company Singleton "+e.getMessage());
		}

	}

}
