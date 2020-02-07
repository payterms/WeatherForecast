package ru.payts.weatherforecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static android.provider.Telephony.Mms.Part.TEXT;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MenuListAdapter adapter = null;
    private EditText searchEditText;

    ViewGroup root;
    String currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentCity = new CityPreference(this).getCity();;
        initViews();
        initList();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //setOnFloatingBtnClick();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WeatherFragment())
                    .commit();
        }
        root = findViewById(R.id.container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.weather, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onContextItemSelected(item);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //searchEditText = findViewById(R.id.searchEditText);
    }

    private void initList() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Tula");
        data.add("Orel");
        adapter = new MenuListAdapter(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(adapter);
    }


    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_add: {
                adapter.addItem(currentCity);
                break;
            }
            case R.id.menu_search: {
                showInputDialog();
                break;
            }
            case R.id.menu_edit: {
                showInputDialog();
                adapter.editItem(currentCity);
                break;
            }
            case R.id.menu_remove: {
                adapter.removeElement();
                break;
            }
            case R.id.menu_clear: {
                adapter.clearList();
                break;
            }
            default: {
                if(id != R.id.menu_more) {
                    Toast.makeText(getApplicationContext(), getString(R.string.action_not_found),
                            Toast.LENGTH_SHORT).show();
                }
            }

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.changecity));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(getString(R.string.gobutton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString(), Locale.getDefault().getLanguage());
            }
        });
        builder.show();
    }

    public void changeCity(String city, String lang) {
        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        wf.changeCity(city, lang);
        currentCity = city;
        new CityPreference(this).setCity(city);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        System.out.println("Повторный запуск!! - onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        System.out.println("onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy()");
    }

}

