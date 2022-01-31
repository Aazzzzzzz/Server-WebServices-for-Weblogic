package dal.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "nhon_Garage")
public class Garage
{
    //region Fields
    @Id
    @SequenceGenerator(name = "nhonGarageSeq", sequenceName = "NHON_GARAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nhonGarageSeq")
    private Long id;
    private String serialNumber;
    private String address;
    @Column(nullable = false)
    private Integer maxCars;
    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Car> cars = new ArrayList<Car>();
    //endregion

    //region CTOR
    public Garage()
    {
    }

    public Garage(Long garageId)
    {
        this.id = garageId;
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

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Integer getMaxCars()
    {
        return maxCars;
    }

    public void setMaxCars(Integer maxCars)
    {
        this.maxCars = maxCars;
    }

    public List<Car> getCars()
    {
        return cars;
    }

    public void setCar(Car car)
    {
        if (this.cars.size() < this.maxCars)
            this.cars.add(car);
    }

    public void setCars(List<Car> cars)
    {
        this.cars = cars;
    }
    //endregion

    //region Methods
    @Override
    public String toString()
    {
        String carsModels = "null";
        if (cars != null)
        {
            carsModels = "";
            for (Car car : cars)
            {
                carsModels = carsModels + car.getModel() + ", ";
            }
        }
        return "Garage{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", address='" + address + '\'' +
                ", maxCars=" + maxCars +
                ",  Cars= [" + carsModels + "]" +
                '}';
    }
    //endregion
}
