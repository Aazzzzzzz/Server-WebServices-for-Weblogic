package web.dto;

import java.io.Serializable;
import java.util.List;

public class OwnerDto
{
    //region Fields
    private Long id;
    private String firstName;
    private String LastName;
    private String contactPhone;
    private List<CarDto> cars;
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
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return LastName;
    }
    public void setLastName(String lastName)
    {
        LastName = lastName;
    }
    public String getContactPhone()
    {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    public List<CarDto> getCars()
    {
        return cars;
    }
    public void setCars(List<CarDto> cars)
    {
        this.cars = cars;
    }
    //endregion

    //region Methods
    @Override
    public String toString()
    {
        return "OwnerDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", cars=" + cars +
                '}';
    }
    //endregion
}
