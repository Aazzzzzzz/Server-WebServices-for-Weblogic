package web.services;

import dal.HibernateUtil;
import dal.bl.SqlValidator;
import dal.dao.CarDao;
import dal.entity.Car;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import web.dto.InfoTotalDto;

import java.util.ArrayList;
import java.util.List;

public class InfoTotalService
{
    private static final Logger       log          = Logger.getLogger(InfoTotalService.class);
    private              CarDao       carDao       = new CarDao();
    ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();
    Session                  session;

    public List<InfoTotalDto> findInfoTotalDTO()
    {
        log.info("# Start 'InfoTotalService' => findAll() method.");
        try
        {
            List<InfoTotalDto> infoTotalDtoList = new ArrayList<InfoTotalDto>();
            List<Car> carList = carDao.findAll();
            for (Car car : carList)
            {
                InfoTotalDto infoDTO = new InfoTotalDto();
                infoDTO.setCarModel(car.getModel());
                infoDTO.setCarRegistrationNumber(car.getRegistrationNumber());
                infoDTO.setGarageSerialNumber(car.getGarage().getSerialNumber());
                infoDTO.setGarageAddress(car.getGarage().getAddress());
                infoDTO.setOwnerFirstName(car.getOwner().getFirstName());
                infoDTO.setOwnerLastName(car.getOwner().getLastName());
                infoDTO.setOwnerPhoneNumber(car.getOwner().getContactPhone());
                infoTotalDtoList.add(infoDTO);
            }
            log.debug(String.format("## Return: List<Garage> size=['%s']", infoTotalDtoList.size()));
            return infoTotalDtoList;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    public List<InfoTotalDto> filterInfoTotalDTO(String carRegistrationNumber, String ownerFirstName,
            String ownerLastName, String garageSerialNumber)
    {
        log.info("# Start 'InfoTotalService' => filterInfoTotalDTO() method.");
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<InfoTotalDto> searchResults = new ArrayList<InfoTotalDto>();
            log.info("Start query");
            String queryString = createQuery(carRegistrationNumber, ownerFirstName, ownerLastName, garageSerialNumber);
            log.debug("Query string : " + queryString);
            Query queryResults = session.createQuery(queryString);
            List<Object> results = queryResults.list();
            log.debug(String.format("Query List size = ['%s']", results.size()));
            session.close();
            log.info("finish query");
            for (Object list : results)
            {
                Object[] items = (Object[]) list;
                InfoTotalDto info = new InfoTotalDto();
                info.setGarageSerialNumber(items[0].toString());
                info.setGarageAddress(items[1].toString());
                info.setCarModel(items[2].toString());
                info.setCarRegistrationNumber(items[3].toString());
                info.setOwnerFirstName(items[4].toString());
                info.setOwnerLastName(items[5].toString());
                info.setOwnerPhoneNumber(items[6].toString());
                searchResults.add(info);
            }
            log.debug(String.format(
                    "carRegistrationNumber = ['%s'], ownerFirstName = ['%s'], ownerLastName = ['%s'], garageSerialNumber = ['%s']",
                    carRegistrationNumber, ownerFirstName, ownerLastName, garageSerialNumber));
            log.debug(String.format("## Return: List<InfoTotalDto> size=['%s']", searchResults.size()));
            return searchResults;
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

    public String createQuery(String carRegistrationNumber, String ownerFirstName, String ownerLastName,
            String garageSerialNumber)
    {
        String whereOrAnd = "WHERE ";
        String queryString =
                "SELECT garage.serialNumber, garage.address, car.model, car.registrationNumber, owner.firstName, owner.lastName, owner.contactPhone "
                        +
                        "FROM nhon_Car car " +
                        "JOIN nhon_Owner owner ON car.owner = owner.id " +
                        "JOIN nhon_Garage garage ON car.garage = garage.id ";
        if (!garageSerialNumber.equals("") & garageSerialNumber != null)
        {
            queryString += whereOrAnd + "lower(garage.serialNumber) LIKE lower('%" + SqlValidator.SafeString(garageSerialNumber) + "%') ";
            whereOrAnd = "AND ";
        }
        if (!ownerFirstName.equals("") & ownerFirstName != null)
        {
            queryString += whereOrAnd + "lower(owner.firstName) LIKE lower('%" + SqlValidator.SafeString(ownerFirstName) + "%') ";
            whereOrAnd = "AND ";
        }
        if (!ownerLastName.equals("") & ownerLastName != null)
        {
            queryString += whereOrAnd + "lower(owner.lastName) LIKE lower('%" + SqlValidator.SafeString(ownerLastName) + "%') ";
            whereOrAnd = "AND ";
        }
        if (!carRegistrationNumber.equals("") & carRegistrationNumber != null)
        {
            queryString += whereOrAnd + "lower(car.registrationNumber) LIKE lower('%" + SqlValidator.SafeString(carRegistrationNumber) + "%')";
        }
        return queryString;
    }

    //region Session
    protected Session getCurrentSession()
    {
        return session;
    }

    public void openSession()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession()
    {
        session.close();
    }

    public void beginTransaction()
    {
        transactionThreadLocal.set(getCurrentSession().beginTransaction());
    }

    public void commit()
    {
        if (transactionThreadLocal.get() != null)
        {
            transactionThreadLocal.get().commit();
        }
    }

    public void rollBack()
    {
        if (transactionThreadLocal.get() != null)
        {
            transactionThreadLocal.get().rollback();
        }
    }
    //endregion

}
