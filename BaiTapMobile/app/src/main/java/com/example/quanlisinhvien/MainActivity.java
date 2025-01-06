package com.example.quanlisinhvien;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlisinhvien.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    private EditText nhapTen, nhapLop;
    private Button btnThem, btnSua;
    private ListView danhSach;
    private CreateDatabase dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> SinhVientList;
    private int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nhapTen = findViewById(R.id.nhapTen);
        nhapLop = findViewById(R.id.nhapLop);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        danhSach  = findViewById(R.id.danhSach);

        dbHelper = new CreateDatabase(this);
        SinhVientList = new ArrayList<>();

        loadSinhVien();

        btnThem.setOnClickListener(v ->{
            String ten = nhapTen.getText().toString();
            String lop = nhapLop.getText().toString();
            dbHelper.insertSinhVien(ten,lop);
            loadSinhVien();
            clearInputs();
        });

        btnSua.setOnClickListener(v ->{
            if (selectedId != -1){
                String ten = nhapTen.getText().toString();
                String lop = nhapLop.getText().toString();
                dbHelper.updateSinhVien(selectedId,ten,lop);
                loadSinhVien();
                clearInputs();
                btnSua.setEnabled(false);
            }
        });

        danhSach.setOnItemClickListener((parent, view, position, id) -> {
            String item = SinhVientList.get(position);
            String[] parts = item.split("-");
            selectedId = Integer.parseInt(parts[0].trim());
            nhapTen.setText(parts[1].trim());
            nhapLop.setText(parts[2].trim());
            btnSua.setEnabled(true);
        });

        danhSach.setOnItemLongClickListener((parent, view, position, id) -> {
            String item = SinhVientList.get(position);
            String[] parts = item.split("-");
            int idToDelete = Integer.parseInt(parts[0].trim());
            dbHelper.deleteSinhVien(idToDelete);
            loadSinhVien();
            return true;
        });
    }

    private void loadSinhVien(){
        SinhVientList.clear();
        Cursor cursor = dbHelper.getAllSinhVien();
        while (cursor.moveToNext()){
            int id  = cursor.getInt(0);
            String ten = cursor.getString(1);
            String lop = cursor.getString(2);
            SinhVientList.add(id + " - " + ten + " - " + lop);
        }
        cursor.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,SinhVientList);
        danhSach.setAdapter(adapter);
    }

    private void clearInputs(){
        nhapTen.setText("");
        nhapLop.setText("");
        selectedId = -1;
    }
}