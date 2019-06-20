
package ifmo.webservices;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for field.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="field"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="id"/&gt;
 *     &lt;enumeration value="name"/&gt;
 *     &lt;enumeration value="publishing"/&gt;
 *     &lt;enumeration value="author"/&gt;
 *     &lt;enumeration value="year"/&gt;
 *     &lt;enumeration value="pages"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
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
