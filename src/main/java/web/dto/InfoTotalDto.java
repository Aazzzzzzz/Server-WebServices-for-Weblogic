package web.dto;

//@SqlResultSetMapping(
//        name = "TestQueryResultMapping"
//        , entities = @EntityResult(
//        entityClass = InfoTotalDto.class
//        , fields = {
//        @FieldResult(name = "garageSerialNumber", column = "serialNumber")
//        , @FieldResult(name = "garageAddress", column = "address")
//        , @FieldResult(name = "carModel", column = "model")
//        , @FieldResult(name = "carRegistrationNumber", column = "registrationNumber")
//        , @FieldResult(name = "ownerFirstName", column = "firstName")
//        , @FieldResult(name = "ownerLastName", column = "lastName")
//        , @FieldResult(name = "ownerPhoneNumber", column = "contactPhone")
//}
//)
//)
//@Entity
public class InfoTotalDto {
    //region Fields
    private String garageSerialNumber;
    private String garageAddress;
    private String carModel;
    private String carRegistrationNumber;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerPhoneNumber;
    //endregion

    //region Properties

    public String getGarageSerialNumber() {
        return garageSerialNumber;
    }

    public void setGarageSerialNumber(String garageSerialNumber) {
        this.garageSerialNumber = garageSerialNumber;
    }

    public String getGarageAddress() {
        return garageAddress;
    }

    public void setGarageAddress(String garageAddress) {
        this.garageAddress = garageAddress;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    //endregion

    //region Methods
    @Override
    public String toString() {
        return "InfoTotalDto{" +
                "garageSerialNumber='" + garageSerialNumber + '\'' +
                ", garageAddress='" + garageAddress + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carRegistrationNumber='" + carRegistrationNumber + '\'' +
                ", ownerFirstName='" + ownerFirstName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", ownerPhoneNumber='" + ownerPhoneNumber + '\'' +
                '}';
    }
    //endregion
}
