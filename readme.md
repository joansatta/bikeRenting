Design: 

This application emulates a spring based project. User interface (in this case, the testing file represents the user interface) interacts with the controller that calls the service to retrieve data from the model. Since this is a mock app, both rentBike and returnBike have overloads to receive Company objects. This way, the test file can evaluate the changes produced in the Company after the performed operations.

Scalability was the most important motivator for this design choice. It would be easy to add new companies (or branches), new rental modes or new Bikes if the model needed to scale. Bike object is empty, but ready to receive parameters like model, brand, etc.

Changes on a productive project

inputs.assets file wouldn't have hard paths to mock parameter files, it would have connection parameters to a database. Model data would be stored on said database and updated on renting and returning operations. Only renting.service package and input.assets file would have to be modified to implement this upgrade. Service classes would be @Autowired. 



Tests

Since this is a simple project, only one test file was created with the following use cases:

Calculate return time: calculates return date of a potentially rented bike given a rent mode and a time unit.

Calculate price: calculates price of a potentially rented bike given a rent mode and a time unit.

Rent bike

Rent and return bike

Rent and return bike late: simulates a late return of a rented bike. The method throws an exception

Rent and return bike with not enough money: simulates that a bike is returned and the money paid is less than the expected amount. The method throws an exception.

Family rental

Family rental not enough stock: simulates that the amount of bikes that need to be rented is bigger than the number of bikes available. The method throws an exception.

Family rental wrong number: simulates that the number of bikes that need to be rented is different from 3, 4 and 5. The method throws an exception.

