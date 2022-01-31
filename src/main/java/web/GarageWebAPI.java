package web;

import org.apache.log4j.Logger;
import web.dto.CarDto;
import web.dto.GarageDto;
import web.dto.InfoTotalDto;
import web.dto.OwnerDto;
import web.services.CarService;
import web.services.GarageService;
import web.services.InfoTotalService;
import web.services.OwnerService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class GarageWebAPI
{

    //region CTOR
    public GarageWebAPI()
    {
    }
    //endregion

    //region Fields
    private static final Logger log = Logger.getLogger(GarageWebAPI.class);
    private final CarService carService = new CarService();
    private final GarageService garageService = new GarageService();
    private final OwnerService ownerService = new OwnerService();
    //endregion

    //region Garage
    @WebMethod
    public String persistGarage(GarageDto object)
    {
        try
        {
            log.info("# Start 'GarageWebAPI' => persistGarage(GarageDto object) method.");
            log.debug(String.format("## GarageDto object: ['%s']", object.toString()));
            garageService.persist(object);
        }
        catch (Exception exception){
            log.debug(exception.getMessage(), exception);
            return "Server problem";
        }
        return String.format("## Result insert: OK; Data:[ '%s' ]", object.toString());
    }
    @WebMethod
    public GarageDto findGarageById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => findGarageById(Long id) method.");
        log.debug(String.format("## Long id: [ '%s' ]", id));
        return garageService.findById(id);
    }
    @WebMethod
    public List<GarageDto> findAllGarages()
    {
        log.info("# Start 'GarageWebAPI' => findAllGarages() method.");
        List<GarageDto> garagesDto = garageService.findAll();
        log.debug(String.format("## Return: List<GarageDto> size=[ '%s' ]\nGarages:\n", garagesDto.size()));
        return garagesDto;
    }
    @WebMethod
    public String updateGarage(GarageDto object)
    {
        try
        {
            log.info("# Start 'GarageWebAPI' => updateGarage(GarageDto object) method.");
            log.debug(String.format("## GarageDto object: [ '%s' ]", object.toString()));
            garageService.update(object);
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            return "Server problem";
        }
        return String.format("## Result insert: OK; GarageDto object:[ '%s' ]", object.toString());
    }
    @WebMethod
    public void deleteGarageById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => deleteGarage(Long id) method.");
        log.debug(String.format("## Long id: [ '%s' ]", id));
        garageService.deleteById(id);
    }
    @WebMethod
    public void deleteAllGarages()
    {
        log.info("# Start 'GarageWebAPI' => deleteAllGarages() method.");
        garageService.deleteAll();
    }
    @WebMethod
    public List<GarageDto> garagesDetailsInfo()
    {
        log.info("# Start 'GarageWebAPI' => garagesDetailsInfo() method.");
        return garageService.detailsInfo();
    }
    //endregion

    //region Car
    @WebMethod
    public String persistCar(CarDto object)
    {
        try
        {
            log.info("# Start 'GarageWebAPI' => persistCar(CarDto entity) method.");
            log.debug(String.format("## CarDto object: [ '%s' ]" , object.toString()));
            carService.persist(object);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
            return "Server problem";
        }
        return String.format("## Result insert: OK; Data:[ '%s' ]", object.toString());
    }
    @WebMethod
    public CarDto findCarById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => findCarById(Long id) method.");
        log.debug(String.format("## Long id: [ '%s' ]", id));
        return carService.findById(id);
    }
    @WebMethod
    public List<CarDto> findAllCars()
    {
        log.info("# Start 'GarageWebAPI' => findAllCars() method.");
        List<CarDto> carsDto = carService.findAll();
        log.debug(String.format("## Return: List<GarageDto> size=[ '%s' ]\nGarages:\n", carsDto.size()));
        return carsDto;
    }
    @WebMethod
    public String updateCar(CarDto entity)
    {
        try
        {
            log.info("# Start 'GarageWebAPI' => updateCar(CarDto entity) method.");
            log.debug(String.format("## CarDto entity: [ '%s' ]", entity.toString()));
            carService.update(entity);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            return "Server problem";
        }
        return "Update: OK";
    }
    @WebMethod
    public void deleteCarById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => deleteCar(Long id) method.");
        log.debug(String.format("## Long id: [ '%s' ]", id));
        carService.deleteById(id);
    }
    @WebMethod
    public void deleteAllCars()
    {
        log.info("# Start 'GarageWebAPI' => deleteAllCars() method.");
        carService.deleteAll();
    }
    //endregion

    //region Owner
    @WebMethod
    public String persistOwner(OwnerDto object)
    {
        log.info("# Start 'GarageWebAPI' => persistOwner(OwnerDto object) method.");
        log.debug(String.format("## OwnerDto object: ['%s']", object.toString()));
        try
        {
            ownerService.persist(object);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            if (exception.getMessage().contains("Validation problem"))
                return exception.getMessage();
            return "Server problem";
        }
        return String.format("## Result insert: OK; Data:[ '%s' ]", object.toString());

    }
    @WebMethod
    public OwnerDto findOwnerById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => findOwnerById(Long id) method.");
        log.debug(String.format("## Long id: [ '%s' ]", id));
        return ownerService.findById(id);
    }
    @WebMethod
    public List<OwnerDto> findAllOwners()
    {
        log.info("# Start 'GarageWebAPI' => findAllOwners() method.");
        List<OwnerDto> ownersDto = ownerService.findAll();
        log.debug(String.format("## Return: List<OwnerDto> size=[ '%s' ]\nGarages:\n", ownersDto.size()));
        return ownersDto;
    }
    @WebMethod
    public String updateOwner(OwnerDto object)
    {
        try
        {
            log.info("# Start 'GarageWebAPI' => updateOwner(OwnerDto object) method.");
            log.debug(String.format("## OwnerDto object: [ '%s' ]", object.toString()));
            ownerService.update(object);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
            return "Server problem";
        }
        return String.format("## Result insert: OK; OwnerDto object:[ '%s' ]", object.toString());
    }
    @WebMethod
    public void deleteOwnerById(Long id)
    {
        log.info("# Start 'GarageWebAPI' => deleteOwner(Long id) method.");
        log.debug("## Long id: [ '%s' ]" + id);
        ownerService.deleteById(id);
    }
    @WebMethod
    public void deleteAllOwners()
    {
        log.info("# Start 'GarageWebAPI' => deleteAll() method.");
        ownerService.deleteAll();
    }
    //endregion

    //region InfoTotal
    @WebMethod
    public List<InfoTotalDto> getInfoTotal()
    {
        log.info("# Start 'GarageWebAPI' => getInfoTotal() method.");
        InfoTotalService info = new InfoTotalService();
        List<InfoTotalDto> infoList = info.findInfoTotalDTO();
        log.debug(String.format("## Return: List<InfoTotalDto> size=[ '%s' ]\nGarages:\n", infoList.size()));
        return infoList;
    }
    @WebMethod
    public List<InfoTotalDto> getFilterInfoTotal(String carRegistrationNumber, String ownerFirstName, String ownerLastName, String garageSerialNumber)
    {
        log.info("# Start 'GarageWebAPI' => getFilterInfoTotal(String carRegistrationNumber, String ownerFirstName, String ownerLastName, String garageSerialNumber) method.");
        log.debug(String.format("carRegistrationNumber = ['%s'], ownerFirstName = ['%s'], ownerLastName = ['%s'], garageSerialNumber = ['%s']", carRegistrationNumber, ownerFirstName, ownerLastName, garageSerialNumber));
        InfoTotalService info = new InfoTotalService();
        List<InfoTotalDto> infoList = info.filterInfoTotalDTO(carRegistrationNumber, ownerFirstName, ownerLastName, garageSerialNumber);
        log.debug(String.format("## Return: List<InfoTotalDto> size=[ '%s' ]\nGarages:\n", infoList.size()));
        return infoList;
    }
    //endregion
}