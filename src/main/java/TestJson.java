import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Dennis Lang on 9/15/16.
 */
public class TestJson {

    public static LinkedHashMap<String, String> getMap(JSONObject jsObj, String name) {
        LinkedHashMap parameters = new LinkedHashMap();

        try {
            if (jsObj.has(name)) {
                JSONObject jobj = jsObj.getJSONObject(name);
                if (jobj != null) {
                    Iterator typeIter = jobj.keys();

                    while (typeIter.hasNext()) {
                        String parentKey = (String) typeIter.next();

                        try {
                            JSONObject jchildObj = jobj.getJSONObject(parentKey);
                            Iterator childIter = jchildObj.keys();

                            while (childIter.hasNext()) {
                                String childKey = (String) childIter.next();
                                parameters.put(parentKey + "." + childKey, jchildObj.getString(childKey));
                            }
                            continue;
                        } catch (JSONException jex) {
                            System.err.println("Error while parsing parameters\n" + jex.getLocalizedMessage());

                            // parameters.put(parentKey, jobj.getString(parentKey));
                        }

                        try {
                            JSONArray jsonArray  = jobj.getJSONArray(parentKey);
                            List<String> values = new ArrayList<String>();
                            for (int arrIdx = 0; arrIdx != jsonArray.length(); arrIdx++) {
                                String value = jsonArray.getString(arrIdx);
                                values.add(value);
                            }
                            parameters.put(parentKey, values.toString());
                            continue;
                        } catch (JSONException jex) {
                            System.err.println("Error while parsing parameters\n" + jex.getLocalizedMessage());
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            System.err.println("Error while getting map from JSON object" + ex.getLocalizedMessage());
        }

        return parameters;
    }

    static final String JSON_TEST1_STR = 
    "[{"
    + "    'Type': 'URLHeadline',"
    + "            'Priority': 100,"
    + "            'StartTime': '2015-11-04T07:40:00Z',"
    + "            'ExpirationTime': '2015-11-11T07:40:00Z',"
    + "            'Name': 'Another headline bound to DAY and HOUR',"
    + "            'URL': '',"
    + "            'Thumbnail': 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Bf109G_3Seiten_neu.jpg/1280px-Bf109G_3Seiten_neu.jpg',"
    + "            'Source': 'admin',"
    + "    'BindToDate': '2015-11-06',"
    + "            'Parameters': {"
    + "        'PlayerParameters': {"
    + "            'AdTagPhone' : 'http://pubads.g.doublecli.......',"
    + "                    'AdTagTablet' : 'http://pubads.g.doublecli.......'"
    + "        },"
    + "        'tags' : [ 'cow', 'dog', 'fish']"
    + "    }"
    + "},"
    + "{"
    + "    'Type': 'MRSSHeadline',"
    + "        'Priority': 100,"
    + "        'StartTime': '2015-11-10T13:26:00Z',"
    + "        'ExpirationTime': '2015-11-11T13:26:00Z',"
    + "        'Name': 'Multi 2',"
    + "        'URL': 'http://www.wgal.com/18265782',"
    + "        'Source': 'admin',"
    + "    'BindToDateAndTime': '2015-11-11T17:00:00Z'"
    + "}"
    + "]";
    
    public static void test1(final String s0) {

        String jsonStr = JSON_TEST1_STR.replaceAll("'", "\"");
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);

            System.out.println(jsonArray.toString(4));

            for (int idx = 0; idx != jsonArray.length(); idx++) {
                JSONObject jobj = jsonArray.getJSONObject(idx);

                Map<String, String> parameters = getMap(jobj, "Parameters");
                System.out.println(parameters.toString());
            }
        } catch (Exception ignore) {
        }

        System.out.println("[Done]");
    }
}