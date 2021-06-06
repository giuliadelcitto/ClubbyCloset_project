package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class vote extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile, imgLeft1, imgRigth1, imgLeft2, imgRigth2;;
    TextView username1, description1,  username2, description2, vleft1,vright1;
    LinearLayout lbar1, rbar1;


    private  static final String FILE_ALLVOTE = "allVote.txt";
    private static final String FILE_USERVOTE ="uservote.txt";
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static final String FILE_USERIMG = "userimg.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ArrayList<Uri> mArrayUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        imgLeft1 = (ImageView) this.findViewById(R.id.left1);
        imgRigth1 = (ImageView) this.findViewById(R.id.right1);
        imgLeft2 = (ImageView) this.findViewById(R.id.left2);
        imgRigth2 = (ImageView) this.findViewById(R.id.right2);
        username1 = (TextView) this.findViewById(R.id.username1);
        username2 = (TextView) this.findViewById(R.id.username2);
        description1 = (TextView) this.findViewById(R.id.descrizione1);
        description2 = (TextView) this.findViewById(R.id.descrizione2);
        vleft1= (TextView) this.findViewById(R.id.tvleft1);
        vright1= (TextView) this.findViewById(R.id.tvright1);
        lbar1 = (LinearLayout)this.findViewById(R.id.leftbar1);
        rbar1 = (LinearLayout) this.findViewById(R.id.rightbar1);

        ImageView[] imgs = {imgLeft1,imgRigth1,imgLeft2,imgRigth2};
        TextView[] text = {username1,description1,username2,description2};
        TextView[] vtxt = {vleft1,vright1};
        LinearLayout[] vbars = {lbar1,rbar1};


        setVote(FILE_ALLVOTE, imgs, text, vtxt, vbars);

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(vote.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(vote.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(vote.this, profile.class);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(vote.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(vote.this, badd);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(home.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Add new img")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        } if (item.getTitle().equals("Add new vote")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(i, RESULT_LOAD_VOTE);
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

        username1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilo = new Intent(vote.this, usersProfile.class);
                profilo.putExtra("user", username1.getText());
                startActivity(profilo);
            }
        });

        username2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilo = new Intent(vote.this, usersProfile.class);
                profilo.putExtra("user", username2.getText());
                startActivity(profilo);
            }
        });

    }

    private void setVote(String fileAllvote, ImageView[] imgs, TextView[] text, TextView[] vtxt, LinearLayout[] vbars) {
        String[] res = load(fileAllvote).split(";;");
        //
        int j = 0;
        for (int i = 0; i <res.length; i++){
            String[] r = res[i].split(";");
            String username = r[0].split(":")[1];
            String[] imgSrc = r[1].split((":"));
            String[] desc = r[2].split(":");
            String[] vote = r[3].split(":");

            int id = getResources().getIdentifier(imgSrc[1],"drawable", "com.example.clubbbycloset");
            imgs[j].setBackgroundResource(id);
            id = getResources().getIdentifier(imgSrc[2],"drawable", "com.example.clubbbycloset");
            imgs[j+1].setBackgroundResource(id);

            text[j].setText(username);
            text[j+1].setText(desc[1] + "\n" + desc[1] + "\n" + desc[3] + "\n");

            int finalJ = j;
            int finalI = i;
            imgs[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveVote(fileAllvote, finalI, finalJ);

                    String[] res = load(fileAllvote).split(";;");
                    String[] r = res[finalI].split(";");
                    String[] vote = r[3].split(":");
                    int[] vot = {Integer.parseInt(vote[1]),Integer.parseInt(vote[2])};
                    setVoteBar(vot[0], vot[1], vtxt,vbars, finalJ);
                }
            });

            imgs[j+1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveVote(fileAllvote, finalI, finalJ+1);

                    String[] res = load(fileAllvote).split(";;");
                    String[] r = res[finalI].split(";");
                    String[] vote = r[3].split(":");
                    int[] vot = {Integer.parseInt(vote[1]),Integer.parseInt(vote[2])};
                    setVoteBar(vot[0], vot[1], vtxt,vbars, finalJ);
                }
            });

            j= j+2;
        }
    }

    public void setVoteBar(int lvote, int rvote, TextView[] vtxt, LinearLayout[] vbars, int j){
        int tot = lvote+rvote;
        int l = (lvote*100)/tot;
        int r = (rvote*100)/tot;
        vtxt[j].setText(Integer.toString(l)+ "%");
        vtxt[j+1].setText(Integer.toString(r)+ "%");
        int w =((l*100)/400);
        //Toast.makeText(vote.this,"w1: " + w + "l  " + l , Toast.LENGTH_SHORT).show();
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams(w*50, LinearLayout.LayoutParams.WRAP_CONTENT);
        vbars[j].setLayoutParams(lp);


        w =((r*100)/400);
        //Toast.makeText(vote.this,"W2: " + w + "r   "+ r, Toast.LENGTH_SHORT).show();
        lp = new  LinearLayout.LayoutParams(w*50, LinearLayout.LayoutParams.WRAP_CONTENT);
        vbars[j+1].setLayoutParams(lp);
        if(l > 50 ){
            vbars[j].setBackgroundResource(R.color.purple);
            vbars[j+1].setBackgroundResource(R.color.litePurple);
        }else{
            vbars[j+1].setBackgroundResource(R.color.purple);
            vbars[j].setBackgroundResource(R.color.litePurple);
        }
        vbars[j].setVisibility(View.VISIBLE);
        vbars[j+1].setVisibility(View.VISIBLE);
    }

    public void saveVote(String FILE_NAME, int i, int j){
        String[] res = load(FILE_NAME).split(";;");
        String[] r = res[i].split(";");

        String username = r[0].split(":")[1];
        String[] vote = r[3].split(":");
        int[] v = null;
        String toAdd;
        if (j%2 == 0) {
            int vo = Integer.parseInt(vote[1]) + 1;
            String[] file = load(FILE_NAME).split(username);
            toAdd = file[0] + username + ";" + r[1] + ";" + r[2] + ";" + vote[0] + ":" + vo + ":" + vote[2] + ";;";
            if (file[1].split(";;").length > 1){
                toAdd = toAdd + file[1].split(";;")[1];
            }
        }else{
            int vo = Integer.parseInt(vote[2]) + 1;
            String[] file = load(FILE_NAME).split(username);
            toAdd = file[0] + username + ";" + r[1] + ";" + r[2] + ";" + vote[0] + ":" + vote[1] + ":" + vo + ";;";
            if (file[1].split(";;").length > 1){
                toAdd = toAdd + file[1].split(";;")[1];
            }
        }

        try{
            //Toast.makeText(vote.this,"ADD: " + toAdd , Toast.LENGTH_SHORT).show();
            save(FILE_ALLVOTE, toAdd);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void save(String FILE_NAME, String text) throws IOException {
        //Toast.makeText(getApplicationContext(), "IN SAVE " + text, Toast.LENGTH_SHORT).show();
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(text.getBytes());
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE) {
            newImg(requestCode,resultCode,data);
        }else if(requestCode == RESULT_LOAD_VOTE) {
            try {
                newVote(requestCode,resultCode,data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newVote(int requestCode, int resultCode, Intent data) throws IOException {
        if (resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                String paths = "";
                int cout = data.getClipData().getItemCount();
                //Toast.makeText(getApplicationContext(), "SIZE  " + cout,Toast.LENGTH_SHORT).show();
                if(cout <= 4) {
                    for (int i = 0; i < cout; i++) {
                        // adding imageuri in array
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        paths = paths + picturePath + ":";
                        //Toast.makeText(getApplicationContext(), "LETTO  " + paths,Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                    loadVoteImg(paths, FILE_USERVOTE);
                    Intent voteView = new Intent(vote.this, voteView.class);
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

        }
    }

    private void loadVoteImg(String picPath, String FILE_NAME) throws IOException {
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
        String t = load(FILE_NAME) + ";voteSrc:" + picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void newImg(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            try {
                changeProfileImg(picturePath, FILE_USERIMG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
        }
    }

    //to give the permission for load img
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void changeProfileImg(String picPath, String FILE_NAME) throws IOException {
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
        String t =load(FILE_NAME)+":"+picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}