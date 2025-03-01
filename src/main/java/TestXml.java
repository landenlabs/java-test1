import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Dennis Lang on 12/10/16.
 *
 * Java encodes strings as an array of char.
 * A char is a 16bit unicode character.
 * http://docs.oracle.com/javase/6/docs/api/java/lang/Character.html#unicode
 *
 * Assigning a character value to an integer converts the 16-bit Unicode character code into its integer equivalent.
 * Unicode encoding coincides with ASCII for the first 256 characters.
 * Unicode correlates with Extended ASCII (8-bit) for the first 256 characters;
 * Extended ASCII, in turn, corresponds directly with 7-bit ASCII for the first 128.
 *
 * So that 'c' is encoded as 0x63 in Unicode, Extended ASCII, and ASCII.
 *
 * This is why you'd see the int for 'c' and think it's ASCII (which it sortof is :).
 *
 * 7-bit ASCII (ISO 646) and 8-bit ISO 8859-1 (Latin-1) are proper subsets of Unicode.
 * That being said, Java encodes all character values as 16-bit Unicode
 *
 */
public class TestXml {

    static final int USE_STRING = 0;
    static final int USE_BYTE = 1;
    static final String[] useModeName = { "String", "ByteArray" };

    static String xml_ISO8859_1 = "<?xml version='1.0' encoding='iso-8859-1'?><root>ä</root>";
    static String xml_ERROR_NONE = "<?xml version='1.0' ?><root>ä</root>";
    static String xml_ERROR_UTF8 = "<?xml version='1.0' encoding='utf-8'?><root>ä</root>";

    // byte[] bytes = {77, -61, -68, 110, 99, 104, 101, 110};
    // String actual = new String(bytes, StandardCharsets.UTF_8);

    // static String xml_UTF8 = "<?xml version='1.0' encoding='UTF-8'?><root>¥1é02°</root>";
    static String xml_UTF8 = "<?xml version='1.0' encoding='UTF-8'?><root>øæå Norwegian or French êèé.</root>";
    // static String xml_UTF8 = "<?xml version='1.0' encoding='UTF-8'?><root>Norwegian</root>";

    public static void tests() {
        test1();
        test2();
        // showCharSet();
        // test3();
    }

    public static void test1() {

        System.out.println("\nXml ISO-8859-1 from file");
        parseFile(xml_ISO8859_1, "iso-8859-1");

        System.out.println("\nXml UTF-8 from file");
        parseFile(xml_UTF8, "utf-8");
    }

    static void parseFile(String xmlStr, String encoding) {

        System.out.println("  XmlStr=" + xmlStr);

        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream("1.xml"), encoding);
            writer.write(xmlStr);
            writer.close();

            SAXParserFactory sf = SAXParserFactory.newInstance();
            SAXParser p = sf.newSAXParser();
            p.parse(new FileInputStream("1.xml"), new DefaultHandler() {
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    int c = (int) ch[start];
                    System.out.printf("  Char=%d(dec) %x(hex) str=", c, c);
                    System.out.println(String.valueOf(ch, start, length));
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }



    public static void test2() {

        for (int useMode = USE_STRING; useMode <= USE_BYTE; useMode++) {
            System.out.print("\n=========== (" + useModeName[useMode] + ") ===========");

            // *** This works because Java string is UTF-16 and iso-8859-1 is a subset of UTF-16
            System.out.println("\nXml ISO-8859-1 from string");
            parseCharXml(xml_ISO8859_1, "ISO-8859-1", useMode);

            System.out.println("\nXml ERROR none  from string");
            parseCharXml(xml_ERROR_NONE, "ISO-8859-1", useMode);

            System.out.println("\nXml ERROR UTF-8 from string");
            parseCharXml(xml_ERROR_UTF8, "ISO-8859-1", useMode);

            // ***  UTF-8 fails as string because not a subset of UTF-16
            //      Works if uses as byte array.
            System.out.println("\nXml UTF-8 from string");
            parseCharXml(xml_UTF8, "UTF-8", useMode);
        }
    }

    public static void test3() {
        System.out.println("\nXml ISO-8859-1 from byte");
        parseByteXml(xml_ISO8859_1);
    }

    public static void parseCharXml(String xmlStr, String encoding, int useType) {
        try {
            System.out.println("  XmlStr=" + xmlStr);

            String newStr = new String(xmlStr.getBytes(encoding), encoding);
            if (!newStr.equals(xmlStr)) {
                System.out.println("  Not encoded correctly, missing some characters");
            }

            SAXParserFactory sf = SAXParserFactory.newInstance();
            SAXParser saxParser = sf.newSAXParser();
            InputStream inStream;

            if (useType == USE_BYTE) {
                // UTF-8 only works if converted to byte stream.
                byte[] inBytes = xmlStr.getBytes(encoding);
                inStream = new java.io.ByteArrayInputStream(inBytes, 0, inBytes.length);
            } else {
                inStream = new StringBufferInputStream(xmlStr);
            }

            final InputSource inSource = new InputSource(inStream);
            System.out.println("  Input encoding=" + inSource.getEncoding());

            saxParser.parse(inSource, new DefaultHandler() {
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    int c = (int) ch[start];
                    System.out.printf("  Char=%d(dec) %x(hex) str=", c, c);
                    System.out.println(String.valueOf(ch, start, length));
                }
            });
        } catch (Exception ex) {
            ColorPrint.red("  [Error] ");
            System.out.println(ex.getMessage());
        }
    }

    public static void parseByteXml(String xmlStr) {
        try {
            byte[] bytes = xmlStr.getBytes();

            SAXParserFactory sf = SAXParserFactory.newInstance();
            SAXParser p = sf.newSAXParser();
            p.parse(new ByteArrayInputStream(bytes, 0, bytes.length), new DefaultHandler() {
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    int c = (int) ch[start];
                    System.out.printf("  Char=%d(dec) %x(hex) str=", c, c);
                    System.out.println(String.valueOf(ch, start, length));
                }
            });
        } catch (Exception ex) {
            ColorPrint.red("  [Error] ");
            System.out.println(ex.getMessage());
        }
    }

    public static void showCharSet() {
        java.util.SortedMap<String, Charset> charSet = Charset.availableCharsets();
        for (String name : charSet.keySet()) {
            System.out.println(name);
        }
    }


}
