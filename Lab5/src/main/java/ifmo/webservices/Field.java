
package ifmo.webservices;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "field")
@XmlEnum
public enum Field {

    @XmlEnumValue("id")
    ID("id"),
    @XmlEnumValue("name")
    NAME("name"),
    @XmlEnumValue("publishing")
    PUBLISHING("publishing"),
    @XmlEnumValue("author")
    AUTHOR("author"),
    @XmlEnumValue("year")
    YEAR("year"),
    @XmlEnumValue("pages")
    PAGES("pages");
    private final String value;

    Field(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Field fromValue(String v) {
        for (Field c: Field.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    @Override
    public String toString() {
        return value;
    }
}
