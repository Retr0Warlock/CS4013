 

public class Owners {
    private String Fullname;

    public Owners(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getFullname() {
        return Fullname;
    }

    public boolean equal(Owners other){
        if(other.getFullname().compareToIgnoreCase(Fullname) == 0){
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return Fullname;
    }
}
