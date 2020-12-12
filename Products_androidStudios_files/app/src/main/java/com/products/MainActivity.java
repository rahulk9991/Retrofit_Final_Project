package com.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText tv_name,tv_price, tv_quantity,tv_description;
    Button btn_submit;
    Node loginapi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name = (EditText)findViewById(R.id.tv_name);
        tv_price =(EditText)findViewById(R.id.tv_price);
        tv_quantity = (EditText)findViewById(R.id.tv_quantity);
        tv_description = (EditText)findViewById(R.id.tv_description);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        Retrofit retrofit = RetrofitClient.getApiClient();
        loginapi = retrofit.create(Node.class);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(tv_name.getText().toString(),tv_price.getText().toString(),tv_quantity.getText().toString(),tv_description.getText().toString());
            }
        });
    }


    private void addProduct(String name, String price,String quantity,String description) {


        compositeDisposable.add(loginapi.product(name,price,quantity,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        if (s.contains("Product Added Successfully")){
                            Toast.makeText(MainActivity.this,"Product Added",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);}
                        else
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }));
    }

}