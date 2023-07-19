package com.msaggik.androidpermissionexample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    // поля
    private ActivityResultLauncher<String[]> storagePermissionLauncher; // для нескольких разрешений
    private final String [] PERMISSION = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}; // разрешения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerPermissionA();
        checkPermission();
    }

    // метод проверки разрешений
    private void checkPermission() {
        // проверка разрешений
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Данны разрешения на чтение аудио-файлов и демонстрацию эквалайзера", Toast.LENGTH_SHORT).show();
        } else { // запрос нескольких разрешений
            storagePermissionLauncher.launch(PERMISSION);
        }
    }

    // регистрация разрешений
    private void registerPermissionA() {
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                result -> result.forEach(
                        (permission, granted) -> {
                            switch (permission) {
                                case Manifest.permission.READ_EXTERNAL_STORAGE:
                                    if (granted) {
                                        Toast.makeText(this, "Разрешение дано к аудиофайлам", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Доступ к аудиофайлам запрещён", Toast.LENGTH_SHORT).show();
                                    } break;
                                case Manifest.permission.RECORD_AUDIO:
                                    if (granted) {
                                        Toast.makeText(this, "Разрешение дано к демонстрации эквалайзера", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Доступ к демонстрации эквалайзера запрещён", Toast.LENGTH_SHORT).show();
                                    } break;
                            }
                        }));
    }

    // регистрация разрешений
    private void registerPermissionB() {
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (result.containsValue(true)) { // если разрешение дано
                Toast.makeText(this, "Разрешение дано к аудиофайлам и демонстрации эквалайзера даны", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Разрешения доступа к аудиофайлам и демонстрации эквалайзера отсутствуют", Toast.LENGTH_SHORT).show();
            }
        });
    }
}