package sera.sera;

/**
 * used to store the date, corresponding stock's information, price and type of a dividend release into one object
 */

public class Dividend {
    private int month; // month of dividend release
    private int year; // year of dividend release
    private int day; // day of dividend release
    private String stockID; // corresponding ID of the stock that has this dividend release
    private double divPrice; // price of the dividend release
    private String divType; // type of dividend - is it past (already released), announced (from API) or estimated (my own prediction)

    // constructor 1 - can directly create a dividend from another dividend (already created)
    public Dividend(Dividend d){
        this.month = d.getMonth();
        this.year = d.getYear();
        this.day = d.getDay();
        this.stockID = d.getStockID();
        this.divPrice = d.getDivPrice();
        if (d.getDivType()!=null){
            this.divType = d.getDivType();
        }
    }

    // constructor 2 - can create a dividend from date stock ID and the price (fetched from API)
    public Dividend(int month, int year, int day, String stockID, double divPrice) {
        this.month = month;
        this.year = year;
        this.day = day;
        this.stockID = stockID;
        this.divPrice = divPrice;
    }

    public String getDivType() {
        return divType;
    }
    public void setDivType(String divType) {
        this.divType = divType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public double getDivPrice() {
        return divPrice;
    }

    public void setDivPrice(double divPrice) {
        this.divPrice = divPrice;
    }
}
