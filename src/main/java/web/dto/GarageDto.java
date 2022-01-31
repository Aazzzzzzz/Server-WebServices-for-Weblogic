package web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GarageDto
{
    //region Fields
    private Long id;
    private String serialNumber;
    private String address;
    private Integer maxCars;
    private List<CarDto> cars = new ArrayList<CarDto>();
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
        return "GarageDTO{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", address='" + address + '\'' +
                ", maxCars=" + maxCars +
                ", cars=" + cars +
                '}';
    }
    //endregion
}