package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Settings {
    private static Settings INSTANSE = new Settings();
    private final Properties properties = new Properties();
    Settings(){
        try{
            properties.load(new FileInputStream(this.getClass().getClassLoader().getResource("mkulakova.properties").getFile()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Settings getInstanse(){return INSTANSE;}
    public String value(String key){
        return this.properties.getProperty(key);
    }
}
