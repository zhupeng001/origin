import org.omg.CORBA.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2018/3/1.
 */
public class HttpClientTest {
    public static void main(String[] args) {
        String licenseNo = "浙A588AX";
        String token = "7cc2bd72eb1e4522804dca3b88e8644d";
        String city = "330100";
        String timestamp = Long.toString(System.currentTimeMillis());
        String sign;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("licenseNo", licenseNo);
        params.put("token", token);
        params.put("city", city);
        params.put("timestamp", timestamp);

        List<NameValuePair> urlParams=new ArrayList<NameValuePair>();

    }

}
