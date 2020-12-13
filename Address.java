

public class Address {
    String firstLine;
    String secondLine;
    String city;
    String county;
    String country;
    
    /**
     * Stores a given address of a Property.
     */
    public Address(String firstLine, String secondLine, String city, String county, String country) {
        this.firstLine = firstLine.trim();
        this.secondLine = secondLine.trim();
        this.city = city.trim();
        this.county = county.trim();
        this.country = country.trim();
    }
    
    /**
     * Stores a given address of a Property.
     */
    public Address(String firstLine, String city, String county, String country) {
        this(firstLine, "", city, county, country);
    }

    /**
     * Stores a given address of a Property.
     */
    public Address(String firstLine, String city, String county) {
        this(firstLine, city, county, "Ireland");
    }
    
    /**
     * Stores a given address of a Property.
     */
    public Address(String[] o) {
        this.firstLine = o[0];
        this.secondLine = o[1];
        this.city = o[2];
        this.county = o[3];
        this.country = o[4];
    }

    /**
     * Returns the first line of the address as a string.
     */
    public String getFirstLine() {
        return firstLine;
    }

    /**
     * Returns the second line of the address as a string.
     */
    public String getSecondLine() {
        return secondLine;
    }

    /**
     * Returns the city of the address as a string.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the county of the address as a string.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Returns the country of the address as a string.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Checks if two addresses are the same.
     */
    @Override
    public boolean equals(Object obj){
        return obj.toString().equalsIgnoreCase(toString());
    }
    
    /**
     * Returns the entire address as a string.
     */
    @Override
    public String toString() {
        return firstLine + "," + secondLine + "," + city + "," + county + "," + country;
    }
}
