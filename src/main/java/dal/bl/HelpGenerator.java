package dal.bl;

import java.util.Random;

public class HelpGenerator {

    //region Field
    private Random rnd;
    private String[] firstNames;
    private String[] lastNames;
    private String[] carModel;
    private String[] numberReg;
    private String[] serialNum;
    private String[] address;
    //endregion

    //region CTOR
    public HelpGenerator()
    {
        rnd = new Random();
        firstNames = new String[] {"Oleg", "Janetta", "Alyce", "Dezi", "Clemency", "Denzel", "Gale", "Kipling",
                "Arlie", "Genie", "Thad", "Casimir", "Twila", "JoBeth", "Ashlie", "Nazarii"};
        lastNames = new String[] {"Holland", "Newton", "Sherman", "Strickland", "Stone", "Carter", "Graves", "Tate",
                "Marsh", "Donnelly", "Fox", "Barlow", "Mac", "Coleman", "Griffin", "Acosta"};
        carModel = new String[]{"Chevrolet Silverado", "Dacia Duster", "Kia Optima", "Daewoo", "Skoda Octavia", "Tesla Model S", "Renault Megane", "Audi RS3",
                "Seat Ibiza", "Porsche Cayenne", "BMW 5 Series", "Land Rover Range Rover", "Audi Q7", "Honda Civic", "Audi Q3", "Lexus RX"};
        numberReg = new String[]{"AA", "AT", "AP", "AO", "AM", "AI", "AH", "AE", "AC", "AB", "AX", "CE", "CB", "CA", "BX", "BT", "BO", "BM", "BK", "BI", "BH", "BE", "BC", "BB", "BA"};
        serialNum = new String[]{"АК", "КК", "АА", "КА", "АВ", "КВ", "АС", "КС", "АЕ", "КЕ", "АН", "КН", "AI", "КІ", "АМ", "КМ", "АО", "КО", "АР", "КР", "АТ", "КТ", "АХ", "КХ", "ВА", "НА", "ВВ", "HВ", "ВС", "НС", "ВЕ", "НЕ", "ВН", "НН", "BI", "НІ", "ВК", "НК", "ВМ", "НМ", "ВО", "НО", "ВТ", "НТ", "ВХ", "НХ", "СА", "ІА", "СВ", "ІВ", "СЕ", "ІЕ", "СН", "ІН", "ІІ"};
        address = new String[]{"Lviv, st.Doroshenka 50", "Lviv, st.Franka 34", "Odesa, st.Arnautska 79", "Lviv, st.Antonovicha 67"};
    }
    //endregion

    //region Methods
    // Owner
    public String ownerFirstName()
    {
        return firstNames[rnd.nextInt(500) % firstNames.length];
    }
    public String ownerLastName()
    {
        return lastNames[rnd.nextInt(500) % lastNames.length];
    }
    public String ownerPhone()
    {
        String newPhone = "+380" + (rnd.nextInt(999999999 + 1 - 100000000) + 100000000);
        return newPhone;
    }
    public Integer ownerAmountCars()
    {
        Integer amount = rnd.nextInt(4 + 1 - 1) + 1;
        return amount;
    }
    // Car
    public String carModel()
    {
        return carModel[rnd.nextInt(500) % carModel.length];
    }
    public String carRegistrationNumber()
    {
        String newRegNumber = numberReg[rnd.nextInt(numberReg.length)] + " " + (rnd.nextInt(9999 + 1 - 1000) + 1000) + " " + numberReg[rnd.nextInt(numberReg.length)];
        return newRegNumber;
    }
    public Integer carKilometrage()
    {
        return rnd.nextInt(250000 + 1 - 20000) + 20000;
    }
    // Garage
    public String garageSerialNumber()
    {
        String newRegNumber =(rnd.nextInt(999 + 1 - 100) + 100) + numberReg[rnd.nextInt(numberReg.length)] + (rnd.nextInt(9999 + 1 - 1000) + 1000) + numberReg[rnd.nextInt(numberReg.length)];
        return newRegNumber;
    }
    public String garageAddress()
    {

        return address[rnd.nextInt(500) % address.length];
    }
    public Integer garageMaxCars()
    {
        Integer maxSize = rnd.nextInt(8 + 1 - 1) + 1;
        return maxSize;
    }
    // Random
    public Integer randomInt( Integer max)
    {
        return rnd.nextInt(max + 1 - 1) + 1;
    }
    public Integer randomInt( Integer max, Integer min)
    {
        return rnd.nextInt(max + 1 - min) + min;
    }
    //endregion

}