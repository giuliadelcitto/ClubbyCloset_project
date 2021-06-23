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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
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
    ImageView bhome, bsearch, badd, bvote, bprofile, imgLeft, imgRigth;
    TextView tvusername, tvdescription,tvlocation,tvtime, vleft, vright, edesc, elocation, etime;
    LinearLayout lbar,rbar;
    LinearLayout scrollView, voteBarLay;

    public static String id;

    private  static final String FILE_ALLVOTE = "allVote.txt";
    private static final String FILE_USERVOTE ="uservote.txt";
    private static final String FILE_ALLUSERS = "allUsersData.txt";

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static final String FILE_USER = "userdata.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Bundle Extra = getIntent().getExtras();
        id = Extra.getString("idProfile");

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        scrollView = (LinearLayout) this.findViewById(R.id.scroll);

        setLayout(FILE_ALLVOTE,scrollView);

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(vote.this, home.class);
                home.putExtra("idProfile", id);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(vote.this, search.class);
                search.putExtra("idProfile", id);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(vote.this, profile.class);
                profile.putExtra("type", "0");
                profile.putExtra("idProfile", id);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(vote.this, vote.class);
                vote.putExtra("idProfile", id);
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
    }

    private void setLayout(String fileAllvote, LinearLayout scrollView) {
        String[] res = load(fileAllvote).split(";;");

        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<res.length;i++){
            String[] r = res[i].split(";");
            String username = r[0].split(":")[0];
            String userProfileImageSrc = r[0].split(":")[1];
            String[] imgSrc = r[1].split((":"));
            String[] desc = r[2].split(":");
            String[] vote = r[3].split(":");

            View view = inflater.inflate(R.layout.vote_frame, null);

            imgLeft = (ImageView) view.findViewById(R.id.left1);
            imgRigth = (ImageView) view.findViewById(R.id.right1);
            voteBarLay = (LinearLayout) view.findViewById(R.id.voteBarlayout);

            if(imgSrc[1].contains("storage")){
                Bitmap bml = BitmapFactory.decodeFile(imgSrc[1]);
                Bitmap bmr = BitmapFactory.decodeFile(imgSrc[2]);
                Bitmap rotatedBitmap = null;
                try {
                    rotatedBitmap = rotateImage(imgSrc[1], bml);
                    imgLeft.setImageBitmap(rotatedBitmap);
                    rotatedBitmap = rotateImage(imgSrc[2], bmr);
                    imgRigth.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                int id = getResources().getIdentifier(imgSrc[1],"drawable", "com.example.clubbbycloset");
                imgLeft.setBackgroundResource(id);
                id = getResources().getIdentifier(imgSrc[2],"drawable", "com.example.clubbbycloset");
                imgRigth.setBackgroundResource(id);
            }

            ImageView userProfileImage = (ImageView)view.findViewById(R.id.userProfileImg);
            if (userProfileImageSrc.contains("storage")) {

                Bitmap bml = BitmapFactory.decodeFile(userProfileImageSrc);
                Bitmap resized = Bitmap.createScaledBitmap(bml, 200, 200, false);
                Bitmap conv_bm = null;
                try {
                    conv_bm = conv_bm = getRoundedRectBitmap(rotateImage(userProfileImageSrc, resized), 200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                userProfileImage.setImageBitmap(conv_bm);
            } else {
                int id = getResources().getIdentifier(userProfileImageSrc, "drawable", "com.example.clubbbycloset");
                Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
                Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                Bitmap conv_bm = getRoundedRectBitmap(resized, 200);
                userProfileImage.setImageBitmap(conv_bm);
            }

            edesc = (TextView)view.findViewById(R.id.edesc);
            edesc.setText(new String(Character.toChars(0x1F4F7)));

            elocation = (TextView)view.findViewById(R.id.elocation);
            elocation.setText(new String(Character.toChars(0x1F4CD)));

            etime = (TextView)view.findViewById(R.id.etime);
            etime.setText(new String(Character.toChars(0x1F552)));



            tvdescription = (TextView)view.findViewById(R.id.description);
            tvlocation = (TextView)view.findViewById(R.id.location);
            tvtime = (TextView)view.findViewById(R.id.time);
            TextView[] arr= {tvdescription,tvlocation,tvtime};
            for(int k= 1 ; k<desc.length ; k++){
                arr[k-1].setText(desc[k]);
            }
            tvusername = (TextView)view.findViewById(R.id.username1);
            tvusername.setText(username);

            tvusername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profilo = new Intent(vote.this, usersProfile.class);
                    profilo.putExtra("user", username);
                    profilo.putExtra("idProfile", id);
                    profilo.putExtra("type", "0");
                    startActivity(profilo);
                }
            });

            lbar = (LinearLayout)view.findViewById(R.id.leftbar);
            rbar = (LinearLayout) view.findViewById(R.id.rightbar);
            vleft = (TextView)view.findViewById(R.id.tvleft);
            vright = (TextView)view.findViewById(R.id.tvright);

            TextView[] vtxt = {vleft,vright};

            LinearLayout[] vbars = {lbar,rbar};

            int finalI = i;
            if (! username.equals(id) ){
                imgRigth.setClickable(true);
                imgLeft.setClickable(true);

                imgLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveVote(fileAllvote, finalI, 0);

                        String[] res = load(fileAllvote).split(";;");
                        String[] r = res[finalI].split(";");
                        String[] vote = r[3].split(":");
                        int[] vot = {Integer.parseInt(vote[1]), Integer.parseInt(vote[2])};
                        setVoteBar(vot[0], vot[1], vtxt, vbars);
                        notClick(imgLeft,imgRigth);
                    }
                });
                imgRigth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveVote(fileAllvote, finalI, 1);

                        String[] res = load(fileAllvote).split(";;");
                        String[] r = res[finalI].split(";");
                        String[] vote = r[3].split(":");
                        int[] vot = {Integer.parseInt(vote[1]), Integer.parseInt(vote[2])};
                        setVoteBar(vot[0], vot[1], vtxt, vbars);
                        notClick(imgLeft,imgRigth);
                    }
                });
            }
            scrollView.addView(view);
        }
    }

    public void notClick(ImageView r, ImageView l){
        r.setClickable(false);
        l.setClickable(false);
    }

    public void setVoteBar(int lvote, int rvote, TextView[] vtxt, LinearLayout[] vbars){
        int tot = lvote+rvote;
        int l = (lvote*100)/tot;
        int r = (rvote*100)/tot;
        vtxt[0].setText(Integer.toString(l)+ "%");
        vtxt[1].setText(Integer.toString(r)+ "%");

        //Toast.makeText(vote.this,"vtxt len  " + vtxt[0].getText()  + "  " + vtxt[1].getText(), Toast.LENGTH_SHORT).show();
        int w =((l*100)/450);
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams(w*50, LinearLayout.LayoutParams.WRAP_CONTENT);
        vbars[0].setLayoutParams(lp);
        w =((r*100)/450) ;
        lp = new  LinearLayout.LayoutParams(w*50, LinearLayout.LayoutParams.WRAP_CONTENT);
        vbars[1].setLayoutParams(lp);
        if(l > 50 ){
            vbars[0].setBackgroundResource(R.color.purple);
            vbars[1].setBackgroundResource(R.color.litePurple);
        }else{
            vbars[1].setBackgroundResource(R.color.purple);
            vbars[0].setBackgroundResource(R.color.litePurple);
        }
        voteBarLay.setVisibility(View.VISIBLE);
        vbars[0].setVisibility(View.VISIBLE);
        vbars[1].setVisibility(View.VISIBLE);
    }

    public void saveVote(String FILE_NAME, int i, int j){
        String[] res = load(FILE_NAME).split(";;");
        String[] r = res[i].split(";");
        String[] vote = r[3].split(":");
        int[] v = null;
        String toAdd = "";
        for (int z=0; z<i; z++){
            toAdd = toAdd + res[z] + ";;";
        }
        if (j == 0) {
            int vo = Integer.parseInt(vote[1]) + 1;
            toAdd =  toAdd + r[0] + ";"+ r[1] + ";" + r[2] + ";" + vote[0] + ":" + vo + ":" + vote[2] + ";;";
        }else{
            int vo = Integer.parseInt(vote[2]) + 1;
            toAdd =  toAdd + r[0] + ";" + r[1] + ";" + r[2] + ";" + vote[0] + ":" + vote[1] + ":" + vo + ";;";
        }
        for (int z=i+1; z<res.length; z++){
            toAdd = toAdd + res[z] + ";;";
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
                    voteView.putExtra("idProfile", id);
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
            String imgId = null;
            try {
                String[] d = load(FILE_USER).split(";;");
                for(int i=0; i<d.length; i++){
                    String[] src = d[i].split(";");
                    if(src[0].split(":")[1].equals(id)){
                        imgId = src[2].split(":")[1];
                    }
                }
                save(FILE_ALLUSERS, load(FILE_ALLUSERS) + id + ":" + imgId + ";imgSrc:" +  picturePath );
                Intent imgVote = new Intent(vote.this, imgView.class);
                imgVote.putExtra("numb", "0");
                imgVote.putExtra("idProfile", id);
                startActivity(imgVote);
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

    public static Bitmap rotateImage(String path, Bitmap source) throws IOException {
        Float angle = null;
        ExifInterface ei = new ExifInterface(path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = Float.valueOf(90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = Float.valueOf(180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = Float.valueOf(270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                angle = Float.valueOf(0);
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 100, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

}