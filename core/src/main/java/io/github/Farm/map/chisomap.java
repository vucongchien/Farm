package io.github.Farm;

public class chisomap {

    private double imageWidth;
    private double imageHeight;
    private double lat1, lon1;
    private double lat2, lon2;

    public chisomap(double imageWidth, double imageHeight, double lat1, double lon1, double lat2, double lon2) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.lat2 = lat2;
        this.lon2 = lon2;
    }

    public double[] latLonToPixel(double lat, double lon) {
        double x = (lon - lon1) / (lon2 - lon1) * imageWidth;
        double y = (lat1 - lat) / (lat1 - lat2) * imageHeight;
        return new double[]{x, y};
    }

    public double[] pixelToLatLon(double x, double y) {
        double lon = lon1 + (x / imageWidth) * (lon2 - lon1);
        double lat = lat1 - (y / imageHeight) * (lat1 - lat2);
        return new double[]{lat, lon};
    }
}
