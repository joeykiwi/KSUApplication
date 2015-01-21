package intracode.org.ksuapplication;

import android.net.Uri;

/**
 * Created by jongwookim on 1/17/15.
 */
public class Applicant {

    private String _name, _phone, _email, _sid;
    private Uri _imageUri;
    private int _id;

    public Applicant(int id, String name, String phone, String email, String sid, Uri imageUri){
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _sid = sid;
        _imageUri = imageUri;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }

    public String getSID() {
        return _sid;
    }

    public Uri getimageUri() {
        return _imageUri;
    }
}
