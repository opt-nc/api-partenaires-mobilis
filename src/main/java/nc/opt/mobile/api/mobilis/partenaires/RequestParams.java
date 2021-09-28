package nc.opt.mobile.api.mobilis.partenaires;

public class RequestParams {

    private String q;

    private NearBy nearBy;

    public NearBy getNearBy() {
        return nearBy;
    }

    public void setNearBy(NearBy nearBy) {
        this.nearBy = nearBy;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getQ() {
        return q;
    }

    public static class NearBy {

        private Double lon;
        private Double lat;
        private Integer distance;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }
    }
}
