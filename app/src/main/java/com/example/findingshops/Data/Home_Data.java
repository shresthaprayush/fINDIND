package com.example.findingshops.Data;

//Just to store home data.
public class Home_Data {

    private String Htitle;
    private String Hdescription;
    private int Hthumbnail;

    public Home_Data() {
    }

    public Home_Data(String htitle, String hdescription, int hthumbnail) {
        Htitle = htitle;
        Hdescription = hdescription;
        Hthumbnail = hthumbnail;
    }

    public String getHtitle() {
        return Htitle;
    }

    public String getHdescription() {
        return Hdescription;
    }

    public int getHthumbnail() {
        return Hthumbnail;
    }

    public void setHtitle(String htitle) {
        Htitle = htitle;
    }

    public void setHdescription(String hdescription) {
        Hdescription = hdescription;
    }

    public void setHthumbnail(int hthumbnail) {
        Hthumbnail = hthumbnail;
    }
}
