package web.services;

import dal.bl.SqlValidator;
import dal.dao.OwnerDao;
import dal.entity.*;
import dal.entity.Owner;
import org.apache.log4j.Logger;
import web.dto.*;
import web.dto.OwnerDto;

import java.util.ArrayList;
import java.util.List;

public class OwnerService
{
    //region Fields
    private static final Logger       log          = Logger.getLogger(OwnerService.class);
    private              OwnerDao     ownerDao     = new OwnerDao();
    private              SqlValidator sqlValidator = new SqlValidator();
    //endregion

    //region Convert Methods EntityToDTO
    private static Owner ownerDTOToOwner(OwnerDto object)
    {
        try
        {
            log.debug(
                    "# Start 'OwnerService' => ownerDTOToOwner(OwnerDto obj) method.\n## Params: " + object.toString());
            Owner item = new Owner();
            item.setId(object.getId());
            item.setContactPhone(object.getContactPhone());
            item.setFirstName(object.getFirstName());
            item.setLastName(object.getLastName());
            return item;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private static OwnerDto ownerToOwnerDTO(Owner object)
    {
        try
        {
            log.debug("# Start 'OwnerService' => ownerToOwnerDTO(Owner obj) method.\n## Params: " + object.toString());
            OwnerDto item = new OwnerDto();
            item.setId(object.getId());
            item.setContactPhone(object.getContactPhone());
            item.setFirstName(object.getFirstName());
            item.setLastName(object.getLastName());
            List<CarDto> cars = new ArrayList<CarDto>();
            for (Car car : object.getCars())
            {
                CarDto carDto = new CarDto();
                carDto.setId(car.getId());
                carDto.setModel(car.getModel());
                carDto.setRegistrationNumber(car.getRegistrationNumber());
                carDto.setKilometrage(car.getKilometrage());
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

    public void persist(OwnerDto object) throws Exception
    {
        log.debug("# Start 'OwnerService' => persist(OwnerDto obj) method.\n## Params: " + object.toString());
        try
        {
            Owner owner = ownerDTOToOwner(object);
            ownerDao.persist(owner);
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public OwnerDto findById(Long id)
    {
        log.info("# Start 'OwnerService' => findById(Long id) method.");
        log.debug("## Long id: " + id);
        try
        {
            ownerDao = new OwnerDao();
            Owner owner = ownerDao.findById(id);
            OwnerDto ownerDTO = ownerToOwnerDTO(owner);
            log.debug("## Returns: " + ownerDTO.toString());
            return ownerDTO;
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }

    public List<OwnerDto> findAll()
    {
        try
        {
            log.info("# Start 'OwnerService' => findAll() method.");
            List<OwnerDto> ownersDTO = new ArrayList<OwnerDto>();
            List<Owner> ownerList = ownerDao.findAll();
            for (Owner owner : ownerList)
            {
                ownersDTO.add(ownerToOwnerDTO(owner));
            }
            log.debug(String.format("## Return: List<Owner> size=['%s']", ownersDTO.size()));
            return ownersDTO;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void update(OwnerDto object) throws Exception
    {
        log.info("# Start 'OwnerService' => update(OwnerDto obj) method.");
        log.debug("## Update(OwnerDto obj).\n## Params: " + object.toString());
        try
        {
            Owner owner = ownerDTOToOwner(object);
            ownerDao.update(owner);
        }
        catch (Exception exception)
        {
            log.debug(exception.getMessage(), exception);
            throw exception;
        }
    }

    public void deleteById(Long id)
    {
        log.info("# Start 'OwnerService' => delete(Long id) method.");
        log.debug("## Params: " + id);
        ownerDao.deleteById(id);
    }

    public void deleteAll()
    {
        log.info("# Start 'OwnerService' => deleteAll() method.");
        ownerDao.deleteAll();
        log.debug("## Result: AllDeleted");
    }
}
