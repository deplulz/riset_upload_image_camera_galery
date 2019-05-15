package com.example.itematik.upload_multipart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;

public class upload_image {

    RequestQueue rQue;

    public void result_intent(final Activity activity, final ImageView imageView, final String upload_URL, RequestQueue rQueue, int requestCode, int resultCode, Intent data, int GALLERY, final Context context) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), contentURI);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            new upload_image().uploadImage(bitmap, activity, context, imageView, upload_URL);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 0){
            Toast.makeText(context,"camera",Toast.LENGTH_SHORT).show();
            try {
                final Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        new upload_image().uploadImage(thumbnail, activity, context, imageView, upload_URL);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            }
            //            imageView.setImageBitmap(thumbnail);
        }
    }

    private void uploadImage(final Bitmap bitmap, Activity activity, final Context context, final ImageView imageView, String upload_URL) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo", new String(response.data));
                        rQue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
//                            Toast.makeText(upload_image.context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            jsonObject.toString().replace("\\\\", "");

                            if (jsonObject.getString("status").equals("true")) {

                                ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
                                JSONArray dataArray = jsonObject.getJSONArray("data");

                                String url = "";
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    url = dataobj.optString("file");
                                }
                                Picasso.get().load(url).into(imageView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
//                        Toast.makeText(upload_image.context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("tags", "ccccc");  add string parameters
                return params;
            }

            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Looper.prepare();
                params.put("filename", new DataPart(imagename + ".png", new MainActivity().getFileDataFromDrawable(bitmap)));
                return params;
            }
        };


        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQue = Volley.newRequestQueue(activity);
        rQue.add(volleyMultipartRequest);
    }
}
