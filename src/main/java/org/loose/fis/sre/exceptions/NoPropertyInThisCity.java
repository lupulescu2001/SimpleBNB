package org.loose.fis.sre.exceptions;

public class NoPropertyInThisCity extends Exception{
  //  private String cityname;

    public NoPropertyInThisCity(String s){
        super(String.format("There are no properties in this city : %s , introduce different city",s));
    }
}
