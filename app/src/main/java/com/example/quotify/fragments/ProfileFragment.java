package com.example.quotify.fragments;

import static com.yalantis.ucrop.UCropFragment.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quotify.R;
import com.example.quotify.models.SignUpUserModel;
import com.example.quotify.utilities.CropImageActivity;
import com.example.quotify.utilities.SigninApiUtility;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View view;
    private CircleImageView profileImageView;
    private TextView profileNameView,favQuoteView;
    private AppCompatButton addImageBtn,nameEditBtn,favQuoteEditBtn;
    private RelativeLayout accountLayout,preferenceLayout,keepLayout,helpLayout, circleLayout;
    private Dialog addImageDialog;
    private String currentUserId = "6333d4354c4dbbff7d2e5967";

    public ProfileFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialise(view);

        getUpdatedProfile(currentUserId);


        profileNameView.setText("Abhijeet Panda");

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
           {
                addImageDialog.setContentView(R.layout.custom_pick_image_dialog);
                addImageDialog.getWindow().setBackgroundDrawableResource(R.drawable.rect_backgorund);

                LinearLayout galleryBtn = addImageDialog.findViewById(R.id.option_gallery);
                LinearLayout cameraBtn = addImageDialog.findViewById(R.id.option_camera);
                AppCompatButton cancelBtn = addImageDialog.findViewById(R.id.cancelBtn);
                addImageDialog.show();
                addImageDialog.setCanceledOnTouchOutside(false);

                galleryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addImageDialog.dismiss();
                        if (!storagePermission())
                        {
                            Toast.makeText(view.getContext(),"Please provide Storage permissions in you Settings",Toast.LENGTH_LONG).show();
                        } else
                        {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                            galleryIntent.setType("image/*");
                            getImageLauncher.launch(galleryIntent);
                        }
                    }
                });

                cameraBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addImageDialog.dismiss();
                        if(!cameraPermission())
                        {
                            Toast.makeText(view.getContext(),"Please provide camera permissions in you Settings",Toast.LENGTH_LONG).show();
                        } else
                        {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getImageLauncher.launch(cameraIntent);
                        }
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addImageDialog.dismiss();
                    }
                });
            }


        });

        return  view;
    }

    private void initialise(View view)
    {
        profileImageView = view.findViewById(R.id.profile_image);
        profileNameView = view.findViewById(R.id.profile_name);
        favQuoteView = view.findViewById(R.id.fav_quote);
        favQuoteView.setSelected(true);
        addImageBtn = view.findViewById(R.id.add_photo_btn);
        nameEditBtn = view.findViewById(R.id.name_edit_btn);
        favQuoteEditBtn = view.findViewById(R.id.fav_quote_edit_btn);
        accountLayout = view.findViewById(R.id.account_layout);
        preferenceLayout = view.findViewById(R.id.preference_layout);
        keepLayout = view.findViewById(R.id.keep_layout);
        helpLayout = view.findViewById(R.id.help_layout);
        circleLayout = view.findViewById(R.id.circle_layout);
        addImageDialog = new Dialog(view.getContext());
        //profileImageView.setImageURI(croppedUri);
    }

    private boolean cameraPermission()
    {
        boolean result_1 = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result_2  = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result_1 && result_2;
    }

    private boolean storagePermission()
    {
        boolean result_1 = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean result_2  = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result_1 && result_2;
    }


    private ActivityResultLauncher<Intent> getImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
              new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK)
            {
                try {
                    Intent imageData = result.getData();
                    Uri imageUri = imageData.getData();

                    Intent cropIntent = new Intent(getContext(), CropImageActivity.class);
                    cropIntent.putExtra("imagePath",imageUri.toString());
                    startActivity(cropIntent);

                }catch (Exception e)
                {
                    Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            } else
            {
                Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
            }
        }
    });


    private void getUpdatedProfile(String userId)
    {

        SigninApiUtility.getApiInstance()
                        .getApiInterface()
                        .currentUser(userId)
                        .enqueue(new Callback<SignUpUserModel>() {
                            @Override
                            public void onResponse(Call<SignUpUserModel> call, Response<SignUpUserModel> response)
                            {
                                if(response.body() != null)
                                {
                                    if(response.body().getSuccess())
                                    {
                                        String profileImage = response.body().getProfile_image();
                                        String userName = response.body().getUsername();
                                        String favQuote = response.body().getFav_quote();

                                        Log.d(TAG, "profileImage: " + profileImage);
                                        Log.d(TAG, "userName: " + userName);
                                        Log.d(TAG, "favQuote: " + favQuote);


                                        String profileImageUrl = "https://quotifyapplication.onrender.com/"+profileImage;
                                        Log.d(TAG, "profileImageUrl: " + profileImageUrl);

                                        Glide.with(getContext()).load(profileImageUrl)
                                                .placeholder(getResources().getDrawable(R.drawable.ic_profile))
                                                .into(profileImageView);
                                    } else
                                    {
                                        Log.d(TAG, "error: " + response.body().getError());
                                        Toast.makeText(getContext(), "Error: " +response.body().getError(), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Log.d(TAG, "error: Null Response");
                                    Toast.makeText(getContext(), "Error: Null Response", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SignUpUserModel> call, Throwable t)
                            {
                                Log.d(TAG, "error: " + t.getMessage());
                                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
    }
}