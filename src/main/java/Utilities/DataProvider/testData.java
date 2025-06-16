package Utilities.DataProvider;

import Utilities.Enums.PetStatus;
import org.testng.annotations.DataProvider;

public class testData {

    @DataProvider(name = "petStatusData")
    public Object[][] petStatusData() {
        return new Object[][] {
                {"available"},
                {"pending"},
                {"sold"}
        };
    }

    @DataProvider(name = "NegativePetStatusData")
    public Object[][] NegativePetStatusData() {
        return new Object[][] {
                {"Notavailable"},
                {"Notpending"},
                {"Notsold"}
        };
    }


    @DataProvider(name = "createPetData")
    public Object[][] createPetData() {
        return new Object[][] {
                {"Fluffy", 123, PetStatus.AVAILABLE},
                {"Buddy", 456, PetStatus.PENDING},
                {"Max", 789, PetStatus.SOLD}
        };
    }

    @DataProvider(name = "updatePetData")
    public Object[][] updatePetData() {
        return new Object[][] {
                {"Fluffy", 123, PetStatus.PENDING},
                {"Buddy", 456, PetStatus.SOLD},
                {"Max", 789, PetStatus.AVAILABLE}
        };
    }
    @DataProvider(name = "deletePetData")
    public Object[][] deletePetData() {
        return new Object[][] {
                {123},
                {456},
                {789}
        };
    }

    @DataProvider(name = "postPetByPetID")
    public Object[][] postPetByPetID() {
        return new Object[][] {
                { 123, "Fluffy",PetStatus.PENDING},
                { 456,"Buddy", PetStatus.SOLD},
                { 789, "Max", PetStatus.AVAILABLE}
        };
    }


}
