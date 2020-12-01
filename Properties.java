import java.util.ArrayList;
public class Properties {
    private ArrayList<Property> properties;
    public Properties(){
        properties = new ArrayList<>();
    }

    public void addProperty(Property prop){
        properties.add(prop);
    }

    public void addProperties(Properties other){
        ArrayList<Property> property = other.getProperties();
        for(Property n : property){
            addProperty(n);
        }
    }

    public ArrayList<Property> searchByName(Owners owner){
        ArrayList<Property> byName = new ArrayList<>();
        for(Property prop : properties){
            if(prop.getOwners().equal(owner)){
                byName.add(prop);
            }
        }
        return byName;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }
}
