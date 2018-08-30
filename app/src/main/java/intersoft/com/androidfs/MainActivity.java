package intersoft.com.androidfs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import butterknife.ButterKnife;
import intersoft.com.androidfs.Adapter.ListAdapter;

import butterknife.BindView;
import intersoft.com.androidfs.Const.ApparelItems;

public class MainActivity extends AppCompatActivity implements
    AdapterView.OnItemSelectedListener{
    private Spinner brandSpinner;
    private Spinner priceSpinner;
    private Spinner apparelSpinner;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    Query query;
    private ListAdapter mAdapter;
    ArrayList<String>brandList = new ArrayList<>();
    ArrayList<String>priceList = new ArrayList<>();
    ArrayList<String>apparelList = new ArrayList<>();

    @BindView(R.id.list_item_recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        brandSpinner = (Spinner)findViewById(R.id.brandSpinner);
        priceSpinner = (Spinner)findViewById(R.id.priceSpinner);
        apparelSpinner = (Spinner)findViewById(R.id.apparelSpinner);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get ${LIMIT} apparel
        query = mFirestore.collection("apparel");

        // Limit items
        query = query.limit(10);

        // Update the query
        mQuery = query;

        // RecyclerView
        mAdapter = new ListAdapter(MainActivity.this,mQuery) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                /*Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();*/
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        brandSpinner.setOnItemSelectedListener(this);
        if (brandList != null){
            brandList.clear();
        }
        brandList.add("Brand");
        brandList.add("Uniqlo");
        brandList.add("GU");
        brandList.add("H&M");

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,brandList);
        brandSpinner.setAdapter(brandAdapter);

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int position,long id) {
                setmQuery();
            }
            public void onNothingSelected(AdapterView parent) {
            }
        });

        priceSpinner.setOnItemSelectedListener(this);
        if (priceList != null){
            priceList.clear();
        }
        priceList.add("Price");
        priceList.add("<1000");
        priceList.add("<2000");
        priceList.add("<3000");
        priceList.add("<4000");
        priceList.add("<5000");

        ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,priceList);
        priceSpinner.setAdapter(priceAdapter);

        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int position,long id) {
                setmQuery();
            }
            public void onNothingSelected(AdapterView parent) {
            }
        });

        apparelSpinner.setOnItemSelectedListener(this);
        if (apparelList != null){
            apparelList.clear();
        }
        apparelList.add("Seller");
        apparelList.add("Tshirt.ts");
        apparelList.add("Glestore");
        apparelList.add("Pizoff");
        apparelList.add("Sukidaya（スキダヤ）");

        ArrayAdapter<String> apparelAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,apparelList);
        apparelSpinner.setAdapter(apparelAdapter);

        apparelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent,View view, int position,long id) {
                setmQuery();
            }
            public void onNothingSelected(AdapterView parent) {
            }
        });
    }

    public Spinner getBrandSpinner() {
        return brandSpinner;
    }

    public void setBrandSpinner(Spinner brandSpinner) {
        this.brandSpinner = brandSpinner;
    }

    public Spinner getPriceSpinner() {
        return priceSpinner;
    }

    public void setPriceSpinner(Spinner priceSpinner) {
        this.priceSpinner = priceSpinner;
    }

    public Spinner getApparelSpinner() {
        return apparelSpinner;
    }

    public void setApparelSpinner(Spinner apparelSpinner) {
        this.apparelSpinner = apparelSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
    private int getSelectedPrice() {

        if(priceSpinner.getSelectedItem()!= null){
            String selected = (String) priceSpinner.getSelectedItem();
            if (selected.equals("<1000")) {
                return 1000;
            } else if (selected.equals("<2000")) {
                return 2000;
            } else if (selected.equals("<3000")) {
                return 3000;
            } else if (selected.equals("<4000")) {
                return 4000;
            } else if (selected.equals("<5000")) {
                return 5000;
            } else {
                return -1;
            }
        } else{
            return -1;
        }
    }


    private String getSelectedBrand() {

        if(brandSpinner.getSelectedItem()!= null){
            String selected = (String) brandSpinner.getSelectedItem();
            if (selected.equals("Uniqlo")) {
                return "uniqlo";
            } else if (selected.equals("GU")) {
                return "gu";
            } else if (selected.equals("H&M")) {
                return "hm";

            } else {
                return "";
            }
        } else{
            return "";
        }
    }

    private String getSelectedSeller() {

        if(apparelSpinner.getSelectedItem()!= null){
            String selected = (String) apparelSpinner.getSelectedItem();
            if (selected.equals("Tshirt.ts")) {
                return "Tshirt.ts";
            } else if (selected.equals("Glestore")) {
                return "Glestore";
            } else if (selected.equals("Pizoff")) {
                return "Pizoff(ピゾフ)";
            }  else if (selected.equals("Sukidaya（スキダヤ）")) {
                return "Sukidaya（スキダヤ）";
            } else {
                return "";
            }
        } else{
            return "";
        }
    }

    private void setmQuery() {
        Filters filters = new Filters();
        filters.setBrand(getSelectedBrand());
        filters.setPrice(getSelectedPrice());
        filters.setSeller(getSelectedSeller());

        query = mFirestore.collection("apparel");

        // Brand (equality filter)
        if (filters.hasBrand()) {
            query = query.whereEqualTo(ApparelItems.FIELD_BRAND, filters.getBrand());
        }
        // Price (equality filter)
        if (filters.hasPrice()) {
            query = query.whereLessThan(ApparelItems.FIELD_PRICE, filters.getPrice());
        }
        // Seller (equality filter)
        if (filters.hasSeller()) {
            query = query.whereEqualTo(ApparelItems.FIELD_SELLER, filters.getSeller());
        }
        mQuery = query;
        mAdapter.setQuery(mQuery);
        mAdapter.notifyDataSetChanged();

    }
}
