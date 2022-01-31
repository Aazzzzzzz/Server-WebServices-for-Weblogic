package web.services;

import dal.bl.SqlValidator;
import dal.dao.GarageDao;
import dal.entity.Car;
import dal.entity.Garage;
import org.apache.log4j.Logger;
import web.dto.CarDto;
import web.dto.GarageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GarageService
{
    //region Fields
    private static final Logger       log          = Logger.getLogger(GarageService.class);
    private              GarageDao    garageDao    = new GarageDao();
    private              SqlValidator sqlValidator = new SqlValidator();
    //endregion

    //region Convert Methods EntityToDTO
    private static Garage garageDTOToGarage(GarageDto object)
    {
        log.debug("# Start 'GarageService' => garageDTOToGarage(GarageDto obj) method.\n## Params: "
                + object.toString());
        try
        {
            Garage item = new Garage();
            item.setId(object.getId());
            item.setSerialNumber(object.getSerialNumber());
            item.setAddress(object.getAddress());
            item.setMaxCars(object.getMaxCars());
            return item;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private static GarageDto garageToGarageDTO(Garage object)
    {
        log.info("# Start 'GarageService' => garageToGarageDTO(Garage obj) method.");
        log.debug("## Params: " + object.toString());
        try
        {
            GarageDto item = new GarageDto();
            item.setId(object.getId());
            item.setSerialNumber(object.getSerialNumber());
            item.setAddress(object.getAddress());
            item.setMaxCars(object.getMaxCars());
            List<CarDto> cars = new ArrayList<CarDto>();
            for (Car car : object.getCars())
            {
                CarDto carDto = new CarDto();
                carDto.setId(car.getId());
                carDto.setModel(car.getModel());
                carDto.setRegistrationNumber(car.getRegistrationNumber());
                carDto.setKilometrage(car.getKilometrage());
                carDto.setGarageId(car.getGarage().getId());
                carDto.setOwnerId(car.getOwner().getId());
                cars.add(carDto);
            }
            item.setCars(cars);
            return item;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }
    //endregion

    public void persist(GarageDto object) throws Exception
    {
        log.debug("# Start 'GarageService' => persist(GarageDto obj) method.\n## Params: "
                + object.toString());
        try
        {
            Garage garage = garageDTOToGarage(object);
            garageDao.persist(garage);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void update(GarageDto object) throws Exception
    {
        log.info("# Start 'GarageService' => update(GarageDto obj) method.");
        log.debug("## Update(GarageDto obj).\n## Params: " + object.toString());
        try
        {
            Garage garage = garageDTOToGarage(object);
            garageDao.update(garage);
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }

    public GarageDto findById(Long id)
    {
        log.info("# Start 'GarageService' => findById(Long id) method.");
        log.debug("## Long id: " + id);
        try
        {
            garageDao = new GarageDao();
            Garage garage = garageDao.findById(id);
            GarageDto garageDTO = garageToGarageDTO(garage);
            log.debug("## Returns: " + garageDTO.toString());
            return garageDTO;
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteById(Long id)
    {
        try
        {
            log.info("# Start 'GarageService' => delete(Long id) method.");
            log.debug("## Params: " + id);
            garageDao.deleteById(id);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public List<GarageDto> findAll()
    {
        log.info("# Start 'GarageService' => findAll() method.");
        try
        {
            List<GarageDto> garagesDTO = new ArrayList<GarageDto>();
            List<Garage> garageList = garageDao.findAll();
            for (Garage garage : garageList)
            {
                garagesDTO.add(garageToGarageDTO(garage));
            }
            log.debug(String.format("## Return: List<Garage> size=['%s']",
                    garagesDTO.size()));
            return garagesDTO;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteAll()
    {
        log.info("# Start 'GarageService' => deleteAll() method.");
        garageDao.deleteAll();
        log.debug("## Result: AllDeleted");
    }

    public List<GarageDto> detailsInfo()
    {
        log.info("# Start 'GarageService' => detailsInfo() method.");
        try
        {
            List<GarageDto> garagesDTOList = new ArrayList<GarageDto>();
            List<Garage> garageList = garageDao.findAll();
            for (Garage garage : garageList)
            {
                GarageDto garageDTO = garageToGarageDTO(garage);
                garagesDTOList.add(garageDTO);
            }
            log.debug("## Returns: [" + garageList.stream().map(Garage::toString)
                    .collect(Collectors.joining("] \n")));
            return garagesDTOList;
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }
}