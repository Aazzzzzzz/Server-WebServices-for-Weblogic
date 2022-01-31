package dal.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "nhon_Owner")
public class Owner {

    //region Fields
    @Id
    @SequenceGenerator(name = "nhonOwnerSeq", sequenceName="NHON_OWNER_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nhonOwnerSeq")
    private Long id;
    private String firstName;
    private String lastName;
    private String contactPhone;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval=false, fetch = FetchType.EAGER)
    private List<Car> cars;
    //endregion

    //region CTOR
    public Owner()
    {
    }
    public Owner(Long ownerId)
    {
        this.id = ownerId;
    }
    //endregion

    //region Properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    //endregion

    //region Methods
    @Override
    public String toString()
    {
        String carsModels = "null";
        if(cars!=null){
            carsModels = "";
            for (Car car:cars) {
                carsModels = carsModels + car.getModel() + ", ";
            }
        }
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", Cars= [" + carsModels + "]" +
                '}';
    }
    //endregion
}
