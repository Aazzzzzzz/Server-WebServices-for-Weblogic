package dal.entity;


import javax.persistence.*;

@Entity(name = "nhon_Car")
public class Car {
    //region Fields
    @Id
    @SequenceGenerator(name = "nhonCarSeq", sequenceName="NHON_CAR_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nhonCarSeq")
    private Long id;
    private String model;
    private Integer kilometrage;
    private String registrationNumber;
    @ManyToOne(cascade = { CascadeType.ALL})
    private Owner owner;
    @ManyToOne(cascade = { CascadeType.ALL})
    private Garage garage;
    //endregion

    //region CTOR
    public Car()
    {

    }
    public Car(Long carId)
    {
        this.id = carId;
    }
    //endregion

    //region Properties
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getModel()
    {
        return model;
    }
    public void setModel(String model)
    {
        this.model = model;
    }
    public Integer getKilometrage()
    {
        return kilometrage;
    }
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }
    public void setKilometrage(Integer kilometrage)
    {
        this.kilometrage = kilometrage;
    }
    public Owner getOwner()
    {
        return owner;
    }
    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }
    public Garage getGarage()
    {
        return garage;
    }
    public void setGarage(Garage garage)
    {
        this.garage = garage;
    }
    //endregion

    //region Methods
    @Override
    public String toString()
    {
        String ownerId = "null";
        String garageId = "null";
        if(garage!=null)
        {
            garageId =garage.getId().toString();
        }
        if(owner!=null)
        {
            ownerId =owner.getId().toString();
        }
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", kilometrage=" + kilometrage +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", owner=" + ownerId +
                ", garage=" + garageId +
                '}';
    }
    //endregion
}
