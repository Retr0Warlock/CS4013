 

public class Owner {
    private String Fullname;

    public Owner(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getFullname() {
        return Fullname;
    }

    public boolean equal(Owner other){
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
