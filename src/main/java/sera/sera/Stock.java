package sera.sera;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Stock object stores dividend information, name ,market and other details of a stock (fetched from API) into one object
 */

public class Stock {
    // most recently announced / released dividend
    private Dividend lastDiv;
    // announced, upcoming dividend (null if nothing announced)
    private Dividend payDiv;
    // list of all past, released dividends
    private ArrayList<Dividend> dividends;

    private String stockCode; // stock code that I can search this stock up in the API with - unique identifier
    private String market; // stock market the stock belongs to
    private double marketPrice; // current market price (fetched from API)

    private double percentdiv; // the percentage of the dividend value out of the market price
    private double totaldiv; // total value of dividend releases this year
    private int divfreq; // how many dividend releases in a year
    private double priceChange; // change in market price compared to yesterday's closing
    private double pnl; // percentage change in market price compared to yesterday's closing
    private int holdings; // how many of this stock does a user have

    private int gap; // the gap between each dividend release (in months)

    private String stockCur; // what currency does the stock market (that this stock belongs to) use



    public Dividend getPayDiv() {
        return payDiv;
    }

    public void setPayDiv(Dividend payDiv) {
        this.payDiv = payDiv;
    }

    public String getStockCur() {
        return stockCur;
    }

    public void setStockCur(String stockCur) {
        this.stockCur = stockCur;
    }

    public Dividend getLastDiv() {
        return lastDiv;
    }

    public void setLastDiv(Dividend lastDiv) {
        this.lastDiv = lastDiv;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }



    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public ArrayList<Dividend> getDividends() {
        return dividends;
    }

    public void setDividends(ArrayList<Dividend> dividends) {
        this.dividends = dividends;
    }

    public double getPercentdiv() {
        return percentdiv;
    }

    public void setPercentdiv(double percentdiv) {
        this.percentdiv = percentdiv;
    }

    public double getTotaldiv() {
        return totaldiv;
    }

    public void setTotaldiv(double totaldiv) {
        this.totaldiv = totaldiv;
    }

    public int getDivfreq() {
        return divfreq;
    }

    public void setDivfreq(int divfreq) {
        this.divfreq = divfreq;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    public int getHoldings() {
        return holdings;
    }

    public void setHoldings(int holdings) {
        this.holdings = holdings;
    }

    // constructor 1 - can just create a stock with stock code (identifier), stock market, and amount of the stock this user has
    public Stock(String stockCode, String market, int holdings) {
        this.stockCode = stockCode;
        this.market = market;
        this.holdings = holdings;
    }

    // constructor 2 - create a stock from another stock
    public Stock(Stock stock){
        this.stockCode = stock.getStockCode();
        this.market = stock.getMarket();
        this.holdings = stock.getHoldings();
        this.marketPrice = stock.getMarketPrice();
        this.dividends = stock.getDividends();
        this.percentdiv = stock.getPercentdiv();
        this.totaldiv = stock.getTotaldiv();
        this.divfreq = stock.getDivfreq();
        this.priceChange = stock.getPriceChange();
        this.pnl = stock.getPnl();
        this.gap = stock.getGap();
        this.stockCur = stock.getStockCur();
        if (stock.getLastDiv()!= null){
            this.lastDiv = stock.getLastDiv();
        }
        if(stock.getPayDiv()!=null){
            this.payDiv = stock.getPayDiv();
        }
    }
}
