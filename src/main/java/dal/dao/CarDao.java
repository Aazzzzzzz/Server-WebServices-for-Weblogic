package dal.dao;

import dal.HibernateUtil;
import dal.entity.Car;
import dal.entity.Garage;
import dal.entity.Owner;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CarDao
{

    //region Fields
    private static final Logger    log       = Logger.getLogger(CarDao.class);
    private              OwnerDao  ownerDao  = new OwnerDao();
    private              GarageDao garageDao = new GarageDao();
    ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();
    Session                  session;
    //endregion

    //region CTOR
    public CarDao()
    {
        openSession();
    }
    //endregion

    //region Methods
    public Car findById(Long id)
    {
        log.info("#Start 'CarDao' => findById(Long entityId) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        Car car = (Car) session.get(Car.class, id);

        closeSession();
        log.debug(String.format("# Car entity: deleteById; session = ['%s']", session.isOpen()));
        return car;
    }

    public List<Car> findAllCarsByOwnerId(Long ownerId)
    {
        log.info("#Start 'CarDao' => findById(Long entityId) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        List<Car> listAllOwnerCars = session.createQuery("from " + Car.class.getName() + " where owner_id = " + ownerId)
                .list();

        closeSession();
        log.debug(String.format("# Car entity: deleteById; session = ['%s']", session.isOpen()));
        return listAllOwnerCars;
    }

    public List<Car> findAll()
    {
        log.info("#Start 'CarDao' => findAll() method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        log.debug(String.format("## session = ['%s']", session.toString()));
        List<Car> cars = getCurrentSession().createQuery("from " + Car.class.getName()).list();
        closeSession();
        log.debug(String.format("## List<Car> entity: OK; session = ['%s']; list size = ['%s']; ", session.isOpen(),
                cars.size()));
        return cars;
    }

    public Car persist(Car entity)
    {
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        try
        {
            beginTransaction();
            if (entity.getOwner() != null)
                entity.setOwner(ownerDao.findById(entity.getOwner().getId()));

            if (entity.getGarage() != null)
                entity.setGarage(garageDao.findById(entity.getGarage().getId()));

            log.info("#Start 'CarDao' => persist(Car entity) method");
            log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']",
                    session.getTransaction().isActive(), session.toString()));
            session.save(entity);
            commit();
        }
        catch (Exception exception)
        {
            rollBack();
            log.error(exception.getMessage(), exception);
        }

        closeSession();
        log.debug(String.format("# Car entity: Saved; session = ['%s']", session.isOpen()));
        return entity;
    }

    public Car update(Car entity) throws Exception
    {
        Car currentCar = null;
        Garage curentGarage = null;
        Owner currentOwner = null;
        try
        {
            if (entity != null)
            {
                currentCar = findById(entity.getId());
                curentGarage = garageDao.findById(entity.getGarage().getId());
                currentOwner = ownerDao.findById(entity.getOwner().getId());
                currentOwner.setCars(null);
            }
            if (!session.isOpen())
            {
                openSession();
                log.warn(
                        String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
            }
            if (currentCar.getOwner() == null | currentCar.getGarage() == null)
            {
                beginTransaction();
                if (currentCar.getGarage() == null)
                {
                    if (curentGarage.getCars() != null)
                        if (curentGarage.getCars().size() < curentGarage.getMaxCars())
                        {
                            curentGarage.setCars(null);
                            currentCar.setGarage(curentGarage);
                        }
                        else
                        {
                            throw new Exception("Update problem, garage is full");
                        }
                }
                if (entity.getOwner() == null)
                {
                    currentCar.setOwner(currentOwner);
                }
                entity = (Car) session.merge(currentCar);
                commit();
                log.debug(String.format("# Car entity: Updated; session = ['%s'], update(Car entity) => entity: ['%s']",
                        session.isOpen(), entity.toString()));
            }
            else if (entity.getGarage().getId() != currentCar.getGarage().getId()
                    | entity.getOwner().getId() != currentCar.getGarage().getId())
            {
                beginTransaction();

                if (entity.getGarage().getId() != currentCar.getGarage().getId())
                {
                    if (curentGarage.getCars() != null)
                        if (curentGarage.getCars().size() < curentGarage.getMaxCars())
                        {
                            curentGarage.setCars(null);
                            currentCar.setGarage(curentGarage);
                        }
                        else
                        {
                            throw new Exception("Update problem, garage is full");
                        }
                }
                if (entity.getOwner().getId() != currentCar.getOwner().getId())
                {
                    currentCar.setOwner(currentOwner);
                }
                entity = (Car) session.merge(currentCar);
                commit();
                log.debug(String.format("# Car entity: Updated; session = ['%s'], update(Car entity) => entity: ['%s']",
                        session.isOpen(), entity.toString()));
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
            throw new Exception("Update problem");
        }
        closeSession();
        return entity;
    }

    public void update(List<Car> entityList) throws Exception
    {
        log.info(String.format("#Start 'CarDao' => update(List<Car> entityList) method. List size = ['%s']",
                entityList.size()));
        for (Car entity : entityList)
        {
            update(entity);
        }
    }

    public void remove(Car entity)
    {
        log.info("#Start 'CarDao' => remove(Car entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }

        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']",
                session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## update(Car entity) => entity: ['%s']", entity.toString()));
        session.remove(entity);

        commit();
        closeSession();
        log.debug(String.format("# Car entity: Update; session = ['%s']", session.isOpen()));
    }

    public void delete(Car entity)
    {
        log.info("#Start 'CarDao' => remove(Car entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'CarDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']",
                session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## update(Car entity) => entity: ['%s']", entity.toString()));

        if (entity.getOwner() != null)
        {
            entity.getOwner().getCars().remove(entity);
        }
        if (entity.getGarage() != null)
        {
            entity.getGarage().getCars().remove(entity);
        }
        entity.setOwner(null);
        entity.setGarage(null);
        session.delete(entity);
        commit();
        closeSession();
    }

    public void deleteById(Long entityId)
    {
        log.info("#Start 'CarDao' => deleteById(Long entityId) method");
        Car entity = findById(entityId);
        log.debug(String.format("# Car entity: ['%s']", entity.toString()));
        delete(entity);
        log.debug(String.format("# Car entity: deleteById; session = ['%s']", session.isOpen()));

    }

    public void deleteCarsArray(List<Car> cars)
    {
        log.info("#Start 'CarDao' => deleteCarsArray() method");
        for (Car car : cars)
        {
            remove(car);
        }
        log.debug("# List<Car> entity: Deleted");

    }

    public void deleteAll()
    {
        log.info("#Start 'CarDao' => deleteAll() method");
        List<Car> entityList = findAll();
        for (Car entity : entityList)
        {
            delete(entity);
        }
        log.debug("# All Car entity: Deleted");
    }
    //endregion

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
            transactionThreadLocal.get().commit();
    }

    public void rollBack()
    {
        if (transactionThreadLocal.get() != null)
            transactionThreadLocal.get().rollback();
    }
    //endregion
}