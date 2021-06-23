package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class voteView extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile , f1, f2;
    EditText edDesc, edLoc, edTime;
    TextView bsave, rv, lv;
    public static String id;

    private  static final String FILE_ALLVOTE = "allVote.txt";
    private static final String FILE_USERVOTE ="uservote.txt";

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;

    private static final String FILE_ALLUSERS = "allUsersData.txt";
    private static final String FILE_USER = "userdata.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view);

        Bundle Extra = getIntent().getExtras();
        id = Extra.getString("idProfile");

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        f1= (ImageView) this.findViewById(R.id.foto1);
        f2= (ImageView) this.findViewById(R.id.foto2);
        edDesc = (EditText) this.findViewById(R.id.description);
        edLoc = (EditText) this.findViewById(R.id.location);
        edTime = (EditText) this.findViewById(R.id.time);
        bsave = (TextView) this.findViewById(R.id.save);
        rv = (TextView) this.findViewById(R.id.right_vote);
        lv = (TextView) this.findViewById(R.id.left_vote);

        ImageView[] imgs = {f1, f2};
        EditText[] descri = {edDesc,edLoc,edTime};

        String numb = Extra.getString("numb");
        String imgSrc;
        String descSrc;
        String votes;
        if (numb.equals("1")){
            imgSrc = Extra.getString("imgSrc");
            descSrc = Extra.getString("descrSrc");
            votes = Extra.getString("votes");

            setVoteLayout(imgSrc,descSrc, votes, imgs, descri);
        }else {
            setPhotosVote(FILE_USERVOTE, imgs);
        }
        //setDescriptionVote(FILE_USERVOTEDESCRIPTION, descri, numb);

        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String profileImg ="";
                    String toAdd = ";description:" + edDesc.getText().toString() + ":" + edLoc.getText().toString() + ":" + edTime.getText().toString();
                    String[] userData = load(FILE_USER).split(";;");
                    for (int i=0; i<userData.length; i++){
                        if(userData[i].split(";")[0].split(":")[1].equals(id)){
                            profileImg = userData[i].split(";")[2].split(":")[1];
                        }
                    }

                    String[] res = load(FILE_USERVOTE).split(";");
                    String toAddAllVote = load(FILE_ALLVOTE) + id + ":" + profileImg + ";voteSrc:" + res[res.length-1].split(":")[1] + ":" +  res[res.length-1].split(":")[2] +  toAdd + ";vote:0:0;;";
                    save(FILE_ALLVOTE , toAddAllVote);

                }catch (IOException e) {
                     e.printStackTrace();
                }
                Intent profile = new Intent(voteView.this, profile.class);
                profile.putExtra("idProfile", id);
                profile.putExtra("type", "0");
                startActivity(profile);
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(voteView.this, home.class);
                home.putExtra("idProfile", id);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(voteView.this, search.class);
                search.putExtra("idProfile", id);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(voteView.this, profile.class);
                profile.putExtra("type", "0");
                profile.putExtra("idProfile", id);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(voteView.this, vote.class);
                vote.putExtra("idProfile", id);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(voteView.this, badd);
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


    private void setPhotosVote(String FILE_NAME, ImageView[] imgs) {
        rv.setVisibility(View.INVISIBLE);
        lv.setVisibility(View.INVISIBLE);
        //Toast.makeText(getApplicationContext(), "numero in func " + numb, Toast.LENGTH_SHORT).show();
        String[] vs = load(FILE_NAME).split(";");
        int j = vs.length - 1;
        String[] res = vs[j].split(":");
        for (int i = 1; i < res.length; i++) {
            if (i <= imgs.length) {
                Bitmap bm = BitmapFactory.decodeFile(res[i]);
                Bitmap rotatedBitmap = null;
                try {
                    rotatedBitmap = rotateImage(res[i], bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgs[i - 1].setImageBitmap(rotatedBitmap);
            }
        }

    }

    private void setVoteLayout(String imgSrc, String descSrc, String votes, ImageView[]imgs, EditText[] des) {

        edDesc.setEnabled(false);
        edTime.setEnabled(false);
        edLoc.setEnabled(false);
        bsave.setClickable(false);
        bsave.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.VISIBLE);
        lv.setVisibility(View.VISIBLE);

        for (int i = 1; i < descSrc.split(":").length; i++) {
            des[i - 1].setText(descSrc.split(":")[i]);
        }
        lv.setText(votes.split(":")[1]);
        rv.setText(votes.split(":")[2]);
        for (int i =1; i< imgSrc.split(":").length; i++) {
            Bitmap bm;
            if(imgSrc.contains("storage")){
                bm = BitmapFactory.decodeFile(imgSrc.split(":")[i]);
                Bitmap rotatedBitmap = null;
                try {
                    rotatedBitmap = rotateImage(imgSrc.split(":")[i], bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgs[i - 1].setImageBitmap(rotatedBitmap);
            }else{
                int id = getResources().getIdentifier(imgSrc.split(":")[i], "drawable", "com.example.clubbbycloset");
                bm = BitmapFactory.decodeResource(getResources(), id);
                imgs[i - 1].setImageBitmap(bm);
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

    //to load img from gallery
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
                    saveVoteImg(paths, FILE_USERVOTE);
                    Intent voteView = new Intent(voteView.this, voteView.class);
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

        }
    }

    private void saveVoteImg(String picPath, String FILE_NAME) throws IOException {
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
                Intent imgVote = new Intent(voteView.this, imgView.class);
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

    public void save(String FILE_NAME, String text) throws IOException {
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

}
