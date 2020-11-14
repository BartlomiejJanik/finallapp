package pl.sda.finalapp.app.users;

public enum Countries {

    POLAND("Polska", "pl"),
    GERMANY("Niemcy", "de"),
    CHINA("Chiny", "cn"),
    USA("USA", "us");

    private final String countryName;
    private final String symbol;

    Countries(String countryName, String symbol) {

        this.countryName = countryName;
        this.symbol = symbol;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getSymbol() {
        return symbol;
    }
}