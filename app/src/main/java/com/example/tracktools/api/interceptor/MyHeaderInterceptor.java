package com.example.tracktools.api.interceptor;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyHeaderInterceptor implements Interceptor {
    private static final String TAG = "HeaderInterceptor";

    public MyHeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        builder.addHeader("token", HostSpUtil.getInstance().getToken());
//        builder.addHeader("App-Version", SPHelper.getAppVersion());
        builder.addHeader("Content-Type", "x-www-form-urlencoded");
//        builder.addHeader("accept", String.format("application/vnd.ipad.lshy-%s+json", BuildConfig.statePlugin ? HostSpUtil.getInstance().getApiVersion() : "2.1"));
//        builder.addHeader("lsbcVersion", HostSpUtil.getInstance().getLsbcVersion());
//        builder.addHeader("language", Locale.getDefault().getLanguage());
        Request request = builder.build();
        //String ms = request.toString() + "  token=" + request.header("token") + "  api=" + request.header("accept") + "  App-Version=" + SPHelper.getAppVersion();
        //ALog.i(ms);
       // CommUtils.writeLog(ms);

        Response response = chain.proceed(request);
        Response.Builder responseBuilder = response.newBuilder();
        response = responseBuilder.build();
        return response;
    }
}
