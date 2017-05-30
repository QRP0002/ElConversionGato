package model;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import presenter.SplashActivityPresenter;

public class HTTPRequest {

    public void callDatabase() {
        new CallingAPI().execute("countType");
        new CallingAPI().execute("countConvert");
        new CallingAPI().execute("getTypeData");
        new CallingAPI().execute("getConvertData");
    }

    private class CallingAPI extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... action) {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("action", action[0])
                    .build();
            Request request = new Request.Builder()
                    .url("http://ec2-52-90-8-139.compute-1.amazonaws.com/API.php")
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String sending = response.body().string();
                return sending != null ? new String[] {action[0],
                        sending} : null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            SplashActivityPresenter presenter = SplashActivityPresenter.getInstance();
            super.onPostExecute(result);
            switch (result[0]) {
                case "countType":
                    presenter.setCountType(result[1]);
                    break;
                case "countConvert":
                    presenter.setCountConvert(result[1]);
                    break;
                case "getTypeData":
                    presenter.setTypeData(result[1]);
                    break;
                case "getConvertData":
                    presenter.setConvertData(result[1]);
                    try {
                        presenter.settingUpDatabaseData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default :
                    break;
            }
        }
    }
}