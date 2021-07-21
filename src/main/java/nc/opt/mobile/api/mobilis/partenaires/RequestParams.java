package nc.opt.mobile.api.mobilis.partenaires;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RequestParams {

    @Valid
    private NearBy nearBy;

    public NearBy getNearBy() {
        return nearBy;
    }

    public void setNearBy(NearBy nearBy) {
        this.nearBy = nearBy;
    }

    public static class NearBy {
        @NotNull
        private Double lon;
        @NotNull
        private Double lat;
        @NotNull
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
