import okhttp3.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("cardNum", "12345678912334")
                    .addFormDataPart("validPeriod", "0422")
                    .addFormDataPart("cvc", "111")
                    .addFormDataPart("installment", "0")
                    .addFormDataPart("paymentAmount", "20000")
                    .addFormDataPart("vatAmount", "")
                    .build();

            Request request = new Request.Builder()
                    .addHeader("content-type","charset=utf-8")
                    .url("http://localhost:8080/v1/DoPayment")
                    .method("POST", requestBody)
                    .build();

            //Async 로 연속 호출
            for(int i=0; i<20; i++) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(" error + Connect Server Error is " + e.toString());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("Response Body is " + response.body().string());
                    }
                });
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


}
