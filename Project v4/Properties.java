import java.util.*;
public class Properties {
    private ArrayList<Property> properties;
    public Properties(){
        properties = new ArrayList<>();
    }

    /* Adds property to the an arraylist */
    public void addProperty(Property property){
        properties.add(property);
    }
    
    /* Returns the properties arraylist */
    public ArrayList<Property> getProperties() {
        return properties;
    }
    
    /* Adds properties object, used from loading from the CSV file when the program starts */
    public void addProperties(Properties other){
        ArrayList<Property> p = other.getProperties();
        for(Property n : p){
            addProperty(n);
        }
    }

    /* Searches all properties for a given name */
    public ArrayList<Property> searchForOwner(Owner o){
        ArrayList<Property> byOwner = new ArrayList<>();
        for(Property property : properties){
            if(property.getOwner().equal(o)){
                byOwner.add(property);
            }
        }
        return byOwner;
    }

    public ArrayList<String> getPropertiesByName(){
        ArrayList<String> Name = new ArrayList<>();
        for(Property property : properties){
            Name.add(property.getOwner().toString());
        }
        ArrayList<String> RemoveRepeats = new ArrayList<>();
        for(String s:Name){
            if(!RemoveRepeats.contains(s)){
                RemoveRepeats.add(s);
            }
        }
        return RemoveRepeats;
    }

    
}
//volvunt ad inceptum