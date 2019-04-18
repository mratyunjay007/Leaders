package com.spirit.tech.leaders;

public class Leader {
    private String Name;
    private String DOB;
    private String DOE;
    private String POB;
    private  String Contibution;
    private String Vision;
    private int Color;




    public Leader(String name, String DOB, String DOE, String POB, String contibution, String vision,int color) {
        Name = name;
        this.DOB = DOB;
        this.DOE = DOE;
        this.POB=POB;
        Contibution = contibution;
        Vision = vision;
        Color=color;

    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDOE() {
        return DOE;
    }

    public void setDOE(String DOE) {
        this.DOE = DOE;
    }

    public String getPOB() {
        return POB;
    }

    public void setPOB(String POB) {
        this.POB = POB;
    }

    public String getContibution() {
        return Contibution;
    }

    public void setContibution(String contibution) {
        Contibution = contibution;
    }

    public String getVision() {
        return Vision;
    }

    public void setVision(String vision) {
        Vision = vision;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }


}
