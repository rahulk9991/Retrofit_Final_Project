package com.products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewProductsActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listView;
    List<GetAllProductsPojo> ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        listView=findViewById(R.id.list);
        Retrofit retrofit = RetrofitClient.getApiClient();
        Node node = retrofit.create(Node.class);
        Call<List<GetAllProductsPojo>> call = node.getOrders();

        call.enqueue(new Callback<List<GetAllProductsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllProductsPojo>> call, Response<List<GetAllProductsPojo>> response) {
                if ((!response.isSuccessful())){
                    textView.setText("Code:"+response.code());
                    return;
                }

                ps = response.body();
                listadapter li=new listadapter();
                listView.setAdapter(li);

            }

            @Override
            public void onFailure(Call<List<GetAllProductsPojo>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }

    public class listadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ps.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v=getLayoutInflater().from(ViewProductsActivity.this).inflate(R.layout.productlist,viewGroup,false);
            TextView tvname=v.findViewById(R.id.tvname);
            TextView tvprice=v.findViewById(R.id.tvprice);
            TextView tvquantity=v.findViewById(R.id.tvquantity);
            tvname.setText(ps.get(i).getName());
            tvprice.setText(ps.get(i).getPrice()+"$");
            tvquantity.setText(ps.get(i).getQuantity());
            return v;
        }
    }
}