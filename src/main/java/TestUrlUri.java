import okhttp3.HttpUrl;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TestUrlUri {

    private final static String url1 = "https://cbslocal-uploads.storage.googleapis.com/anv-pvw/C25/012/C25012566E874B87BE024E5B2C939E0B_8.jpg?GoogleAccessId=onemcpadmin@anvato-mcp-apps.iam.gserviceaccount.com&Expires=1635617595&Signature=QV%2F0tkkwNIurYXlFuPWUySleJ0FvFvMkw7eRWQ6Zm5M9cwFYKz0VEoydslIkKygBYLCmFRctF2COgk0lGugQLN528R0KMIDP2f8avUzcvdD1Q667UWsBB1mJARQnSCpOKV5LlflKkBNg%2F%2B1wqXFdyu9mIwpSFbYYF45C1LAPJDtcCVJIhLSjX7c8N5iUMEEAS2yXpUhL%2BWw5wpNFjI05%2FK1wiNByXTNmcmOPUen2jYTCEON2%2BxQG27ddTyhcPLfMvl6fN5HpAnsSG1%2F%2B9Ze9PSO1dciIHi%2FT5NQYBQnZJ3jQ2fQxTx0vle2x3PJa%2FOeMvHyU6yrM4MsrP%2F%2FXpXr8SA%3D%3D";
    private final static String url2 = "https://host.com/path1/file2?Query1=%26And&Query2=@%40At&Query3=%2BPlus&Query4=!%21Exclamation%23Hash$%24Dollar%25Percent*%2AStar{%7BLeftBrace";
    private final static String url3 = "https://host.com/path1/file2?Query1=;/?:@+$,!*()==&&[]{}";

    public static void Test() {

        URL url;

        try {
            url = getUrl(url1);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println(" TESTurluri [DONE]");
    }

    public static URL getUrl(String urlStr) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
        String decStr = URLDecoder.decode(urlStr, StandardCharsets.UTF_8.toString());
        URL url = new URL(decStr);    // Decode to handle case where already encoded
        utils.URI uri = new utils.URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        URL urlOut = uri.toURL();



        System.out.println("InputRawStr=" + urlStr);
        System.out.println(" DecodedStr=" + decStr);
        System.out.println(" UriStr=    " + uri.toString());
        // System.out.println(" UriASCII=  " + uri.toASCIIString());

        System.out.println("\nDecodedURL "
                + "\n Protocol=" + url.getProtocol()
                + "\n Host=    " + url.getHost()
                + "\n Path=    " + url.getPath()
                + "\n Query=   " + url.getQuery()
        );

        System.out.println("\nUri "
                + "\n Protocol=" + uri.getScheme()
                + "\n Host=    " + uri.getHost()
                + "\n Path=    " + uri.getPath()
                + "\n Query=   " + uri.getQuery()
        );
        System.out.println("\nUri "
                + "\n Protocol=" + uri.getScheme()
                + "\n Host=    " + uri.getHost()
                + "\n PathRaw= " + uri.getRawPath()
                + "\n QueryRaw=" + uri.getRawQuery()
        );

        System.out.println("\nEncodedURL "
                + "\n Protocol=" + urlOut.getProtocol()
                + "\n Host=    " + urlOut.getHost()
                + "\n Path=    " + urlOut.getPath()
                + "\n Query=   " + urlOut.getQuery()
        );

        HttpUrl httpUrl = HttpUrl.parse(urlStr);
        URI httpUri = httpUrl.uri();
        System.out.println("\nHttpUrl "
                + "\n Protocol=" + httpUri.getScheme()
                + "\n Host=    " + httpUri.getHost()
                + "\n Path=    " + httpUri.getPath()
                + "\n Query=   " + httpUri.getQuery()
        );
        return urlOut;
    }
}
