package project.randd;

public class Bridge {
    int aX, bX, aY, bY;

    public Bridge(int aX, int aY, int bX, int bY){
        this.aX = aX;
        this.aY = aY;
        this.bX = bX;
        this.bY = bY;
    }

    public int getaX(){
        return aX;
    }

    public int getbX() {
        return bX;
    }

    public int getaY() {
        return aY;
    }

    public int getbY() {
        return bY;
    }

    public boolean hasPoint(int x, int y) {
        if ((aX == x && aY == y) || (bX == x && bY == y))
            return true;
        else
            return false;
    }
}
