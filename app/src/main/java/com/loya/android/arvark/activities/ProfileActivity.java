package com.loya.android.arvark.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loya.android.arvark.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 55;
    private Button saveButton;
    private Spinner stateSpinner, lgaSpinner;
    private String state, lga;
    private EditText nameField, companyNameField, addressField, companyPhoneField, businessTypeField;

    private String name, companyName, address, phone, businessType;

    private TextView tapText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    private StorageReference mStorageRef;
    private Uri mImageUri = null;

    private ImageView imageView;

    private ProgressBar mProgressBar;

    private DatabaseReference profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        //reference to the "Users" node
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        //a directory is created to store all profile images to the Firebase Storage
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile_images");


        saveButton = (Button) findViewById(R.id.save_button);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        lgaSpinner = (Spinner) findViewById(R.id.lgaSpinner);
        nameField = (EditText) findViewById(R.id.name_edit_text);
        companyNameField = (EditText) findViewById(R.id.company_name_edit_text);
        addressField = (EditText) findViewById(R.id.company_address);
        companyPhoneField = (EditText) findViewById(R.id.company_phone_edit_text);
        businessTypeField = (EditText) findViewById(R.id.business_type_edit_text);
        tapText = (TextView) findViewById(R.id.tap_text);
        imageView = (ImageView) findViewById(R.id.fb_icon);
        mProgressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);

        profileImage = mDatabaseUsers.child(mAuth.getCurrentUser().getUid());


        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild("image")) {
                    String imageUrl = (String) dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("image").getValue();
                    Picasso.with(ProfileActivity.this).load(imageUrl).into(imageView);
                } else {
                    imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //call to method that'll set up the address of the user
        setupAddressSpinners();


        tapText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //image picker intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProfile();
            }
        });
    }


    /**
     * Method to set up the address of the user
     */
    public void setupAddressSpinners() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        //populates the quantity spinner ArrayList
        ArrayAdapter statesAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.array_states_in_nigeria, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        statesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        stateSpinner.setAdapter(statesAdapter);

        // Set the integer mSelected to the constant values
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = (String) parent.getItemAtPosition(position);
                setUpStatesSpinner(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Unknown
            }
        });
    }

    /**
     * method to set up the state spinner
     *
     * @param position current position of the spinner
     */
    private void setUpStatesSpinner(int position) {
        switch (position) {
            case 0:
                setUpLgaSpinner(R.array.lgas_in_abia);
                break;
            case 1:
                setUpLgaSpinner(R.array.lgas_in_adamawa);
                break;
            case 2:
                setUpLgaSpinner(R.array.lgas_in_akwa_ibom);
                break;
            case 3:
                setUpLgaSpinner(R.array.lgas_in_anambra);
                break;
            case 4:
                setUpLgaSpinner(R.array.lgas_in_bauchi);
                break;
            case 5:
                setUpLgaSpinner(R.array.lgas_in_bayelsa);
                break;
            case 6:
                setUpLgaSpinner(R.array.lgas_in_benue);
                break;
            case 7:
                setUpLgaSpinner(R.array.lgas_in_borno);
                break;
            case 8:
                setUpLgaSpinner(R.array.lgas_in_cross_river);
                break;
            case 9:
                setUpLgaSpinner(R.array.lgas_in_delta);
                break;
            case 10:
                setUpLgaSpinner(R.array.lgas_in_ebonyi);
                break;
            case 11:
                setUpLgaSpinner(R.array.lgas_in_edo);
                break;
            case 12:
                setUpLgaSpinner(R.array.lgas_in_ekiti);
                break;
            case 13:
                setUpLgaSpinner(R.array.lgas_in_enugu);
                break;
            case 14:
                setUpLgaSpinner(R.array.lgas_in_gombe);
                break;
            case 15:
                setUpLgaSpinner(R.array.lgas_in_imo);
                break;
            case 16:
                setUpLgaSpinner(R.array.lgas_in_jigawa);
                break;
            case 17:
                setUpLgaSpinner(R.array.lgas_in_kaduna);
                break;
            case 18:
                setUpLgaSpinner(R.array.lgas_in_kano);
                break;
            case 19:
                setUpLgaSpinner(R.array.lgas_in_katsina);
                break;
            case 20:
                setUpLgaSpinner(R.array.lgas_in_kebbi);
                break;
            case 21:
                setUpLgaSpinner(R.array.lgas_in_kogi);
                break;
            case 22:
                setUpLgaSpinner(R.array.lgas_in_kwara);
                break;
            case 23:
                setUpLgaSpinner(R.array.lgas_in_lagos);
                break;
            case 24:
                setUpLgaSpinner(R.array.lgas_in_nasarawa);
                break;
            case 25:
                setUpLgaSpinner(R.array.lgas_in_niger);
                break;
            case 26:
                setUpLgaSpinner(R.array.lgas_in_ogun);
                break;
            case 27:
                setUpLgaSpinner(R.array.lgas_in_ondo);
                break;
            case 28:
                setUpLgaSpinner(R.array.lgas_in_osun);
                break;
            case 29:
                setUpLgaSpinner(R.array.lgas_in_oyo);
                break;
            case 30:
                setUpLgaSpinner(R.array.lgas_in_plateau);
                break;
            case 31:
                setUpLgaSpinner(R.array.lgas_in_rivers);
                break;
            case 32:
                setUpLgaSpinner(R.array.lgas_in_sokoto);
                break;
            case 33:
                setUpLgaSpinner(R.array.lgas_in_taraba);
                break;
            case 34:
                setUpLgaSpinner(R.array.lgas_in_yobe);
                break;
            case 35:
                setUpLgaSpinner(R.array.lgas_in_zamfara);
                break;
            case 36:
                setUpLgaSpinner(R.array.lgas_in_fct);
                break;
        }
    }

    /**
     * Method to set up the local government areas corresponding to selected states
     *
     * @param lgaArray represents the local government areas of the selected state
     */
    private void setUpLgaSpinner(int lgaArray) {
        ArrayAdapter lgaAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                lgaArray, android.R.layout.simple_spinner_item);
        lgaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        lgaSpinner.setAdapter(lgaAdapter);

        lgaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                lga = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * Method to set up the profile of the user
     */
    private void setUpProfile() {

        name = nameField.getText().toString().trim();
        companyName = companyNameField.getText().toString().trim();
        address = addressField.getText().toString().trim();
        phone = companyPhoneField.getText().toString().trim();
        businessType = businessTypeField.getText().toString().trim();
        // tapText.setText(state + " " + lga + " " + name + " " + companyName + "\n" + address + " " + phone + " " + businessType);


        final String user_id = mAuth.getCurrentUser().getUid();

        if (mImageUri != null) {


            showProgress();
            //upload the image to the Firebase Storage so that the download url can be retrieved
            //and saved to the database
            StorageReference filePath = mStorageRef.child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    //creates a new child inside the User directory
                    //stores the name and profile image of the current user to the database using the user id
                    mDatabaseUsers.child(user_id).child("name").setValue(name);
                    mDatabaseUsers.child(user_id).child("company_name").setValue(companyName);
                    mDatabaseUsers.child(user_id).child("address").setValue(address);
                    mDatabaseUsers.child(user_id).child("phone").setValue(phone);
                    mDatabaseUsers.child(user_id).child("business_type").setValue(businessType);
                    mDatabaseUsers.child(user_id).child("lga").setValue(lga);
                    mDatabaseUsers.child(user_id).child("state").setValue(state);
                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl);
                    Toast.makeText(ProfileActivity.this, "Profile set up complete!", Toast.LENGTH_LONG).show();
                    dismissProgress();

                    Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setAllowRotation(true)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();

                imageView.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}