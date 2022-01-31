package web.services;

import dal.bl.SqlValidator;
import dal.dao.CarDao;

import dal.entity.Car;
import dal.entity.Garage;
import dal.entity.Owner;
import org.apache.log4j.Logger;
import web.dto.CarDto;
import web.dto.GarageDto;
import web.dto.OwnerDto;

import java.util.ArrayList;
import java.util.List;

public class CarService
{
    //region Fields
    private static final Logger       log          = Logger.getLogger(CarService.class);
    private              CarDao       carDao       = new CarDao();
    private              SqlValidator sqlValidator = new SqlValidator();

    //endregion

    //region Convert Methods EntityToDTO
    public static Car carDTOToCar(CarDto object)
    {
        log.debug("Start 'CarService' => carDtoToCar(CarDto obj) method.\n## Params: " + object.toString());
        try
        {
            Car item = new Car();
            item.setId(object.getId());
            item.setRegistrationNumber(object.getRegistrationNumber());
            item.setKilometrage(object.getKilometrage());
            item.setModel(object.getModel());
            item.setOwner((object.getOwnerId() != null) ?
                    new Owner(object.getOwnerId()) :
                    null);
            item.setGarage((object.getGarageId() != null) ?
                    new Garage(object.getGarageId()) :
                    null);
            log.debug(" Entity car: " + object.toString());
            return item;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public static CarDto carToCarDTO(Car object)
    {
        log.debug("# Start 'CarService' => carDtoToCar(Car obj) method.\n## Params: "
                + object.toString());
        try
        {
            CarDto carDTO = new CarDto();
            carDTO.setId(object.getId());
            carDTO.setRegistrationNumber(object.getRegistrationNumber());
            carDTO.setModel(object.getModel());
            carDTO.setKilometrage(object.getKilometrage());
            if (object.getOwner() != null)
            {
                OwnerDto ownerDTO = new OwnerDto();
                ownerDTO.setId(object.getOwner().getId());
                ownerDTO.setFirstName(object.getOwner().getFirstName());
                ownerDTO.setLastName(object.getOwner().getLastName());
                ownerDTO.setContactPhone(object.getOwner().getContactPhone());
                carDTO.setOwnerId(ownerDTO.getId());
            }
            if (object.getGarage() != null)
            {
                GarageDto garageDTO = new GarageDto();
                garageDTO.setId(object.getGarage().getId());
                garageDTO.setSerialNumber(object.getGarage().getSerialNumber());
                garageDTO.setMaxCars(object.getGarage().getMaxCars());
                garageDTO.setAddress(object.getGarage().getAddress());
                carDTO.setGarageId(garageDTO.getId());
            }
            return carDTO;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }
    //endregion

    //region Methods
    public void persist(CarDto object) throws Exception
    {
        try
        {
            log.info("# Start 'CarService' => persist(CarDto obj) method.");
            log.debug("## Params: " + object.toString());
            Car item = carDTOToCar(object);
            carDao.persist(item);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void update(CarDto object) throws Exception
    {
        log.info("# Start 'CarService' => update(CarDto obj) method.");
        log.debug("## Params: " + object.toString());
        try
        {
            Car item = carDTOToCar(object);
            carDao.update(item);
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }

    public CarDto findById(Long id)
    {
        log.info("# Start 'CarService' => findById(Long id) method.");
        log.debug("## Long id: " + id);
        try
        {
            CarDto carDTO = carToCarDTO(carDao.findById(id));
            log.debug("## FindById from DB and convert to CarDTO: " + carDTO.toString());
            return carDTO;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteById(Long id)
    {
        try
        {
            log.info("# Start 'CarService' => delete(Long id) method.");
            log.debug("## Long id: " + id);
            carDao.deleteById(id);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteAllCarsByOwnerId(Long ownerId)
    {
        log.info("# Start 'CarService' => deleteAllCarsByOwnerId(Long ownerId) method.");
        log.debug("## Long ownerId: " + ownerId);
        List<Car> cars = carDao.findAllCarsByOwnerId(ownerId);
        carDao.deleteCarsArray(cars);
    }

    public List<CarDto> findAll()
    {
        log.info("# Start 'CarService' => find() method.");
        List<CarDto> carsDTO = new ArrayList<CarDto>();
        try
        {
            List<Car> carsList = carDao.findAll();
            for (Car car : carsList)
            {
                carsDTO.add(carToCarDTO(car));
            }
            log.debug(String.format("## Return: List<Garage> size=['%s']",
                    carsDTO.size()));
            return carsDTO;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteAll()
    {
        log.info("# Start 'CarService' => delete() method.");
        carDao.deleteAll();
        log.debug("## Result: AllDeleted");
    }
    //endregion
}