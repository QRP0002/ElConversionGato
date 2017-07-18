package remote;

import service.ConversionClient;

public class ApiUtils {

    private ApiUtils() {}

    private static final String BASE_URL = "http://ec2-52-90-8-139.compute-1.amazonaws.com/";

    public static ConversionClient getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ConversionClient.class);
    }
}
