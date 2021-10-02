package com.nbg.mobify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.nbg.mobify.databinding.ActivityInformationBinding;
import com.nbg.mobify.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    int DIALOG_GAME_RESTART=100;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(DIALOG_GAME_RESTART).show();
            }
        });



    }


    public String getMemoryInfo() {
        ProcessBuilder cmd;
        String result="";

        try {
            String[] args = {"/system/bin/cat", "/proc/meminfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private String ReadCPUinfo()
    {
        ProcessBuilder cmd;
        String result="";

        try{
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                System.out.println(new String(re));
                result = result + new String(re);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public int getNumberOfCores() {
        if(Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }else {
            //Code for old SDK values
            return 0;
            //return Runtime.getRuntime().availableProcessors();
        }
    }
    /*
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }
    */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 100:
                Context context=MainActivity.this;
                ActivityInformationBinding info=ActivityInformationBinding.inflate(getLayoutInflater());
                dialog=new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(info.getRoot());




                info.model.setText(String.valueOf(Build.MODEL));

                info.board.setText(String.valueOf(Build.BOARD));

                info.brand.setText(String.valueOf(Build.BRAND));

                info.manufacturer.setText(String.valueOf(Build.MANUFACTURER));

                info.device.setText(String.valueOf(Build.DEVICE));

                info.product.setText(String.valueOf(Build.PRODUCT));

                info.tags.setText(String.valueOf(Build.TAGS));

                info.serial.setText(String.valueOf(Build.SERIAL));

                info.hardware.setText(String.valueOf(Build.HARDWARE));


                info.cores.setText(String.valueOf(getNumberOfCores()));

                String arch = System.getProperty("os.arch");
                info.architecture.setText(arch);

                info.cpu.setText(String.valueOf(ReadCPUinfo()));



                info.memory.setText(String.valueOf(getMemoryInfo()));

                info.realease.setText(String.valueOf(Build.VERSION.RELEASE));

                       if(String.valueOf( Build.VERSION.BASE_OS)=="")
                           info.os.setText("Not Found");
                       else
                info.os.setText(String.valueOf( Build.VERSION.BASE_OS) );

                info.codeName.setText(String.valueOf(Build.VERSION.CODENAME) );

                info.securityPatch.setText(String.valueOf(Build.VERSION.SECURITY_PATCH));


                info.sdkApi.setText(String.valueOf(Build.VERSION.SDK_INT));

                info.displayBuild.setText(String.valueOf(Build.DISPLAY));

                info.fingerprint.setText(String.valueOf(Build.FINGERPRINT));

                    info.buildId.setText(String.valueOf(Build.ID));

                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
                String date = sdf.format(Build.TIME);


                info.date.setText(date);

                info.type.setText(String.valueOf(Build.TYPE));

                info.User.setText(String.valueOf(Build.USER));
                info.bootLoader.setText(String.valueOf(Build.BOOTLOADER));

                info.kernelVersion.setText(String.valueOf(System.getProperty("os.version")));


                info.radioVersion.setText(String.valueOf(Build.getRadioVersion()));

                //tv.append(pInfo.installLocation);
                //tv.append(getVMVersion());



                info.restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        //whatever code you want to execute on restart
                    }
                });
                break;

            default: break;
        }
        return dialog;
    }
}