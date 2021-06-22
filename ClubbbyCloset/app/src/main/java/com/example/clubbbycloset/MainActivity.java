package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_ALLUSERS = "allUsersData.txt";
    private String users = "ginger:profilo1;imgSrc:gin3;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin5;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin6;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin7;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin8;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin9;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin10;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin11;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "ginger:profilo1;imgSrc:gin12;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "giulia_:profilo2;imgSrc:giu5;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu6;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu7;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu8;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu9;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu10;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu11;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giulia_:profilo2;imgSrc:giu12;descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" +
            "giuseppe05:profilo3;imgSrc:peppe1;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe2;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe3;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe4;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe5;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe6;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "giuseppe05:profilo3;imgSrc:peppe7;descrizione:provadescri2:provalocatio2:provaora2:provalink2;;" +
            "gloria.z:profilo1;imgSrc:glo3;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo4;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo5;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo6;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo7;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo8;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "gloria.z:profilo1;imgSrc:glo9;descrizione:provadescri3:provalocatio3:provaora3:provalink3;;" +
            "manuelito:profilo3;imgSrc:manu5;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu6;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu7;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu8;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu9;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu10;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu11;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu12;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "manuelito:profilo3;imgSrc:manu13;descrizione:provadescri4:provalocatio4:provaora4:provalink4;;" +
            "willB:profilo3;imgSrc:will3;descrizione:provadescri5:provalocatio5:provaora5:provalink5;;" +
            "willB:profilo3;imgSrc:will4;descrizione:provadescri5:provalocatio5:provaora5:provalink5;;" +
            "willB:profilo3;imgSrc:will5;descrizione:provadescri5:provalocatio5:provaora5:provalink5;;" +
            "willB:profilo3;imgSrc:will6;descrizione:provadescri5:provalocatio5:provaora5:provalink5;;" +
            "willB:profilo3;imgSrc:will7;descrizione:provadescri5:provalocatio5:provaora5:provalink5;;";

    private static final String FILE_TOPICS = "topics.txt";
    private String topics = "topic:Special Occasion:evento:wedding:party:pool:birthday:cerimony:graduation;" +
            "imgSrc:glo5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe4:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +

            ";;topic:Sport Vibes:sport:run:corsa:passeggiata:workout:walking:friends:gym;" +
            "imgSrc:will7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu13:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu11:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +

            ";;topic:Happy Hour:ape:aperitivo:friends:appointment:cocktail:beer:party;" +
            "imgSrc:will3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe1:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu11:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin11:descrizione:provadescri1:provalocatio1:provaora1:provalink1" +

            ";;topic:Back to school:scuola:liceo:college:university:library:caffee;" +
            "imgSrc:will3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu8descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo4:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin3:descrizione:provadescri1:provalocatio1:provaora1:provalink1" +

            ";;topic:Dinner Night:appointment:friends:restourant:party:night;" +
            "imgSrc:will7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe2:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin11:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin10:descrizione:provadescri1:provalocatio1:provaora1:provalink1" +


            ";;topic:Working time:work:formal:job:office:interview:smart;" +
            "imgSrc:manu8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo4:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe2:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe1:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu11:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin6:descrizione:provadescri1:provalocatio1:provaora1:provalink1" +

            ";;topic:On journay:sportivo:vacation:trip:sea:lake:mountain:friends:family;"+
            "imgSrc:will3:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:glo4:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:peppe1:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu8:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin12:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin7:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin3:descrizione:provadescri1:provalocatio1:provaora1:provalink1" +

            ";;topic:Summertime:bikini:beach:sea:surf:friends:family:sun:sunny;"+
            "imgSrc:will5:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:will6:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:manu2:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu10:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:giu9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
            "imgSrc:gin11:descrizione:provadescri1:provalocatio1:provaora1:provalink1;" +
             "imgSrc:gin9:descrizione:provadescri1:provalocatio1:provaora1:provalink1;;" ;

    private  static final String FILE_ALLVOTE = "allVote.txt";
    private String votes = "username:ginger;voteSrc:gin1:gin2;description:provasedcrizione:provalovation:provaorario;vote:0:0;;" +
            "username:ginger;voteSrc:gin3:gin4;description:provasedcrizione:provalovation:provaorario;vote:0:0;;" +
            "username:giulia_;voteSrc:giu1:giu2;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:giulia_;voteSrc:giu3:giu4;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:gloria.z;voteSrc:glo1:glo2;description:provasedcrizione2:provalovation2:provaorario2;vote:0:0;;" +
            "username:manuelito;voteSrc:manu1:manu2;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;" +
            "username:manuelito;voteSrc:manu3:manu4;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;" +
            "username:willB;voteSrc:will1:will2;description:provasedcrizione4:provalovation4:provaorario4;vote:10:3;;" ;

    private static final String FILE_USER = "userdata.txt";
    Button b1;
    TextView b2,forgot;
    EditText ed1,ed2;
    CheckBox show_hide_password;
    ImageView info ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.btnlogin);
        ed1 = (EditText)findViewById(R.id.et_email);
        ed2 = (EditText)findViewById(R.id.et_password);
        b2 = (TextView) findViewById(R.id.newAccount);
        forgot = (TextView) findViewById(R.id.forgot);
        info = (ImageView)findViewById(R.id.info);
        show_hide_password = (CheckBox) this.findViewById(R.id.show_hide_password);

        try {
            saveFile(users, FILE_ALLUSERS);
            save(topics, FILE_TOPICS);
            saveFile(votes, FILE_ALLVOTE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ret = load(FILE_USER);
                String name= null;
                String psw= null;
                int ok = -1;
                if (ret!=null){
                    String[] t = ret.split(";;");
                    for(int i =0; i<t.length; i++) {
                        name = t[i].split(";")[0].split(":")[1];
                        psw = t[i].split(";")[1].split(":")[1];
                        if (name.equals(ed1.getText().toString()) && psw.equals(ed2.getText().toString())) {
                            ok = 1;
                            Intent home = new Intent(MainActivity.this, home.class);
                            home.putExtra("idProfile", name);
                            startActivity(home); // takes the user to the signup activity
                        }
                    }
                    if(ok < 1) {
                        Toast.makeText(getApplicationContext(), "WrongCredentials", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "No Valid account please SIGN UP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, signup.class);
                startActivity(signup); // takes the user to the signup activity
            }

        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_forgot, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_info, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

            }
        });

        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    ed2.setInputType(InputType.TYPE_CLASS_TEXT);
                    ed2.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    ed2.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ed2.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });

    }

    public void saveFile(String txt,String FILE_NAME) throws IOException {
        String res = load(FILE_NAME);
        if (res == null){
            save(txt, FILE_NAME);
        }else {
            save(res,FILE_NAME);
        }
    }
    public void save(String text, String FILE_NAME) throws IOException {
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(text.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto "+text, Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load(String FILE_NAME) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}