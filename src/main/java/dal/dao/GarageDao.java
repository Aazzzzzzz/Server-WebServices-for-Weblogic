package dal.dao;

import dal.HibernateUtil;
import dal.entity.Car;
import dal.entity.Garage;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GarageDao
{
    //region Fields
    private static final Logger log = Logger.getLogger(GarageDao.class);
    ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();
    Session session;
    //endregion

    //region CTOR
    public GarageDao()
    {
        openSession();
    }
    //endregion

    //region Methods
    public Garage findById(Long entityId)
    {
        log.info("#Start 'GarageDao' => findById(Long entityId) method");
        Garage entity = new Garage();
        try
        {
            if (!session.isOpen())
            {
                openSession();
                log.warn(String.format("@ Warning 'GarageDao' => session : Opened\n session = ['%s']", session.toString()));
            }
            entity =  (Garage) session.get(Garage.class, entityId);
            log.debug(String.format("# Garage entity: ['%s']", entity.getId()));

            closeSession();
            log.debug(String.format("# Garage entity: deleteById; session = ['%s']", session.isOpen()));
        }
        catch (Exception exception)
        {
            log.error(exception.getMessage(), exception);
        }
        return entity;
    }
    public List<Garage> findAll()
    {
        log.info("#Start 'GarageDao' => findAll() method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'GarageDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        log.debug(String.format("## session = ['%s']", session.toString()));
        List<Garage> garages = session.createQuery("from " + Garage.class.getName()).list();

        closeSession();
        log.debug(String.format("## List<Garage> entity: OK; session = ['%s']; list size = ['%s']; ", session.isOpen(), garages.size()));
        return  garages;
    }
    public Garage persist(Garage entity)
    {
        log.info("#Start 'GarageDao' => persist(Garage entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'GarageDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## save(Garage entity) => entity: ['%s']", entity.toString()));
        session.save(entity);
        commit();

        closeSession();
        log.debug(String.format("# Garage entity: Saved; session = ['%s']", session.isOpen()));
        return entity;
    }
    public Garage update(Garage entity)
    {
        Garage updateGarage = findById(entity.getId());
        updateGarage.setMaxCars(entity.getMaxCars());
        updateGarage.setAddress(entity.getAddress());
        updateGarage.setSerialNumber(entity.getSerialNumber());
        log.info("#Start 'GarageDao' => update(Garage entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'GarageDao' => session : Opened\n session = ['%s']", session.toString()));
        }

        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## update(Garage entity) => entity: ['%s']", updateGarage.toString()));
        session.update(updateGarage);
        commit();
        closeSession();
        log.debug(String.format("# Garage entity: Update; session = ['%s']", session.isOpen()));
        return updateGarage;
    }
    public void update(List<Garage> entityList)
    {
        log.info(String.format("#Start 'GarageDao' => update(List<Garage> entityList) method. List size = ['%s']", entityList.size()));
        for (Garage entity : entityList)
        {
            update(entity);
        }
    }
    public void delete(Garage entity)
    {
        log.info("#Start 'GarageDao' => delete(Garage entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'GarageDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## delete(Garage entity) => entity: ['%s']", entity.toString()));

        if (entity.getCars() != null)
        {
            for (Car car : entity.getCars())
            {
                car.setGarage(null);
                session.update(car);
            }
        }
        entity.getCars().clear();
        session.delete(entity);

        commit();
        closeSession();
        log.debug(String.format("# Garage entity: Deleted; session = ['%s']", session.isOpen()));
    }
    public void deleteById(Long entityId)
    {
        log.info("#Start 'GarageDao' => deleteById(Long entityId) method");
        Garage entity = findById(entityId);
        log.debug(String.format("## Find Garage entity: ['%s']", entity.toString()));
        delete(entity);
        log.debug(String.format("# Garage entity: deleteById; session = ['%s']", session.isOpen()));
    }
    public void deleteAll()
    {
        log.info("#Start 'GarageDao' => deleteAll() method");
        List<Garage> entityList = findAll();
        for (Garage entity : entityList)
        {
            delete(entity);
        }
        log.debug("# All Garage entity: Deleted");
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
        if(transactionThreadLocal.get() != null){
            transactionThreadLocal.get().commit();
        }
    }
    public void rollBack()
    {
        if(transactionThreadLocal.get() != null){
            transactionThreadLocal.get().rollback();
        }
    }
    //endregion
}