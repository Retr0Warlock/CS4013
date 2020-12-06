

public class Address {
    String firstLine;
    String secondLine;
    String city;
    String county;
    String country;

    public Address(String firstLine, String secondLine, String city, String county, String country) {
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.city = city;
        this.county = county;
        this.country = country;
    }

    public Address(String firstLine, String city, String county, String country) {
        this(firstLine, "", city, county, country);
    }

    public Address(String firstLine, String city, String county) {
        this(firstLine, city, county, "Ireland");
    }

    public Address(String[] o) {
        this.firstLine = o[0];
        this.secondLine = o[1];
        this.city = o[2];
        this.county = o[3];
        this.country = o[4];
    }

    public String getFirstLine() {
        return firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return firstLine + "," + secondLine + "," + city + "," + county + "," + country;
    }
}
