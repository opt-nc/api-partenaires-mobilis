package nc.opt.mobile.api.mobilis.partenaires.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.data.geo.Point;

@Converter
public class PointConverter implements AttributeConverter<Point, String> {
    private static WKTReader wktReader = new WKTReader();

    @Override
    public String convertToDatabaseColumn(Point attribute) {
        return String.format("POINT(%s %s)", attribute.getX(), attribute.getY());
    }

    @Override
    public Point convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        Geometry geometry;
        try {
            geometry = wktReader.read(dbData);
        } catch (ParseException e) {
            return null;
        }
        return new Point(geometry.getInteriorPoint().getX(), geometry.getInteriorPoint().getY());
    }
}
