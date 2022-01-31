package dal;

import dal.bl.HelpGenerator;
import dal.dao.CarDao;
import dal.dao.GarageDao;
import dal.dao.OwnerDao;
import dal.entity.Car;
import dal.entity.Garage;
import dal.entity.Owner;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InitializeDB
{
    //region Fields
    private static final Logger log = Logger.getLogger(InitializeDB.class);
    private OwnerDao ownerDao = new OwnerDao();
    private CarDao carDao = new CarDao();
    private GarageDao garageDao = new GarageDao();
    //endregion

    //region Methods
    public void Init()
    {
        log.info("# Start 'InitializeDB' => Init method");
        try
        {
            GarageDao garageDao = new GarageDao();
            CarDao carDao = new CarDao();
            OwnerDao ownerDao = new OwnerDao();

            List<Garage> garages = garageDao.findAll();
            List<Car> cars = carDao.findAll();
            List<Owner> owners = ownerDao.findAll();

            if (garages.size() <= 0 & cars.size() <= 0 & owners.size() <= 0)
            {
                FirstSeed();
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
    }
    public void FirstSeed()
    {
        log.info("#  Start 'InitializeDB' => FirstSeed method");
        try
        {
            HelpGenerator generator = new HelpGenerator();
            ArrayList<Car> listCars = new ArrayList<Car>();
            ArrayList<Garage> listGarages = new ArrayList<Garage>();
            // Create new garages
            Integer garageCount = 30;
            for (Integer i = 0; i < garageCount; i++)
            {
                Garage newGarage = new Garage();
                newGarage.setSerialNumber(generator.garageSerialNumber());
                newGarage.setAddress(generator.garageAddress());
                newGarage.setMaxCars(generator.garageMaxCars());
                List<Car> cars = new ArrayList<Car>();
                newGarage.setCars(cars);
                garageDao.persist(newGarage);
                listGarages.add(newGarage);
            }
            // Create new Owners and Cars
            Integer owmerCount = 50;
            for (Integer i = 0; i < owmerCount; i++)
            {
                Owner newOwner = new Owner();
                newOwner.setFirstName(generator.ownerFirstName());
                newOwner.setLastName(generator.ownerLastName());
                newOwner.setContactPhone(generator.ownerPhone());
                List<Car> cars = new ArrayList<Car>();

                ownerDao.persist(newOwner);

                Integer amount = generator.ownerAmountCars();
                // Create Cars by owner
                for (Integer k = 0; k < amount; k++)
                {
                    Car newCar = new Car();
                    newCar.setModel(generator.carModel());
                    newCar.setRegistrationNumber(generator.carRegistrationNumber());
                    newCar.setKilometrage(generator.carKilometrage());
                    newCar.setOwner(newOwner);
                    cars.add(newCar);
                    carDao.persist(newCar);
                    listCars.add(newCar);
                }
            }

            // Join Garage to Car
            for (Integer garageIndex= 0, carIndex = 0; garageIndex < listGarages.size(); )
            {
                if (carIndex < listCars.size())
                {
                    Garage garage = listGarages.get(garageIndex);
                    if (garage.getMaxCars() > garage.getCars().size())
                    {
                        Car car = listCars.get(carIndex);
                        garage.setCar(car);
                        car.setGarage(garage);
                        carIndex++;
                        garageDao.update(garage);
                        carDao.update(car);
                    }
                    else
                        garageIndex++;
                }
                else
                {
                    garageIndex += listGarages.size()+1;
                }
            }
            Integer maxCarsInALLGarage = 0;
            for (Integer i = 0; i < listGarages.size(); i++)
            {
                maxCarsInALLGarage += listGarages.get(i).getMaxCars();
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
    }
    //endregion
}
