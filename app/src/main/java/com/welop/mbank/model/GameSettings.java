package com.welop.mbank.model;

public class GameSettings {
    private static final int DEFAULT_LUXURY_TAX = 100;
    private static final int DEFAULT_INCOME_TAX = 200;
    private static final int DEFAULT_GO_COST = 200;
    private static final int DEFAULT_START_BALANCE = 1500;
    private static final Boolean DEFAULT_SHOW_MONEY = true;
    private int goCost;
    private int luxuryTaxCost;
    private int incomeTaxCost;
    private int startBalance;
    private Boolean showBalance;

    public Boolean getShowBalance() {
        return showBalance;
    }

    public void setShowBalance(Boolean showBalance) {
        this.showBalance = showBalance;
    }

    /**
     * Returns room's "GO" cost setting.
     * @return Lobby's "GO" cost setting.
     */
    public int getGoCost() {
        return goCost;
    }

    /**
     * Sets room's "GO" cost setting.
     * @param goCost Lobby's "GO" cost setting.
     */
    public void setGoCost(int goCost) {
        this.goCost = goCost;
    }

    /**
     * Returns room's Luxury tax setting.
     * @return Lobby's Luxury tax setting.
     */
    public int getLuxuryTaxCost() {
        return luxuryTaxCost;
    }

    /**
     * Sets room's Luxury tax setting.
     * @param luxuryTaxCost Lobby's Luxury tax setting.
     */
    public void setLuxuryTaxCost(int luxuryTaxCost) {
        this.luxuryTaxCost = luxuryTaxCost;
    }

    /**
     * Returns room's Income tax setting.
     * @return Lobby's Income tax setting.
     */
    public int getIncomeTaxCost() {
        return incomeTaxCost;
    }

    /**
     * Sets room's Luxury tax setting.
     * @param incomeTaxCost Lobby's Luxury tax setting.
     */
    public void setIncomeTaxCost(int incomeTaxCost) {
        this.incomeTaxCost = incomeTaxCost;
    }

    /**
     * Returns room wallets start balance.
     * @return Lobby wallets start balance.
     */
    public int getStartBalance() {
        return startBalance;
    }

    /**
     * Sets room wallets start balance.
     * @param startBalance Lobby wallets start balance.
     */
    public void setStartBalance(int startBalance) {
        this.startBalance = startBalance;
    }

    /**
     * Public constructor.
     * @param goCost Lobby's "GO" setting.
     * @param luxuryTaxCost Lobby's Luxury tax setting.
     * @param incomeTaxCost Lobby's Income tax setting.
     * @param startBalance Lobby wallets start balance.
     */
    public GameSettings(int goCost, int luxuryTaxCost, int incomeTaxCost, int startBalance, Boolean showBalance) {
        this.goCost = goCost;
        this.luxuryTaxCost = luxuryTaxCost;
        this.incomeTaxCost = incomeTaxCost;
        this.startBalance = startBalance;
        this.showBalance = showBalance;
    }

    /**
     * Default constructor.
     */
    public GameSettings() {
        this(DEFAULT_GO_COST, DEFAULT_INCOME_TAX, DEFAULT_LUXURY_TAX, DEFAULT_START_BALANCE, DEFAULT_SHOW_MONEY);
    }
}
