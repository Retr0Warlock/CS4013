 

public class Owner {
    private String fullName;

    public Owner(String fullName) {
        this.fullName = fullName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object other){
        return(toString().equalsIgnoreCase(other.toString()));
    }
    
    @Override
    public String toString() {
        return fullName;
    }
}
