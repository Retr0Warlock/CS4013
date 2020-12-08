public class Owner {
    private String Fname;
    private String Lname;

    /*Initializes the Owner class */
    public Owner(String firstname, String lastname) {
        this.Fname = firstname;
        this.Lname = lastname;
    }

    /* Returns firstname @return */
    public String getFirstname() {
        return Fname;
    }

    /* Returns lastname */
    public String getLastname() {
        return Lname;
    }

    /* Checks to see if two Owner objects are equal */
    public boolean equal(Owner other){
        if(other.getFirstname().compareToIgnoreCase(Fname) == 0 &&
            other.getLastname().compareToIgnoreCase(Lname) == 0){
            return true;
        }else {
            return false;
        }

    }
    
    @Override
    public String toString() {
        return Fname + " " + Lname;
    }
}
//volvunt ad inceptum