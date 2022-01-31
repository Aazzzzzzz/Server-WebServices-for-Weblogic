package dal.dao;

import dal.HibernateUtil;
import dal.entity.Owner;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class OwnerDao
{
    //region Fields
    private static final Logger log = Logger.getLogger(OwnerDao.class);
    ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();
    Session session;
    //endregion

    //region CTOR
    public OwnerDao()
    {
        openSession();
    }
    //endregion

    //region Methods
    public Owner findById(Long entityId)
    {
        log.info("#Start 'OwnerDao' => findById(Long entityId) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'OwnerDao' => session : Opened\n session = ['%s']", session));
        }
        Owner entity = (Owner) session.get(Owner.class, entityId);
        
        closeSession();
        log.debug(String.format("# Owner entity: deleteById; session = ['%s']", session.isOpen()));
        return entity;
    }
    public List<Owner> findAll()
    {
        log.info("#Start 'OwnerDao' => findAll() method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'OwnerDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        log.debug(String.format("## session = ['%s']", session.toString()));
        List<Owner> owners = session.createQuery("from " + Owner.class.getName()).list();
        closeSession();
        log.debug(String.format("## List<Owner> entity: OK; session = ['%s']; list size = ['%s']; ", session.isOpen(), owners.size()));
        return owners;
    }
    public Owner persist(Owner entity)
    {
        log.info("#Start 'OwnerDao' => persist(Owner entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'OwnerDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## save(Owner entity) => entity: ['%s']", entity.toString()));
        session.save(entity);
        commit();

        closeSession();
        log.debug(String.format("# Owner entity: Saved; session = '%s'", session.isOpen()));
        return entity;
    }
    public Owner update(Owner entity)
    {
        log.info("#Start 'OwnerDao' => update(Owner entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'OwnerDao' => session : Opened\n session = ['%s']", session));
        }

        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## update(Owner entity) => entity: ['%s']", entity.toString()));
        Owner updateOwner = (Owner) session.merge(entity);
        commit();
        
        closeSession();
        log.debug(String.format("# Owner entity: Update; session = '%s'", session.isOpen()));
        return updateOwner;
    }
    public void update(List<Owner> entityList)
    {
        log.info(String.format("#Start 'OwnerDao' => update(List<Owner> entityList) method. List size = ['%s']", entityList.size()));
        for (Owner entity : entityList)
        {
            update(entity);
        }
    }
    public void delete(Owner entity)
    {
        log.info("#Start 'OwnerDao' => remove(Owner entity) method");
        if (!session.isOpen())
        {
            openSession();
            log.warn(String.format("@ Warning 'OwnerDao' => session : Opened\n session = ['%s']", session.toString()));
        }
        beginTransaction();
        log.debug(String.format("## beginTransaction() => transaction = ['%s'], session = ['%s']", session.getTransaction().isActive(), session.toString()));
        log.debug(String.format("## update(Owner entity) => entity: ['%s']", entity.toString()));
        session.delete(entity);
        commit();

        closeSession();
        log.debug(String.format("# Owner entity: Deleted; session = ['%s']", session.isOpen()));
    }
    public void deleteById(Long entityId)
    {
        log.info("#Start 'OwnerDao' => deleteById(Long entityId) method");
        Owner entity = findById(entityId);
        delete(entity);
        log.debug(String.format("# Owner entity: deleteById; session = ['%s']", session.isOpen()));
    }
    public void deleteAll()
    {
        log.info("#Start 'OwnerDao' => deleteAll() method");
        List<Owner> entityList = findAll();
        for (Owner entity : entityList)
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
        if(transactionThreadLocal.get() != null)
        {
            transactionThreadLocal.get().commit();
        }
    }



    public void rollBack()
    {
        if(transactionThreadLocal.get() != null)
        {
            transactionThreadLocal.get().rollback();
        }
    }
    //endregion
}