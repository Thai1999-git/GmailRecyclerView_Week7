package vn.hust.edu.gmailrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.hust.edu.gmailrecyclerview.Adapters.GmailItemAdapter;
import vn.hust.edu.gmailrecyclerview.Models.GmailItemModel;

public class MainActivity extends AppCompatActivity {

    List<GmailItemModel> items;
    GmailItemAdapter adapter;
    boolean isShowingFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        //for (int i = 0; i < 10; i++)
        items.add(new GmailItemModel("Hello World", "Subject subject", "Content content","5.00 pm" ));
        items.add(new GmailItemModel("Monday", "Subject subject", "Content content","6.00 pm" ));
        items.add(new GmailItemModel("Tuesday", "Subject subject", "Content content","7.00 pm" ));
        items.add(new GmailItemModel("Wednesday", "Subject subject", "Content content","5.00 am" ));
        items.add(new GmailItemModel("Thursday", "Subject subject", "Content content","9.00 pm" ));
        items.add(new GmailItemModel("Friday", "Subject subject", "Content content","10.00 pm" ));
        items.add(new GmailItemModel("Saturday", "Subject subject", "Content content","8.00 pm" ));
        items.add(new GmailItemModel("Sunday", "Subject subject", "Content content","11.00 pm" ));
        items.add(new GmailItemModel("Yesterday", "Subject subject", "Content content","12.00 pm" ));
        items.add(new GmailItemModel("Today", "Subject subject", "Content content","6.00 am" ));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GmailItemAdapter(items);
        recyclerView.setAdapter(adapter);
        isShowingFavorite = false;

        findViewById(R.id.btn_fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowingFavorite = !isShowingFavorite;
                if(isShowingFavorite)
                    adapter.showFavorite();
                else
                    adapter.showAll();
            }
        });

        EditText editEnter = findViewById(R.id.edit_enter);
        editEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edittable) {
                String keyword = edittable.toString();
                if (keyword.length() > 2)
                    adapter.search(keyword);
                else
                    adapter.showAll();
            }
        });
    }
}
