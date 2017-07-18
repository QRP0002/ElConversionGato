package presenter;

import android.util.Log;

import com.quinn.scitools.activity.SplashScreen;

import java.util.ArrayList;

import model.Conversion;
import model.ConversionRow;
import pojo.database.ConversionDBHelper;
import pojo.database.ConversionsDB;
import remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ConversionClient;

public class SplashActivityPresenter {

    private  ConversionClient mAPIService;
    private Integer remoteCount = -1;
    private final Object lock = new Object();

    //Splash Activity View Interface
    public interface SplashActivityView {
        void startMainActivity();

        void startConnectionDialog();
    }

    //View used for the SQLite Database.
    private SplashScreen view;

    public SplashActivityPresenter(SplashScreen view) {
        this.view = view;
    }

    public void networkFlow(boolean networkResult) throws InterruptedException {
        ConversionDBHelper db = new ConversionDBHelper(view);

        if(networkResult) {
            settingUpDatabaseData();
        } else if(!networkResult && db.getConversionRowCount() > 0) {
            view.startMainActivity();
        } else {
            view.startConnectionDialog();
        }
    }

    private void settingUpDatabaseData() throws InterruptedException {
        mAPIService = ApiUtils.getAPIService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    sendRowCount("countConvert");
                    while(remoteCount == -1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t1.start();
    }

    private void sendConversions(String title) throws NullPointerException {
        
        mAPIService.loadConversions(title).enqueue(new Callback<Conversion>() {
            @Override
            public void onResponse(Call<Conversion> call, Response<Conversion> response) {
                if(response.isSuccessful()) {
                    Conversion responseBody = response.body();
                    parseConversionData(responseBody);
                }
            }

            @Override
            public void onFailure(Call<Conversion> call, Throwable t) {
                Log.d("ZERO", "FIVE");
            }
        });
    }

    private void sendUpdates(String title, String id) throws NullPointerException {

        mAPIService.loadUpdates(title, id).enqueue(new Callback<Conversion>() {
            @Override
            public void onResponse(Call<Conversion> call, Response<Conversion> response) {
                if(response.isSuccessful()) {
                    Conversion responseBody = response.body();
                    parseConversionData(responseBody);
                }
            }

            @Override
            public void onFailure(Call<Conversion> call, Throwable t) {

            }
        });
    }

    private void sendRowCount(String title)  {

        mAPIService.loadRowCount(title).enqueue(new Callback<ConversionRow>() {
            @Override
            public void onResponse(Call<ConversionRow> call, Response<ConversionRow> response) {
                final ConversionRow responseBody = response.body();
                if(response.isSuccessful()) {
                    Thread t2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (lock) {
                                setRemoteCount(Integer.parseInt(responseBody.toString()));
                                lock.notify();
                            }
                        }
                    });
                    t2.start();

                    ConversionDBHelper db = new ConversionDBHelper(view);
                    int rowCount = db.getConversionRowCount();
                    if(rowCount == 0) {
                        Log.d("REMOTE", "REMOTE");
                        sendConversions("getConvertData");
                    } else if(rowCount > 0 && rowCount != remoteCount) {
                        Log.d("REMOTE", "REMOTE");
                        sendUpdates("getUpdateData",
                                Integer.toString(db.getConversionRowCount()));
                    } else if (rowCount > 0 && rowCount == remoteCount){
                        view.startMainActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<ConversionRow> call, Throwable t) {
            }
        });
    }

    private void parseConversionData(Conversion body) {
        String[] parseOne = body.toString().split(",");
        parseOne[0] = parseOne[0].replace("[", "");
        parseOne[parseOne.length - 1] = parseOne[parseOne.length - 1].replace("]", "");

        ArrayList<ConversionsDB> list = new ArrayList<ConversionsDB>();
        ConversionDBHelper db = new ConversionDBHelper(view);
        for(int i = 0; i < parseOne.length; i++) {
            String[] temp = parseOne[i].split("-");
            list.add(new ConversionsDB(Integer.parseInt(temp[0].trim()), temp[1].trim(),
                    temp[2].trim(), temp[3].trim(), temp[4].trim(), temp[5].trim()));
        }
        db.insertConversions(list);
        view.startMainActivity();
    }

    private void setRemoteCount(Integer remoteCount) {
        this.remoteCount = remoteCount;
    }
}