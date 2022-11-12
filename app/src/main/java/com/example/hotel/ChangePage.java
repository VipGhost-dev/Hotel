package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePage extends AppCompatActivity {

    private EditText inRoom;
    private EditText inCountPeoples;
    private CheckBox bStatus;
    private Button btnChange;
    private Button btnDelete;
    private String status;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_page);

        inRoom = findViewById(R.id.RoomInput);
        inCountPeoples =findViewById(R.id.CountPeopleInput);
        bStatus = findViewById(R.id.BoxStatus);
        btnChange = findViewById(R.id.btn_change);
        btnDelete = findViewById(R.id.btn_delete);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ЛедровЕИ/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<DataStorage> call = retrofitAPI.getData(MainActivity.index);
        call.enqueue(new Callback<DataStorage>() {
            @Override
            public void onResponse(Call<DataStorage> call, Response<DataStorage> response) {
                id = response.body().getID();
                inRoom.setText(Integer.valueOf(response.body().getRoom()).toString());
                inCountPeoples.setText(Integer.valueOf(response.body().getCount_Peoples()).toString());
                status = response.body().getStatus();
                if (status.equals("Занято")){
                    bStatus.setChecked(true);
                }
                else {
                    bStatus.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<DataStorage> call, Throwable t) {

            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inRoom.getText().toString().isEmpty() && inCountPeoples.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePage.this, "Все текстовые поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                putData(inRoom.getText().toString(), inCountPeoples.getText().toString(),bStatus.isChecked());
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }
    private void putData(String room, String countPeoples, boolean check) {
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
        DataStorage dataStorage = new DataStorage(id,Integer.parseInt(room), Integer.parseInt(countPeoples), status);

        // calling a method to create a post and passing our modal class.
        Call<DataStorage> call = retrofitAPI.updateData(id,dataStorage);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataStorage>() {
            @Override
            public void onResponse(Call<DataStorage> call, Response<DataStorage> response) {
                // this method is called when we get response from our api.
                Toast.makeText(ChangePage.this, "Запись изменена", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePage.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<DataStorage> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(ChangePage.this, "Ошибка изменения", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData() {
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

        // calling a method to create a post and passing our modal class.
        Call call = retrofitAPI.deleteData(id);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataStorage>() {
            @Override
            public void onResponse(Call<DataStorage> call, Response<DataStorage> response) {
                // this method is called when we get response from our api.
                Toast.makeText(ChangePage.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePage.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<DataStorage> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(ChangePage.this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
            }
        });

    }
}