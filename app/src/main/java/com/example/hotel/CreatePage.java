package com.example.hotel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

public class CreatePage extends AppCompatActivity {

    private EditText inRoom;
    private EditText inCountPeoples;
    private CheckBox bStatus;
    private Button btnCreate;
    private String status;
    private ImageView image;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

        inRoom = findViewById(R.id.RoomInput);
        inCountPeoples =findViewById(R.id.CountPeopleInput);
        bStatus = findViewById(R.id.BoxStatus);
        btnCreate = findViewById(R.id.btn_create);
        image = findViewById(R.id.img);

        image.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImg.launch(intent);        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inRoom.getText().toString().isEmpty() && inCountPeoples.getText().toString().isEmpty()) {
                    Toast.makeText(CreatePage.this, "Все текстовые поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postData(inRoom.getText().toString(), inCountPeoples.getText().toString(),bStatus.isChecked());
            }
        });
    }
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    image.setImageBitmap(bitmap);
                    encodedImage = encodeImage(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });
    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    private void postData(String room, String countPeoples, boolean check) {
        if(check){
            status = "Занято";
        }
        else{
            status = "Свободно";
        }
        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ЛедровЕИ/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataStorage dataStorage = new DataStorage(Integer.parseInt(room), Integer.parseInt(countPeoples), status, encodedImage);

        // calling a method to create a post and passing our modal class.
        Call<DataStorage> call = retrofitAPI.createPost(dataStorage);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataStorage>() {
            @Override
            public void onResponse(Call<DataStorage> call, Response<DataStorage> response) {
                // this method is called when we get response from our api.
                Toast.makeText(CreatePage.this, "Запись добавлена", Toast.LENGTH_SHORT).show();

                // on below line we are setting empty text
                // to our both edit text.
                inRoom.setText("");
                inCountPeoples.setText("");
                bStatus.setChecked(false);

                startActivity(new Intent(CreatePage.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<DataStorage> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(CreatePage.this, "Ошибка добавления", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void  gotoMain(View v){startActivity(new Intent(this,MainActivity.class)); finish();}
}