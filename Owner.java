 

public class Owner {
    private String Fullname;

    public Owner(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getFullname() {
        return Fullname;
    }

    @Override
    public boolean equals(Object other){
        return(toString().equalsIgnoreCase(other.toString()));
    }
    
    @Override
    public String toString() {
        return Fullname;
    }
}
