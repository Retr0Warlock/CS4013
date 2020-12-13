 

public class Owner {
    private String fullName;

    /**
     * Stores the given name of an owner.
     */public Owner(String fullName) {
        this.fullName = fullName.trim();
    }

    /**
     * Returns the owners full name.
     */public String getFullName() {
        return fullName;
    }
    
    /**
     * Checks if name of two owners are the same.
     */
    @Override
    public boolean equals(Object other){
        return(toString().equalsIgnoreCase(other.toString()));
    }
    
    /**
     * Returns the owners full name as a string.
     */
    @Override
    public String toString() {
        return fullName;
    }
}
