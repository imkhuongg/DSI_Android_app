package com.example.dsidemo.views.MainScreen.shopManage.Searchs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.utils.MySingleton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchProductActivity extends AppCompatActivity {

    private TextInputEditText txt_search;
    private ShapeableImageView btn_search;
    private ImageView btn_back;
    private ListView search_hint;
    public List<String> searchHints;
    private ShopperRepository shopperRepository;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_manage_search_product);

        txt_search = findViewById(R.id.txt_search);
        btn_search = findViewById(R.id.btn_search);
        search_hint = findViewById(R.id.search_hint);
        btn_back = findViewById(R.id.btn_back);

        helper.hideSystemUI(getWindow().getDecorView());

        float radius = 12f;
        searchHints = new ArrayList<>();
        btn_search.setShapeAppearanceModel(btn_search.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED,radius)
                .build()
        );
        txt_search.requestFocus();
        txt_search.postDelayed( () -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm!= null) imm.showSoftInput(txt_search,InputMethodManager.SHOW_IMPLICIT);
        },100);


        txt_search.addTextChangedListener(new TextWatcher() {

            private Timer timer = new Timer();
            private final long DELAY = 10;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = txt_search.getText().toString();
                if(!search.isEmpty()) {
                    btn_search.setClickable(true);
                    btn_search.setBackground(ContextCompat.getDrawable(SearchProductActivity.this, R.color.MainColor));

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            callHint(search);
                        }
                    },DELAY);

                }
                else {

                    arrayAdapter.clear();
                    arrayAdapter.notifyDataSetChanged();
                    btn_search.setClickable(false);
                    btn_search.setBackground(ContextCompat.getDrawable(SearchProductActivity.this, R.color.lightGray));
                };
            }
        });
        arrayAdapter = new ArrayAdapter<>(SearchProductActivity.this, android.R.layout.simple_list_item_1, searchHints);
        search_hint.setAdapter(arrayAdapter);
        btn_back.setOnClickListener(v -> {finish();});

        txt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE ||
                        i == EditorInfo.IME_ACTION_GO ||
                        i == EditorInfo.IME_ACTION_SEND ||
                        (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {

                    if(!txt_search.getText().toString().isEmpty()){
                        GotoSearchResult(txt_search.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GotoSearchResult(txt_search.getText().toString());
            }
        });

        search_hint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GotoSearchResult(search_hint.getItemAtPosition(i).toString());
            }
        });


    }
    public void callHint(String query){


        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
        requestQueue = MySingleton.getInstance(SearchProductActivity.this).getRequestQueue();
        shopperRepository = new ShopperRepository();
        String token = sharedPreferences.getString("token", "");
        String name_product = txt_search.getText().toString();

        if(query.isEmpty()) {
          runOnUiThread( () -> {
              searchHints.clear();
              arrayAdapter.notifyDataSetChanged();
          });

        }

        shopperRepository.getHintList(token, query, requestQueue, new ShopperRepository.ArrayListCallback() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    searchHints = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        searchHints.add(response.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(searchHints);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
    public void GotoSearchResult(String param){
        Intent intent = new Intent(SearchProductActivity.this, ResultsSearchActivity.class);
        intent.putExtra("search",param);
        startActivity(intent);
    }

}

