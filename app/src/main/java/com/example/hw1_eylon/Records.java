package com.example.hw1_eylon;

class Records {
    private int distance;
    private int coins;
    private String time;
    private double latitude;
    private double longitude;

    public Records(){


}
    public Records(int distance, int coins, String time, double latitude, double longitude) {
        this.distance = distance;
        this.coins = coins;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getDistance() {
        return distance;
    }

    public Records setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public int getCoins() {
        return coins;
    }

    public Records setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Records setTime(String time) {
        this.time = time;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Records setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Records setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}

