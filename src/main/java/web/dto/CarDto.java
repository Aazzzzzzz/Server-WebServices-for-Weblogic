package web.dto;

import java.io.Serializable;

public class CarDto
{
    //region Field
    private Long id;
    private String model;
    private Integer kilometrage;
    private String registrationNumber;
    private Long ownerId;
    private Long garageId;
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
    public void setKilometrage(Integer kilometrage)
    {
        this.kilometrage = kilometrage;
    }
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }
    public Long getOwnerId()
    {
        return ownerId;
    }
    public void setOwnerId(Long ownerId)
    {
        this.ownerId = ownerId;
    }
    public Long getGarageId()
    {
        return garageId;
    }
    public void setGarageId(Long garageId)
    {
        this.garageId = garageId;
    }
    //endregion

    //region Methods
    @Override
    public String toString()
    {
        return "CarDTO{" +
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