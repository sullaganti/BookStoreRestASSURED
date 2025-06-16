package Utilities.envPicker;

import org.aeonbits.owner.Config;

@Config.Sources({
//NOTE - COMPLILE THE PROJECT BEFORE TRYING TO READ PROP FILES
        // "classpath:environment/${env}.properties"
        //    "file:D:\\Java\\SeleniumFramework-master\\src\\main\\resources\\environment\\${env}.properties"
        "${file}"

})
public interface Environment extends Config {

    String BookStore_BaseURI();
    String PetStore_BaseURI();

}