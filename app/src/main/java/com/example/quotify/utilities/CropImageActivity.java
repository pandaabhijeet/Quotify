package com.example.quotify.utilities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.quotify.BuildConfig;
import com.example.quotify.LoginActivity;
import com.example.quotify.R;
import com.example.quotify.fragments.ProfileFragment;
import com.example.quotify.models.ProfileImageModel;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropImageActivity extends AppCompatActivity {

    Uri imageUri;
    String imagePath;
    String userId = "6333d4354c4dbbff7d2e5967";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        getIntentData();

          String timeStamp = new SimpleDateFormat("ddmmyyyy_hhmmss").format(new Date());
          String dest_uri = timeStamp + ".jpg";

        UCrop.Options options = new UCrop.Options();
        options.setCropFrameColor(getResources().getColor(R.color.primary_red));
        options.setFreeStyleCropEnabled(true);
        options.setToolbarTitle("Crop Image");

        UCrop.of(imageUri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(CropImageActivity.this);

    }

    private void getIntentData()
    {
        Intent intent = getIntent();

        if(intent != null)
        {
            imagePath = intent.getStringExtra("imagePath");
            imageUri = Uri.parse(imagePath);
        } else
        {
            Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP)
        {
            if(data != null)
            {
                final Uri uploadUri = UCrop.getOutput(data);

                Log.d(TAG,"UploadUri:" +uploadUri);

                 saveProfileImage(uploadUri);

//                Intent parentIntent = NavUtils.getParentActivityIntent(this);
//                if(parentIntent != null)
//                {
//                    Log.d(TAG, "onActivityResult: "+parentIntent);
//                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(parentIntent);
//                    finish();
//                }
//                ProfileFragment frag = new ProfileFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.crop_image_activity_container,frag);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
         }

        }else if(resultCode == UCrop.RESULT_ERROR)
        {
            Throwable t = UCrop.getError(data);
            Toast.makeText(this,t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private File getImageFile() throws IOException {
        String imageFileName = new SimpleDateFormat("ddmmyyyy_hhmmss").format(new Date());
        File storageDir = this.getCacheDir();
        File file = File.createTempFile(imageFileName, ".jpg", storageDir);
        return file;
    }


    private  void saveProfileImage(Uri fileUri){

        String path = FileUtils.getRealPath(getApplicationContext(),fileUri);
        File file = new File(path);
        if(file.exists())
        {
            Toast.makeText(getApplicationContext(),file.getName(),Toast.LENGTH_LONG).show();
            fileUri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file);
            Log.d(TAG, "fileUri: "+fileUri);
        }

        try {
            RequestBody userIdPart = RequestBody.create(MediaType.parse("multipart/form-data"),userId);
            Log.d(TAG, "resolver: "+getContentResolver().getType(fileUri));
            RequestBody imagePart = RequestBody.create(MediaType.parse(getApplicationContext().getContentResolver().getType(fileUri)),file);
            MultipartBody.Part profileImage = MultipartBody.Part.createFormData("profileImage",file.getName(),imagePart);


        SigninApiUtility.getApiInstance()
                        .getApiInterface()
                        .uploadProfileImage(profileImage,userIdPart)
                        .enqueue(new Callback<ProfileImageModel>() {
                            @Override
                            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {

                                if (response.body() != null)
                                {
                                    if(response.body().getSuccess())
                                    {
                                        Toast.makeText(getApplicationContext(),"Profile picture updated", Toast.LENGTH_LONG).show();

                                        ProfileFragment frag = new ProfileFragment();
                                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.crop_image_activity_container,frag);
                                        fragmentTransaction.commit();
                                    }else
                                    {
                                        Toast.makeText(getApplicationContext(),response.body().getError(), Toast.LENGTH_LONG).show();
                                    }
                                }else
                                {
                                    Toast.makeText(getApplicationContext(),"Null Response", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ProfileImageModel> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


        }catch (Exception e)
        {
            Log.d("Exception parse :",e.getMessage());
        }
    }
}