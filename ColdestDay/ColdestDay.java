
/**
 * Write a description of ColdestDay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;

public class ColdestDay {
    
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        //FileResource fr = new FileResource("F:/Twinkle's Archive 2020/Java Course-2 Files/WEEK 3/ColdestDay/nc_weather/2014/weather-2014-01-03.csv");
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestSoFar = coldestHourInFile(parser);
        System.out.println("Coldest temperature of the day is : " + coldestSoFar.get("TemperatureF"));
        System.out.println("Date and time : " + coldestSoFar.get("DateUTC"));
        //System.out.println("Filename : " + );
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null;
        for(CSVRecord currentRow : parser)
        {
            coldestSoFar = commonCT(coldestSoFar, currentRow);
        }
        return coldestSoFar; 
    }
    
    public void testFileWithColdestTemperatue(){
        CSVRecord coldestSoFar = fileWithColdestTemperature();
        System.out.println("Coldest temperature of the year is : " + coldestSoFar.get("TemperatureF"));
        System.out.println("Date and time : " + coldestSoFar.get("DateUTC"));
    }
    
    public CSVRecord fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource(); 
        CSVRecord coldestSoFar = null;
        String filename = null;
        for(File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            coldestSoFar = commonCT(coldestSoFar, currentRow);
            if(coldestSoFar == currentRow)
            {
                filename = f.getName();
            }
        }
        System.out.println("Filename : " + filename);
        return coldestSoFar;
    }
    
    public CSVRecord commonCT(CSVRecord coldestSoFar, CSVRecord currentRow){
        if(coldestSoFar == null)
        {
            coldestSoFar = currentRow;
        }
        else
        {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
            if(currentTemp < coldestTemp && currentTemp != -9999)
            {
                coldestSoFar = currentRow;
            }
        }
        return coldestSoFar;
    }
    
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumiditySoFar = lowestHumidityInFile(parser);
        System.out.println("Lowest humidity of the day is : " + lowestHumiditySoFar.get("Humidity")); 
        System.out.println("Date and time : " + lowestHumiditySoFar.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumiditySoFar = null;
        for(CSVRecord currentRow : parser)
        {
            lowestHumiditySoFar = commonLH(lowestHumiditySoFar, currentRow);
        }
        return lowestHumiditySoFar;
    }
    
    public void testLowestHumidityInManyFiles(){
        CSVRecord lowestHumiditySoFar = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity of the year is : " + lowestHumiditySoFar.get("Humidity"));
        System.out.println("Date and time : " + lowestHumiditySoFar.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource(); 
        CSVRecord lowestHumiditySoFar = null;
        for(File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            lowestHumiditySoFar = commonLH(lowestHumiditySoFar, currentRow);
        }
        return lowestHumiditySoFar;
    }
    
    public CSVRecord commonLH(CSVRecord lowestHumiditySoFar, CSVRecord currentRow){
        if(lowestHumiditySoFar == null){
            String CRH = currentRow.get("Humidity");
            if(CRH.contains("N/A") == false)
            {
                lowestHumiditySoFar = currentRow;
            }
        }
        else
        {
            String CRH = currentRow.get("Humidity");
            if(CRH.contains("N/A") == false)
            {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestHumiditySoFar.get("Humidity"));
                if(currentHumidity < lowestHumidity)
                {
                    lowestHumiditySoFar = currentRow;
                }
            }
        }
        return lowestHumiditySoFar;
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureInFile(parser);
        System.out.println("Average temperature of the day is : " + avg);
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        double avg = 0;
        int count = 0;
        for(CSVRecord currentRow : parser)
        {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            avg += currentTemp;
            count += 1;
        }
        avg = avg/count;
        return avg;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureWithHighHumidityInFile(parser, 80);
        if(avg == 0)
        {
            System.out.println("No temperatures with that humidity");         
        }
        else
        {
            System.out.println("Average temperature with high humidity : " + avg);
        }
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double avg = 0;
        int count = 0;
        for(CSVRecord currentRow : parser)
        {
            int currHum = Integer.parseInt(currentRow.get("Humidity"));
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            if(currHum >= value)
            {
                avg += currentTemp;
                count += 1;
            }
        }
        if(count != 0)
        {
            avg = avg/count;
        }
        return avg;
    }
}