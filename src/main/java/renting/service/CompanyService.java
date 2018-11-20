package renting.service;

import java.io.IOException;

import renting.exception.RentingException;
import renting.model.Company;
import renting.utils.ProjectPropertiesSingleton;

public class CompanyService {
	
	private static ProjectPropertiesSingleton properties;
	
	public CompanyService() {
		try {
			properties = ProjectPropertiesSingleton.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Company getCompany(String name) throws RentingException {
		try {
			String line = GenericService.getLine(properties.getInput("pathToCompanyModel"), name);
			Integer totalAvailableBikes = Integer.valueOf(GenericService.parseLine(line, "totalAvailableBikes=",";"));
			String[] amountsOfBikesInPromo = GenericService.parseLine(line,"amountsOfBikesInPromo=",";").split(",");
			double discount = Double.valueOf(GenericService.parseLine(line, "discount=", ","));
			Company company = new Company();
			company.setBikes(totalAvailableBikes);
			company.setAmountsOfBikesInPromo(amountsOfBikesInPromo);
			company.setDiscount(discount);
			return company;
		} catch (Exception e){
			throw new RentingException("Coult not instance or retrieve Company Singleton "+e.getMessage());
		}
	}
}
