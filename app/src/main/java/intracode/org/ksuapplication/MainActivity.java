package intracode.org.ksuapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    EditText a_Name, a_Email, a_Phone, a_sid;
    ImageView applicant_imageView;
    List<Applicant> applicants = new ArrayList<Applicant>();
    ListView applicantListView;
    Uri imageUri = null;
//    Uri imageUri = Uri.parse("android.resource://intracode.org.ksuapplicant/drawable/no_user.png");
    DatabaseHandler dbHandler;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        a_Name = (EditText) findViewById(R.id.applicantName);
        a_Email = (EditText) findViewById(R.id.applicantEmail);
        a_Phone = (EditText) findViewById(R.id.applicantPhone);
        a_sid = (EditText) findViewById(R.id.applicantSID);

        applicantListView = (ListView) findViewById(R.id.listView);

        applicant_imageView = (ImageView) findViewById(R.id.applicantImage);

        dbHandler = new DatabaseHandler(getApplicationContext());

        TabHost tab = (TabHost) findViewById(R.id.tab);

        tab.setup();

        TabHost.TabSpec tabSpec = tab.newTabSpec("staffApplication");
        tabSpec.setContent(R.id.staffApplication);
        tabSpec.setIndicator("staffApplication");
        tab.addTab(tabSpec);

        tabSpec = tab.newTabSpec("list");
        tabSpec.setContent(R.id.ApplicantList);
        tabSpec.setIndicator("list");
        tab.addTab(tabSpec);

        final Button applyButton = (Button) findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Joey", "Button");
                Applicant applicant = new Applicant(dbHandler.getApplicantCount(), String.valueOf(a_Name.getText()),
                        String.valueOf(a_Phone.getText()), String.valueOf(a_Email.getText()), String.valueOf(a_sid.getText()), imageUri );
                dbHandler.createApplicant(applicant);
                applicants.add(applicant);

                Toast.makeText(getApplicationContext(), "You, " + a_Name.getText().toString()  +
                        ", successfully applied to KSU", Toast.LENGTH_SHORT).show();

                populateList();

            }
        });

        a_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyButton.setEnabled(!a_Name.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        applicant_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select your photo"), 1);
            }
        });

//        Log.d("Joey", "here");

//        List<Applicant> addableApplicants = dbHandler.getAllApplicant();

//        int applicantCount = dbHandler.getApplicantCount();

//        for (int i = 0;i < applicantCount;i++) {
//            applicants.add(addableApplicants.get(i));
//        }
//
//        if (!applicants.isEmpty()) {
//            populateList();
//        }
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                applicant_imageView.setImageURI(data.getData());
            }
        }
    }

    private void populateList() {
        ArrayAdapter<Applicant> adapter = new ApplicantListAdapter();
        applicantListView.setAdapter(adapter);
    }


    private class ApplicantListAdapter extends ArrayAdapter<Applicant> {
        public ApplicantListAdapter() {
            super (MainActivity.this, R.layout.list_applicant, applicants  );
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.list_applicant, parent, false);
            }

            Applicant currentApplicant = applicants.get(position);

            TextView name = (TextView) view.findViewById(R.id.applicant_Name);
            TextView phone = (TextView) view.findViewById(R.id.applicant_Phone);
            TextView email = (TextView) view.findViewById(R.id.applicant_Email);
            TextView sid = (TextView) view.findViewById(R.id.applicant_StudentID);
            ImageView ivApplicantImage = (ImageView) view.findViewById(R.id.applicantImage_inListView);


            name.setText(currentApplicant.getName());
            phone.setText(currentApplicant.getPhone());
            email.setText(currentApplicant.getEmail());
            sid.setText(currentApplicant.getSID());
            ivApplicantImage.setImageURI(currentApplicant.getimageUri());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
